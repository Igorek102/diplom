package ru.igorek.javafx.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;
import ru.igorek.javafx.MainApp;

/**
 * FXML Controller class
 *
 * @author Игорек
 */
public class NoInternetController{
    public void onRepeatBtn(ActionEvent actionEvent){
        //onCancelBtn(actionEvent);
        //new MainApp().start((Stage)(((Node)actionEvent.getSource()).getScene().getWindow()));
    }
    public void onCancelBtn(ActionEvent actionEvent){
        ((Stage)(((Node)actionEvent.getSource()).getScene().getWindow())).close();  
    }
}
