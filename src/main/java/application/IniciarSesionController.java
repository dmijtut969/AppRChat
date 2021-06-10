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
import mail.CredentialsConstants;
import mail.Sender;
import sesion.SesionActual;
import sesion.Usuario;
import utils.CustomAlerta;
import utils.CustomException;

/**
 * The Class IniciarSesionController.
 */
public class IniciarSesionController {

	/** The principal anchor. */
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

	/** The btn recordar contra. */
	@FXML
	private Button btnRecordarContra;

	/** The txt field email contra. */
	@FXML
	private TextField txtFieldEmailContra;

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
	 * Login and show principal.fxml 
	 *
	 * @param event the event
	 */
	@FXML
	void iniciarSesion(ActionEvent event) {
		try {
			if (UsuarioManager.findByNombre(textFieldNombreUsuario.getText()).getPassword()
					.equals(passFieldPassword.getText())) {
				SesionActual.setUsuarioActual(UsuarioManager.findByNombre(textFieldNombreUsuario.getText()));
				App.setRoot("principal");
				App.cambiarResAEscena();
			} else {
				throw new CustomException("Contraseña incorrecta");
			}
		} catch (SQLTimeoutException e) {
			new CustomAlerta(new Alert(AlertType.WARNING), "Cuidado!", "Error al iniciar sesion en la BBDD", e.getMessage());
		} catch (SQLException e) {
			new CustomAlerta(new Alert(AlertType.WARNING), "Cuidado!", "Error al iniciar sesion en la BBDD", e.getMessage());
		} catch (CustomException e) {
			new CustomAlerta(new Alert(AlertType.WARNING), "Cuidado!", "Datos incorrectos", e.getMessage());
			;
		} catch (IOException e) {
			new CustomAlerta(new Alert(AlertType.WARNING), "Cuidado!", "Error al iniciar sesion (IOException)", e.getMessage());
		}
	}

	/**
	 * Show registro.fxml 
	 *
	 * @param event the event
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@FXML
	void noTieneCuenta(ActionEvent event) throws IOException {
		App.setRoot("registro");
		App.cambiarResAEscena();
	}

	/**
	 * Send the password of an email passed by parameter.
	 *
	 * @param event the event
	 */
	@FXML
	void recordarContra(ActionEvent event) {

		try {
			Usuario usuarioARecordar = UsuarioManager.findByEmail(txtFieldEmailContra.getText());
			System.out.println(usuarioARecordar);
			if (usuarioARecordar == null) {
				new CustomAlerta(new Alert(AlertType.WARNING), "Cuidado!", "Error con el email",
						"No se encuentra el email");
			} else {
				new Sender().send(CredentialsConstants.USER, txtFieldEmailContra.getText(),
						"Recordar contraseña de RChat",
						"Su nombre de usuario es : " + usuarioARecordar.getNombreUsuario() + "\nSu contraseña es : "
								+ usuarioARecordar.getPassword());
				new CustomAlerta(new Alert(AlertType.INFORMATION), "Listo!",
						"Se ha encontrado un usuario con ese email", "Revisa su correo y la carpeta de SPAM");
			}
		} catch (SQLTimeoutException e) {
			new CustomAlerta(new Alert(AlertType.ERROR), "Cuidado!", "Error SQLTimeoutException", e.getMessage());
		} catch (SQLException e) {
			new CustomAlerta(new Alert(AlertType.ERROR), "Cuidado!", "Error SQLException", e.getMessage());
		} catch (CustomException e) {
			new CustomAlerta(new Alert(AlertType.WARNING), "Cuidado!", "Error con el email", e.getMessage());
		}
	}
}
