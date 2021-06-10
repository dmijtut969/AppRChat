/*
 * @author Daniel Mijens Tutor
 */
package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;


/**
 * JavaFX App.
 */
public class App extends Application {

    /** scene  La escene. */
    private static Scene scene;
    
    /**
     * Start.
     *
     * @param stage the stage
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("iniciarsesion"));
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    /**
     * Sets the root.
     *
     * @param fxml the new root
     * @throws IOException Signals that an I/O exception has occurred.
     */
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * Load FXML.
     *
     * @param fxml the fxml
     * @return the parent
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        launch();
    }
    
    /**
     * Change to the new resolution.
     */
    public static void cambiarResAEscena() {
    	((Stage)scene.getWindow()).sizeToScene();
    	((Stage)scene.getWindow()).centerOnScreen();
    }
    
    /**
     * Exit stage.
     */
    public static void salirStage() {
    	((Stage)scene.getWindow()).close();
    }

}