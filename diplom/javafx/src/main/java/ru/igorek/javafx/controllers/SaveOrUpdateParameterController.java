package ru.igorek.javafx.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ru.igorek.core.model.Parameter;

/**
 * FXML Controller class
 *
 * @author Игорек
 */
public class SaveOrUpdateParameterController implements Initializable{
    @FXML
    private TextField nameTF;
    @FXML
    private TextArea descTA;
    @FXML
    private CheckBox isFlacgCB;
    
    private String oldName, oldDesc;
    private boolean oldVal;
    private static Parameter selParameter;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selParameter = ApplicationUpdatingController.getSelParam();
        oldName = selParameter.getParameterName();
        oldDesc = selParameter.getParameterDescription();
        oldVal = selParameter.isFlag();
        
        nameTF.setText(oldName);
        descTA.setText(oldDesc);
        isFlacgCB.setSelected(oldVal);
    }
    
    public void onOkBtnClick(ActionEvent actionEvent){
        if (nameTF.getText().equals("")){
            new ErrorDialog().showErrorDialog(actionEvent, "Input Error", "Поле 'Имя' является обязательны для заполнения!");
            return;
        }
        String newName = nameTF.getText(), newDesc = descTA.getText();
        boolean newVal = isFlacgCB.isSelected();
        if (oldName.equals(newName) && oldDesc.equals(newDesc)  && oldVal==newVal){
            onCancelBtnClick(actionEvent);
            return;
        }
        if (oldName.equals("")){
            Parameter parameter = ResourceConnectionController.dbApi.addParameterToApplication(ApplicationsController.getCurApplication().getApplicationId(), newName, newDesc, newVal);
            ApplicationUpdatingController.addParam(parameter);
        }
        else{
            selParameter.setParameterName(newName);
            selParameter.setParameterDescription(newDesc);
            selParameter.setFlag(newVal);

            ResourceConnectionController.dbApi.updateParameter(selParameter);
            ApplicationUpdatingController.refreshParameter();
        }
        onCancelBtnClick(actionEvent);
    }
    public void onCancelBtnClick(ActionEvent actionEvent){
        new AuthorisationWindowController().onCancelBtnClick(actionEvent);
    }
}
