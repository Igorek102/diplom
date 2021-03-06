package ru.igorek.javafx.controllers;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ru.igorek.core.model.Application;
import ru.igorek.core.model.Event;
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
    private TableView applicationsTV, historyTable;
    private static TableView appls;
    @FXML
    private TableColumn<Application, String> nameCol;
    @FXML
    private TableColumn<Application, String> descCol;
    @FXML
    private TableColumn<Application, String> pathCol;
    @FXML
    private TableColumn<Application, Long> idCol;
    @FXML
    private TableView applsHistory;
    @FXML
    private TableColumn<Application, String> happlCol;
    @FXML
    private TableColumn<Application, Long> hidCol;
    @FXML
    private TableColumn<Event, Date> dateCol;
    @FXML
    private TableColumn<Event, String> resCol;
    
    private final String appUpdPath = "/fxml/ApplicationUpdating.fxml";
    private final String appDelPath = "/fxml/ApplicationDeleting.fxml";
    private final String appRegPath = "/fxml/ApplRegForm.fxml";
    private final String startFormPath = "/fxml/StartApplicationForm.fxml";
    
    private final MainApp mainApp = new MainApp();
    private static Application curApplication;
    
    private static ObservableList<Application> applications;
    private static ObservableList<Event> events;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        applications = FXCollections.observableArrayList(dbApi.getApplicationsByResource(ResourceConnectionController.getCurUrl()));
        
        appls = applicationsTV;
        applications.addListener((ListChangeListener.Change<? extends Application> c) -> {
            appls.setItems(applications);
        });
                
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        pathCol.setCellValueFactory(new PropertyValueFactory<>("path"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("applicationId"));
        hidCol.setCellValueFactory(new PropertyValueFactory<>("applicationId"));
        happlCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("dat"));
        resCol.setCellValueFactory(new PropertyValueFactory<>("message"));
                
        appls.setItems(applications);
        
        applicationNameTF.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            ObservableList<Application> visibleAppls = FXCollections.observableArrayList();
            applications.stream().forEach((application) ->{
                if (application.getName().startsWith(newValue))
                    visibleAppls.add(application);
            });
            appls.setItems(visibleAppls);
        });
        
        
        applsHistory.setItems(applications);
        
        applsHistory.getSelectionModel().selectedItemProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> {
            events = FXCollections.observableArrayList(dbApi.getApplicationHistory(((Application)(applsHistory.getSelectionModel().getSelectedItem())).getApplicationId()));
            historyTable.setItems(events);
        });
    }    
    
    public void onAnotherRes(ActionEvent actionEvent){
        try {
            new MainApp().start((Stage)(((Node)actionEvent.getSource()).getScene().getWindow()));
        } catch (Exception ex) {
            Logger.getLogger(ApplicationsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void onStartBtnClick(ActionEvent actionEvent){
        if (appls.getSelectionModel().isEmpty()){
            new ErrorDialog().showErrorDialog(actionEvent, "", "Приложение не выбрано!");
            return;
        }
        curApplication = (Application)appls.getSelectionModel().getSelectedItem();
        new MainApp().showForm(actionEvent, startFormPath, "Запуск приложения");
    }
    
    public void onDeleteBtnClick(ActionEvent actionEvent){
        if (appls.getSelectionModel().isEmpty()){
            new ErrorDialog().showErrorDialog(actionEvent, "", "Приложение не выбрано!");
            return;
        }
        new MainApp().showForm(actionEvent, appDelPath, "Удаление приложения");
    }
    
    public void onRegBtnClick(ActionEvent actionEvent){
        mainApp.showForm(actionEvent, appRegPath, "Регистрация приложения");
    }
    
    public void onUpdateBtnClick(ActionEvent actionEvent){
        if (appls.getSelectionModel().isEmpty()){
            new ErrorDialog().showErrorDialog(actionEvent, "", "Приложение не выбрано!");
            return;
        }
        curApplication = (Application)appls.getSelectionModel().getSelectedItem();
        mainApp.showForm(actionEvent, appUpdPath, "Редактирование");
    }
    public static void refresh(){
        int index = applications.indexOf(curApplication);
        Application application = new Application();
        application.setApplicationId(curApplication.getApplicationId());
        application.setDescription(curApplication.getDescription());
        application.setName(curApplication.getName());
        application.setPath(curApplication.getPath());
        applications.remove(curApplication);
        applications.add(index, application);
    }
    public static void add(Application application){
        applications.add(application);
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
