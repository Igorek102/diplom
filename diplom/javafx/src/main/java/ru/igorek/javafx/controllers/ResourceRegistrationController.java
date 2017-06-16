package ru.igorek.javafx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import ru.igorek.core.model.Resource;

/**
 * FXML Controller class
 *
 * @author Игорек
 */
public class ResourceRegistrationController{  
    @FXML
    TextField urlTf, portTf, domainNameTF;
    
    public void onReg(ActionEvent actionEvent){
        if (urlTf.getText().equals("") || portTf.getText().equals("")){
            new ErrorDialog().showErrorDialog(actionEvent, "", "Все поля должны быть заполнены!");
            return;
        }
        String resourceUrl = urlTf.getText() + ":" + portTf.getText();
        String domainName = domainNameTF.getText();
        Resource resource = new Resource();
        resource.setURL(resourceUrl);
        resource.setDomainName(domainName);
        ResourceConnectionController.dbApi.addResource(resourceUrl, domainName);
        ResourceConnectionController.refresh(resource);
        onCanc(actionEvent);
    }
    public void onCanc(ActionEvent actionEvent){
        new AuthorisationWindowController().onCancelBtnClick(actionEvent);
    }
}
