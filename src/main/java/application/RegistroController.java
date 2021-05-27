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
		try {
			if (UsuarioManager.findByEmailBoolean(textFieldEmail.getText())) {
				throw new CustomException("Ya se ha registrado ese email");
			} else if (UsuarioManager.findByNombreBoolean(textFieldNombreUsuario.getText())) {
				throw new CustomException("Ya se ha registrado ese nombre de usuario");
			} else if (!passFieldPassword.getText().equals(passFieldRepPassword.getText())){
				throw new CustomException("No coinciden las passwords");
			}else {
				UsuarioManager.create(textFieldNombreUsuario.getText(), passFieldPassword.getText(),
						textFieldEmail.getText());
				App.setRoot("iniciarsesion");
				App.cambiarResAEscena();
			}
		} catch (

		SQLTimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CustomException e) {
			new CustomAlerta(new Alert(AlertType.WARNING), "Cuidado!", "No se ha podido registrar", e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
