package ru.igorek.javafx;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.igorek.javafx.controllers.ResourceConnectionController;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.igorek.core.dao.SshApi;
import ru.igorek.core.utils.HibernateUtil;


public class MainApp extends Application {
    @Override
    public void start(Stage stage){
        Parent root;
        Scene scene;
        try{
            root = FXMLLoader.load(getClass().getResource("/fxml/ResourceConnection.fxml"));
            scene = new Scene(root);
            scene.getStylesheets().add("/styles/Styles.css");

            stage.setTitle("Java Application Starter");
            stage.setMinWidth(300);
            stage.setMinHeight(200);
        }
        catch(Throwable ex){
            try {
                root = FXMLLoader.load(getClass().getResource("/fxml/ResourceConnection.fxml"));
            } catch (IOException ex1) {
                root = null;
            }
            
            stage.setTitle("No internate connection");
        }
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @Override
    public void stop() throws Exception {
        HibernateUtil.closeSessionFactory();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch();
    }
    
    public void showForm(Event actionEvent, String path, String title){
        try {
            Stage stage = new Stage();
            Parent auth = FXMLLoader.load(getClass().getResource(path));
            Scene scene = new Scene(auth);
            stage.setTitle(title);
            stage.setResizable(false);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
