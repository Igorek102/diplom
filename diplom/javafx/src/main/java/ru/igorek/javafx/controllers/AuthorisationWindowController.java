package ru.igorek.javafx.controllers;

import com.jcraft.jsch.JSchException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import ru.igorek.core.dao.NoSuchUserException;

/**
 *
 * @author Игорек
 */
public class AuthorisationWindowController {
    @FXML
    private TextField login, password;
    
    public void onEnterBtnClick(ActionEvent actionEvent){
        String log = login.getText();
        String pass = password.getText();
        if (log.equals("") || pass.equals("")){
            new ErrorDialog().showErrorDialog(actionEvent, "Input Error", "Все поля должны быть заполнены!");
            return;
        }
        Window mainWindow = ((Stage)(((Node)actionEvent.getSource()).getScene().getWindow())).getOwner();
        try {
            boolean isAuthCorrect = ResourceConnectionController.dbApi.checkLoginAndPassword(ResourceConnectionController.getCurUrl(), log, pass);
            if (isAuthCorrect){
                onCancelBtnClick(actionEvent);
                showResourceApplications(mainWindow);
            }
            else {
                showErrorAuthorisationDialog(actionEvent);
            }
        } catch (NoSuchUserException ex) {
            try {
                String curUrl = ResourceConnectionController.getCurUrl();
                String url = curUrl.substring(0, curUrl.indexOf(":"));
                int port = Integer.parseInt(curUrl.substring(curUrl.indexOf(":") + 1, curUrl.length()));
                ResourceConnectionController.sshApi.closeSession(ResourceConnectionController.sshApi.initSession(url, port, log, pass));
                ResourceConnectionController.dbApi.addUserToResource(ResourceConnectionController.getCurUrl(), log, pass);
                onCancelBtnClick(actionEvent);
                showResourceApplications(mainWindow);
                
            } catch (JSchException ex1) {
                showErrorAuthorisationDialog(actionEvent);
            }
        }
    }
    
    public void onCancelBtnClick(ActionEvent actionEvent){
        ((Stage)(((Node)actionEvent.getSource()).getScene().getWindow())).close();    
    }
    private void showResourceApplications(Window mainWindow){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Applications.fxml"));
            Scene applicationsScene = new Scene(root);
            ((Stage)mainWindow).setScene(applicationsScene);
        } catch (IOException ex) {
            Logger.getLogger(AuthorisationWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void showErrorAuthorisationDialog(ActionEvent actionEvent){
        new ErrorDialog().showErrorDialog(actionEvent, "Error Authorisation", "Логин или пароль введен неверно!");
    }
}
