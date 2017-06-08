package ru.igorek.javafx.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ru.igorek.core.model.Application;
import ru.igorek.javafx.MainApp;

/**
 * FXML Controller class
 *
 * @author Игорек
 */
public class ApplicationUpdatingController implements Initializable{
    @FXML
    private TextArea descTF;
    @FXML
    private TextField nameTF, pathTF;
    
    private final String appUpdPath = "/fxml/ApplicationUpdSaving.fxml";
    private static Application savingApplication;
    private static String name, desc, path;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        savingApplication = ApplicationsController.getCurApplication();
        nameTF.setText(savingApplication.getName());
        descTF.setText(savingApplication.getDescription());
        pathTF.setText(savingApplication.getPath());
    }
    
    public void onSaveBtnClick(ActionEvent actionEvent){
        if (nameTF.getText().equals("") || pathTF.getText().equals("")){
            new ErrorDialog().showErrorDialog(actionEvent, "Input Error", "Поля 'Имя' и 'Путь' обязательны для заполнения!");
            return;
        }
        name = nameTF.getText();
        desc = descTF.getText();
        path = pathTF.getText();
        new MainApp().showForm(actionEvent, appUpdPath, "Dialog");
    }
    public void onCancelBtnClick(ActionEvent actionEvent){
        new AuthorisationWindowController().onCancelBtnClick(actionEvent);
    }
    public void saveChanges(){
        savingApplication.setName(name);
        savingApplication.setDescription(desc);
        savingApplication.setPath(path);
        ResourceConnectionController.dbApi.updateApplication(savingApplication);
        ApplicationsController.refresh();
    }
}
