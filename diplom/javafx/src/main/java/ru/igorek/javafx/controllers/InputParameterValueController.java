package ru.igorek.javafx.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Игорек
 */
public class InputParameterValueController implements Initializable {
    @FXML
    private Label textLabel;
    @FXML
    private TextField valueTF;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        textLabel.setText("Введите значение для параметра '" + StartApplicationFormController.getCurParameter().getParameterName() + "':");
    }    
    public void onOkBtnClick(ActionEvent actionEvent){
        if (valueTF.getText().equals("")){
            new ErrorDialog().showErrorDialog(actionEvent, "Incorrect Input", "Введите значение!");
            return;
        }
        onCancelBtnClick(actionEvent);
        StartApplicationFormController.choiceParam(valueTF.getText());
    }
    public void onCancelBtnClick(ActionEvent actionEvent){
        new AuthorisationWindowController().onCancelBtnClick(actionEvent);
    }
}
