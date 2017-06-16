package ru.igorek.javafx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Игорек
 */
public class ResourceRegistrationController{  
    @FXML
    TextField urlTf, portTf;
    
    public void onReg(ActionEvent actionEvent){
        if (urlTf.getText().equals("") || portTf.getText().equals("")){
            new ErrorDialog().showErrorDialog(actionEvent, "", "Все поля должны быть заполнены!");
            return;
        }
        String resourceUrl = urlTf.getText() + ":" + portTf.getText();
        ResourceConnectionController.dbApi.addResource(resourceUrl);
        ResourceConnectionController.refresh(resourceUrl);
        onCanc(actionEvent);
    }
    public void onCanc(ActionEvent actionEvent){
        new AuthorisationWindowController().onCancelBtnClick(actionEvent);
    }
}
