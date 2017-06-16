package ru.igorek.javafx.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ru.igorek.core.model.Application;

/**
 * FXML Controller class
 *
 * @author Игорек
 */
public class ApplRegFormController{
    @FXML
    private TextField nameTF, pathTF;
    @FXML
    private TextArea descTF;
            
    public void onSaveBtnClick(ActionEvent actionEvent){
        String name = nameTF.getText();
        String path = pathTF.getText();
        if (name.equals("") || path.equals("")){
            new ErrorDialog().showErrorDialog(actionEvent, "Ошибка ввода", "Поля 'Имя' и 'Путь' обязательны для заполнения!");
            return;
        }
        Application application = ResourceConnectionController.dbApi.addApplicationToResource(ResourceConnectionController.getCurUrl(), name, descTF.getText(), path);
        ApplicationsController.add(application);
        onCancelBtnClick(actionEvent);
    }
    
    public void onCancelBtnClick(ActionEvent actionEvent){
        new AuthorisationWindowController().onCancelBtnClick(actionEvent);
    }
}
