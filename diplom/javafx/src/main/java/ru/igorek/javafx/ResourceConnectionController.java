package ru.igorek.javafx;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import ru.igorek.core.dao.DBApi;

public class ResourceConnectionController implements Initializable {
    private static DBApi dbApi = new DBApi();
    private static ObservableList<String> urls = FXCollections.observableArrayList(dbApi.getAllUrls());
    
    @FXML
    private TextField resourceUrlTf;
    @FXML
    private ListView resources;
    @FXML
    private Button connectBtn;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        resources.setItems(urls);
        
        resourceUrlTf.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            ObservableList<String> visibleUrls = FXCollections.observableArrayList();
            urls.stream().forEach((resUrl) ->{
                if (resUrl.startsWith(newValue))
                    visibleUrls.add(resUrl);
            });
            resources.setItems(visibleUrls);
        });
    }    
    
    public void onConnect(ActionEvent actionEvent) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Scene2.fxml"));
        Scene secScene = new Scene(root);
        ((Stage)(((Node)actionEvent.getSource()).getScene().getWindow())).setScene(secScene);
    }
    
    public static DBApi getDbApi(){
        return dbApi;
    }
}
