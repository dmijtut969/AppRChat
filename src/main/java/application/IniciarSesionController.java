package application;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;

import conectorManager.UsuarioManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import sesion.SesionActual;
import utils.CustomAlerta;
import utils.CustomException;


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
    	try {
			if(UsuarioManager.findByNombre(textFieldNombreUsuario.getText()).getPassword().equals(passFieldPassword.getText())) {
				SesionActual.setUsuarioActual(UsuarioManager.findByNombre(textFieldNombreUsuario.getText()));
				App.setRoot("principal");
				App.cambiarResAEscena();
			}else {
				throw new CustomException("Contraseña incorrecta");
			}
		} catch (SQLTimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (CustomException e) {
			new CustomAlerta(new Alert(AlertType.WARNING), "Cuidado!", "Datos incorrectos",e.getMessage());
			;
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void noTieneCuenta(ActionEvent event) throws IOException {
    	App.setRoot("registro");
    	App.cambiarResAEscena();
    }
}
