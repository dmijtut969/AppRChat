package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class RegistroController {

    @FXML
    private AnchorPane anchor;

    @FXML
    private TextField textFieldEmail;

    @FXML
    private TextField textFieldNombreUsuario;

    @FXML
    private PasswordField passFieldPassword;

    @FXML
    private PasswordField passFieldRepPassword;

    @FXML
    private Button btnTieneCuenta;

    @FXML
    private Button btnRegistrarse;

    @FXML
    private Button btnSalir;

    @FXML
    void TieneCuenta(ActionEvent event) throws IOException {
    	App.setRoot("iniciarSesion");
    	App.cambiarResAEscena();
    }

    @FXML
    void cerrarApp(ActionEvent event) {
    	App.salirStage();
    }

    @FXML
    void registrarse(ActionEvent event) {
    	
    }

}
