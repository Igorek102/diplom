package ru.igorek.javafx.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import ru.igorek.core.model.Application;
import ru.igorek.javafx.MainApp;
import static ru.igorek.javafx.controllers.ResourceConnectionController.dbApi;

/**
 * FXML Controller class
 *
 * @author Игорек
 */
public class ApplicationsController implements Initializable {
    @FXML
    private TextField applicationNameTF;
    @FXML
    private TableView applicationsTV;
    private static TableView appls;
    @FXML
    private TableColumn<Application, String> nameCol;
    @FXML
    private TableColumn<Application, String> descCol;
    @FXML
    private TableColumn<Application, String> pathCol;
    @FXML
    private TableColumn<Application, Long> idCol;
    
    private final String appUpdPath = "/fxml/ApplicationUpdating.fxml";
    private final String appDelPath = "/fxml/ApplicationDeleting.fxml";
    
    private final MainApp mainApp = new MainApp();
    private static Application curApplication;
    
    private static ObservableList<Application> applications = FXCollections.observableArrayList(dbApi.getApplicationsByResource(ResourceConnectionController.getCurUrl()));
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        appls = applicationsTV;
        applications.addListener((ListChangeListener.Change<? extends Application> c) -> {
            appls.setItems(applications);
        });
                
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        pathCol.setCellValueFactory(new PropertyValueFactory<>("path"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("applicationId"));
        
        appls.setItems(applications);
        
        applicationNameTF.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            ObservableList<Application> visibleAppls = FXCollections.observableArrayList();
            applications.stream().forEach((application) ->{
                if (application.getName().startsWith(newValue))
                    visibleAppls.add(application);
            });
            appls.setItems(visibleAppls);
        });
    }    
    
    public void onStartBtnClick(ActionEvent actionEvent){
        if (appls.getSelectionModel().isEmpty()){
            new ErrorDialog().showErrorDialog(actionEvent, "Selection Error", "Приложение не выбрано!");
            return;
        }
    }
    
    public void onDeleteBtnClick(ActionEvent actionEvent){
        if (appls.getSelectionModel().isEmpty()){
            new ErrorDialog().showErrorDialog(actionEvent, "Selection Error", "Приложение не выбрано!");
            return;
        }
        new MainApp().showForm(actionEvent, appDelPath, "Application Deleting");
    }
    
    public void onRegBtnClick(ActionEvent actionEvent){
        applicationsTV.getItems().stream().forEach((application) -> System.out.println(application));
    }
    
    public void onUpdateBtnClick(ActionEvent actionEvent){
        if (appls.getSelectionModel().isEmpty()){
            new ErrorDialog().showErrorDialog(actionEvent, "Selection Error", "Приложение не выбрано!");
            return;
        }
        curApplication = (Application)appls.getSelectionModel().getSelectedItem();
        mainApp.showForm(actionEvent, appUpdPath, "Application Updating");
    }
    public static void refresh(){
        applications.stream().forEach((application) -> System.out.println(application));
        int index = applications.indexOf(curApplication);
        Application application = new Application();
        application.setApplicationId(curApplication.getApplicationId());
        application.setDescription(curApplication.getDescription());
        application.setName(curApplication.getName());
        application.setPath(curApplication.getPath());
        applications.remove(curApplication);
        applications.add(index, application);
    }
    public static void del(){
        Application application = (Application)appls.getSelectionModel().getSelectedItem();
        dbApi.deleteApplication(application.getApplicationId());
        applications.remove(application);
    }
    public static Application getCurApplication() {
        return curApplication;
    }
    public static String getSelApplName(){
        return ((Application)appls.getSelectionModel().getSelectedItem()).getName();
    }
}
