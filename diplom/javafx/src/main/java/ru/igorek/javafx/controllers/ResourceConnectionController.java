package ru.igorek.javafx.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.igorek.core.dao.DBApi;
import ru.igorek.core.dao.SshApi;
import ru.igorek.core.model.Application;
import ru.igorek.core.model.Resource;
import ru.igorek.javafx.MainApp;

public class ResourceConnectionController implements Initializable {
    @FXML
    private TextField resourceUrlTf;
    @FXML
    private TableView resources;
    @FXML
    private TableColumn<Resource, String> urlCol;
    @FXML
    private TableColumn<Resource, String> domainNameCol;
    
    private final String authPath = "/fxml/AuthorisationWindow.fxml";
    private final String newResPath = "/fxml/ResourceRegistration.fxml";
    
    public static DBApi dbApi = new DBApi();;
    public static SshApi sshApi = new SshApi();
    private static String currentResourceUrl;
    
    private static ObservableList<Resource> urls = FXCollections.observableArrayList(dbApi.getAllResources());
    private final MainApp mainApp = new MainApp();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        urlCol.setCellValueFactory(new PropertyValueFactory<>("URL"));
        domainNameCol.setCellValueFactory(new PropertyValueFactory<>("domainName"));
        urls.addListener((ListChangeListener.Change<? extends Resource> c) -> {
            resources.setItems(urls);
        });
        
        resources.setItems(urls);
        
        resourceUrlTf.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            ObservableList<Resource> visibleUrls = FXCollections.observableArrayList();
            urls.stream().forEach((resUrl) ->{
                if (resUrl.getURL().startsWith(newValue) || resUrl.getDomainName().startsWith(newValue))
                    visibleUrls.add(resUrl);
            });
            resources.setItems(visibleUrls);
        });
    }    
    
    public void onConnectBtnClick(ActionEvent actionEvent){
        if (resources.getSelectionModel().isEmpty()){
            new ErrorDialog().showErrorDialog(actionEvent, "", "Ресурс не выбран!");
            return;
        }
        currentResourceUrl = ((Resource)resources.getSelectionModel().getSelectedItem()).getURL();
        String url = currentResourceUrl.substring(0, currentResourceUrl.indexOf(":"));
        int port = Integer.parseInt(currentResourceUrl.substring(currentResourceUrl.indexOf(":") + 1, currentResourceUrl.length()));
        boolean isResourceAvailable = sshApi.isResourceAvailable(url,port);
        if (isResourceAvailable)
            mainApp.showForm(actionEvent, authPath, "Авторизация");
        else {
            new ErrorDialog().showErrorDialog(actionEvent, "Ресурс недоступен", "Не удается установить соединение!");
        }
    }
    public void onNewResourceBtnClick(ActionEvent actionEvent){
        mainApp.showForm(actionEvent, newResPath, "Регистрация ресурса");
    }
    public static void refresh(Resource newRes){
        urls.add(newRes);
    }
    public static String getCurUrl(){
        return currentResourceUrl;
    }
}
