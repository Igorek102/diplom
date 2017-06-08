package ru.igorek.javafx.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Игорек
 */
public class ApplicationDeletingController implements Initializable{
    @FXML
    private Label delLabel;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        delLabel.setText("Вы уверены, что хотите удалить приложение '" + ApplicationsController.getSelApplName() + "'?");
    }
    
    public void onYesBtnClick(ActionEvent actionEvent){
        ApplicationsController.del();
        onNoBtnClick(actionEvent);
    }
    public void onNoBtnClick(ActionEvent actionEvent){
        new AuthorisationWindowController().onCancelBtnClick(actionEvent);
    }
}
