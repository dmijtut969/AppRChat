package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class IniciarSesionController {

    @FXML
    private AnchorPane anchor;

    @FXML
    private PasswordField passFieldPassword;

    @FXML
    private TextField textFieldNombreUsuario;

    @FXML
    private Button btnSalir;

    @FXML
    private Button btnNoTieneCuenta;

    @FXML
    private Button btnIniciarSesion;

    @FXML
    void cerrarApp(ActionEvent event) {
    	App.salirStage();
    }

    @FXML
    void iniciarSesion(ActionEvent event) {
    	System.out.println(textFieldNombreUsuario.getText() + " " + passFieldPassword.getText());
    }

    @FXML
    void noTieneCuenta(ActionEvent event) throws IOException {
    	App.setRoot("registro");
    	App.cambiarResAEscena();
    }
}
