package ru.igorek.javafx.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Игорек
 */
public class ErrorDialog implements Initializable{
    @FXML
    private Label errorLabel;
    
    private static Label err;
    
    public void showErrorDialog(ActionEvent actionEvent,String stageTitle,String errMsg){
        try {
            Stage stage = new Stage();
            Parent errAuth = FXMLLoader.load(getClass().getResource("/fxml/Error.fxml"));
            Scene scene = new Scene(errAuth);
            stage.setTitle(stageTitle);
            stage.setResizable(false);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
            stage.setScene(scene);
            err.setText(errMsg);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void onOkBtnClick(ActionEvent actionEvent){
        new AuthorisationWindowController().onCancelBtnClick(actionEvent);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        err = errorLabel;
        err.setAlignment(Pos.CENTER);
    }
}
