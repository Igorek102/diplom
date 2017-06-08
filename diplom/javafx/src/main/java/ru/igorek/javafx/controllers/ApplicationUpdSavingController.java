package ru.igorek.javafx.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Игорек
 */
public class ApplicationUpdSavingController{
    public void onYesBtnClick(ActionEvent actionEvent){
        new ApplicationUpdatingController().saveChanges();
        onNoBtnClick(actionEvent);
        ((Stage)((Stage)(((Node)actionEvent.getSource()).getScene().getWindow())).getOwner()).close();
    }
    
    public void onNoBtnClick(ActionEvent actionEvent){
        new AuthorisationWindowController().onCancelBtnClick(actionEvent);
    }
}
