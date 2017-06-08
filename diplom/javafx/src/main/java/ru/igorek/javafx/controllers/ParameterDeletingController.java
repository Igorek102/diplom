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
public class ParameterDeletingController implements Initializable{

    @FXML
    private Label delLabel;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        delLabel.setText("Вы уверены, что хотите удалить параметр '" + ApplicationUpdatingController.getSelParamName() + "'?");
    } 

    @FXML
    private void onYesBtnClick(ActionEvent actionEvent) {
        ApplicationUpdatingController.delParam();
        onNoBtnClick(actionEvent);
    }

    @FXML
    private void onNoBtnClick(ActionEvent actionEvent) {
        new AuthorisationWindowController().onCancelBtnClick(actionEvent);
    }
}
