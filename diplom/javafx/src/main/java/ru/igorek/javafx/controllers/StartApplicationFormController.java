package ru.igorek.javafx.controllers;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import ru.igorek.core.model.Application;
import ru.igorek.core.model.Parameter;
import ru.igorek.javafx.MainApp;

/**
 * FXML Controller class
 *
 * @author Игорек
 */
public class StartApplicationFormController implements Initializable {
    @FXML
    private Label applicationNameLabel;
    @FXML
    private TableView avParamsTV, chParamsTV;
    private static TableView avParams, chParams;
    @FXML
    private TableColumn<Parameter, String> nameCol;
    @FXML
    private TableColumn<Parameter, String> descCol;
    @FXML
    private TableColumn<ChoisenParameter, String> chNameCol;
    @FXML
    private TableColumn<ChoisenParameter, String> chValueCol;
    
    private static ObservableList<Parameter> parameters;
    private static ObservableList<ChoisenParameter> chParameters;
    
    private final String inpValPath = "/fxml/InputParameterValue.fxml";
    private static Parameter curParameter;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        avParams = avParamsTV;
        chParams = chParamsTV;
        
        applicationNameLabel.setText(ApplicationsController.getCurApplication().getName());
        
        nameCol.setCellValueFactory(new PropertyValueFactory<>("parameterName"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("parameterDescription"));
        chNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        chValueCol.setCellValueFactory(new PropertyValueFactory<>("value"));
        
        chParameters = FXCollections.observableArrayList();
        parameters = FXCollections.observableArrayList(ResourceConnectionController.dbApi.getParametersByApplication(ApplicationsController.getCurApplication().getApplicationId()));
        avParams.setItems(parameters);
        chParams.setItems(chParameters);
        
        parameters.addListener((ListChangeListener.Change<? extends Parameter> c) -> {
            avParamsTV.setItems(parameters);
        });
        chParameters.addListener((ListChangeListener.Change<? extends ChoisenParameter> c) -> {
            chParamsTV.setItems(chParameters);
        });
        
        avParamsTV.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 2){
                curParameter = (Parameter)avParamsTV.getSelectionModel().getSelectedItem();
                if (curParameter.isFlag())
                    choiceParam(null);
                else
                    new MainApp().showForm(event, inpValPath, "Input Value");
            }
        });
    }    
    public void onStartBtnClick(ActionEvent actionEvent){
        String curUrl = ResourceConnectionController.getCurUrl();
        String url = curUrl.substring(0, curUrl.indexOf(":"));
        int port = Integer.parseInt(curUrl.substring(curUrl.indexOf(":") + 1, curUrl.length()));
        String login = AuthorisationWindowController.getCurUserLogin();
        String pass = AuthorisationWindowController.getCurUserPassword();
        String command = getCommand();
        String result = ResourceConnectionController.sshApi.startApplication(url, port, login, pass, command);
        //result.stream().forEach((string) -> System.out.println(string));
        ResourceConnectionController.dbApi.addEventToHistory(ApplicationsController.getCurApplication().getApplicationId(), new Date(), result);
        onCancelBtnClick(actionEvent);
    }
    public void onCancelBtnClick(ActionEvent actionEvent){
        new AuthorisationWindowController().onCancelBtnClick(actionEvent);
    }
    public static Parameter getCurParameter() {
        return curParameter;
    }
    public static void choiceParam(String value){
        ChoisenParameter chParameter = new ChoisenParameter();
        chParameter.setName(curParameter.getParameterName());
        if (value==null)
            chParameter.setValue("");
        else
            chParameter.setValue(value);
        parameters.remove(curParameter);
        chParameters.add(chParameter);
    }
    private String getCommand(){
        StringBuilder sb = new StringBuilder();
        sb.append("java -jar ");
        sb.append(ApplicationsController.getCurApplication().getPath());
        sb.append("/");
        sb.append(ApplicationsController.getCurApplication().getName());
        chParameters.stream().forEach((parameter) -> {
            sb.append(" ");
            sb.append(parameter.getName());
            String parameterValue = parameter.getValue();
            if (!parameterValue.equals("")){
                sb.append(" ");
                sb.append(parameterValue);
            }
        });
        return sb.toString();
    }
}
