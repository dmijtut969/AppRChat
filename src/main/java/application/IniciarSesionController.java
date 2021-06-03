/*
 * @author Daniel Mijens Tutor
 */
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

/**
 * The Class IniciarSesionController.
 */
public class IniciarSesionController {

    /** The anchor. */
    @FXML
    private AnchorPane anchor;

    /** The pass field password. */
    @FXML
    private PasswordField passFieldPassword;

    /** The text field nombre usuario. */
    @FXML
    private TextField textFieldNombreUsuario;

    /** The btn salir. */
    @FXML
    private Button btnSalir;

    /** The btn no tiene cuenta. */
    @FXML
    private Button btnNoTieneCuenta;

    /** The btn iniciar sesion. */
    @FXML
    private Button btnIniciarSesion;

    /**
     * Cerrar app.
     *
     * @param event the event
     */
    @FXML
    void cerrarApp(ActionEvent event) {
    	App.salirStage();
    }

    /**
     * Iniciar sesion.
     *
     * @param event the event
     */
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

    /**
     * No tiene cuenta.
     *
     * @param event the event
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @FXML
    void noTieneCuenta(ActionEvent event) throws IOException {
    	App.setRoot("registro");
    	App.cambiarResAEscena();
    }
}
