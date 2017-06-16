package ru.igorek.javafx.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import ru.igorek.core.model.Application;
import ru.igorek.core.model.Parameter;
import ru.igorek.javafx.MainApp;
import static ru.igorek.javafx.controllers.ResourceConnectionController.dbApi;

/**
 * FXML Controller class
 *
 * @author Игорек
 */
public class ApplicationUpdatingController implements Initializable{
    private final String paramDelPath = "/fxml/ParameterDeleting.fxml";
    private final String paramSorUPath = "/fxml/SaveOrUpdateParameter.fxml";
    
    @FXML
    private TextArea descTF;
    @FXML
    private TextField nameTF, pathTF;
    @FXML
    private TableView parametersTV;
    private static TableView params;
    @FXML
    private TableColumn<Parameter, String> nameCol;
    @FXML
    private TableColumn<Parameter, String> descCol;
    @FXML
    private TableColumn<Parameter, Boolean> valCol;
    @FXML
    private TableColumn<Parameter, Long> idCol;
    
    private static Application savingApplication;
    private static String curName, curDesc, curPath;
    private static Parameter selectedParameter;
    
    private static ObservableList<Parameter> parameters;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        parameters = FXCollections.observableArrayList(dbApi.getParametersByApplication(ApplicationsController.getCurApplication().getApplicationId()));
        savingApplication = ApplicationsController.getCurApplication();
        
        params = parametersTV;
        parameters.addListener((ListChangeListener.Change<? extends Parameter> c) -> {
            params.setItems(parameters);
        });
                
        nameCol.setCellValueFactory(new PropertyValueFactory<>("parameterName"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("parameterDescription"));
        valCol.setCellValueFactory(new PropertyValueFactory<>("flag"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("parameterId"));
        
        params.setItems(parameters);
        
        curName = savingApplication.getName();
        curDesc = savingApplication.getDescription();
        curPath = savingApplication.getPath();
        
        nameTF.setText(curName);
        descTF.setText(curDesc);
        pathTF.setText(curPath);
    }
    
    public void onSaveBtnClick(ActionEvent actionEvent){
        if (nameTF.getText().equals("") || pathTF.getText().equals("")){
            new ErrorDialog().showErrorDialog(actionEvent, "", "Поля 'Имя' и 'Путь' обязательны для заполнения!");
            return;
        }
        String newName = nameTF.getText(), newDesc = descTF.getText(), newPath = pathTF.getText();
        if (curName.equals(newName) && curDesc.equals(newDesc) && curPath.equals(newPath)){
            onCancelBtnClick(actionEvent);
            return;
        }
        savingApplication.setName(newName);
        savingApplication.setDescription(newDesc);
        savingApplication.setPath(newPath);
            
        ResourceConnectionController.dbApi.updateApplication(savingApplication);
        ApplicationsController.refresh();
        onCancelBtnClick(actionEvent);
    }
    public void onCancelBtnClick(ActionEvent actionEvent){
        new AuthorisationWindowController().onCancelBtnClick(actionEvent);
    }
    
    public void onAddPar(ActionEvent actionEvent){
        selectedParameter = new Parameter();
        selectedParameter.setFlag(false);
        selectedParameter.setParameterDescription("");
        selectedParameter.setParameterName("");
        new MainApp().showForm(actionEvent, paramSorUPath, "Добавление параметра");
    }
    public void onDelPar(ActionEvent actionEvent){
        if (params.getSelectionModel().isEmpty()){
            new ErrorDialog().showErrorDialog(actionEvent, "", "Параметр не выбран!");
            return;
        }
        new MainApp().showForm(actionEvent, paramDelPath, "Удаление приложения");
    }
    
    public void onChPar(ActionEvent actionEvent){
        selectedParameter = (Parameter)params.getSelectionModel().getSelectedItem();
        new MainApp().showForm(actionEvent, paramSorUPath, "Редактирование параметра");
    }
    public static void addParam(Parameter parameter){
        parameters.add(parameter);
    }
    public static void refreshParameter(){
        int index = parameters.indexOf(selectedParameter);
        Parameter parameter = new Parameter();
        parameter.setParameterId(selectedParameter.getParameterId());
        parameter.setParameterName(selectedParameter.getParameterName());
        parameter.setParameterDescription(selectedParameter.getParameterDescription());
        parameter.setFlag(selectedParameter.isFlag());
        parameters.remove(selectedParameter);
        parameters.add(index, parameter);
    }
    public static void delParam(){
        Parameter parameter  = (Parameter)params.getSelectionModel().getSelectedItem();
        dbApi.deleteParameter(parameter.getParameterId());
        parameters.remove(parameter);
    }
    public static String getSelParamName(){
        return ((Parameter)params.getSelectionModel().getSelectedItem()).getParameterName();
    }
    public static Parameter getSelParam(){
        return selectedParameter;
    }
}
