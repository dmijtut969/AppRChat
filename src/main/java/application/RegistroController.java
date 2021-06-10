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
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import utils.CustomAlerta;
import utils.CustomException;

/**
 * The Class RegistroController.
 */
public class RegistroController {

	/** The principal anchor. */
	@FXML
	private AnchorPane anchor;

	/** The text field email. */
	@FXML
	private TextField textFieldEmail;

	/** The text field nombre usuario. */
	@FXML
	private TextField textFieldNombreUsuario;

	/** The pass field password. */
	@FXML
	private PasswordField passFieldPassword;

	/** The pass field rep password. */
	@FXML
	private PasswordField passFieldRepPassword;

	/** The btn tiene cuenta. */
	@FXML
	private Button btnTieneCuenta;

	/** The btn registrarse. */
	@FXML
	private Button btnRegistrarse;

	/** The btn salir. */
	@FXML
	private Button btnSalir;

	/**
	 * Switch to scene iniciarsesion.fxml
	 *
	 * @param event the event
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@FXML
	void TieneCuenta(ActionEvent event) throws IOException {
		App.setRoot("iniciarSesion");
		App.cambiarResAEscena();
	}

	/**
	 * Close the app.
	 *
	 * @param event the event
	 */
	@FXML
	void cerrarApp(ActionEvent event) {
		App.salirStage();
	}

	/**
	 * Register a new user in the database.
	 *
	 * @param event the event
	 */
	@FXML
	void registrarse(ActionEvent event) {
		try {
			if (textFieldEmail.getText().indexOf("@") < 0 || textFieldEmail.getText().isBlank()) {
				throw new CustomException("No es un email");
			} else if (UsuarioManager.findByEmailBoolean(textFieldEmail.getText())) {
				throw new CustomException("Ya se ha registrado ese email o lo has dejado en blanco");
			} else if (UsuarioManager.findByNombreBoolean(textFieldNombreUsuario.getText())||textFieldNombreUsuario.getText().isBlank()) {

				throw new CustomException("Ya se ha registrado ese nombre de usuario o lo has dejado en blancos");
			} else if (!passFieldPassword.getText().equals(passFieldRepPassword.getText())||passFieldPassword.getText().isBlank()) {
				throw new CustomException("No coinciden las passwords o lo has dejado en blanco");
			} else {
				UsuarioManager.create(textFieldNombreUsuario.getText(), passFieldPassword.getText(),
						textFieldEmail.getText());
				App.setRoot("iniciarsesion");
				App.cambiarResAEscena();
			}
		} catch (

		SQLTimeoutException e) {
			new CustomAlerta(new Alert(AlertType.WARNING), "Cuidado!", "Error al registrarse en la BBDD",
					e.getMessage());
		} catch (SQLException e) {
			new CustomAlerta(new Alert(AlertType.WARNING), "Cuidado!", "Error al registrarse en la BBDD",
					e.getMessage());
		} catch (CustomException e) {
			new CustomAlerta(new Alert(AlertType.WARNING), "Cuidado!", "No se ha podido registrar", e.getMessage());
		} catch (IOException e) {
			new CustomAlerta(new Alert(AlertType.WARNING), "Cuidado!", "Error al registrarse (IOException)",
					e.getMessage());
		}
	}

}
