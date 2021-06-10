/*
 * @author Daniel Mijens Tutor
 */
package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import conector.Conector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import sesion.SesionActual;
import utils.CustomAlerta;
import utils.CustomException;

/**
 * The Class CrearGrupoController.
 */
public class CrearGrupoController {

	/** The principal anchor. */
	@FXML
	private AnchorPane anchor;

	/** The text field nombre grupo. */
	@FXML
	private TextField textFieldNombreGrupo;

	/** The text field categoria. */
	@FXML
	private TextField textFieldCategoria;

	/** The btn crear grupo. */
	@FXML
	private Button btnCrearGrupo;
	
    /** The btn atras. */
    @FXML
    private Button btnAtras;

	/** The btn salir. */
	@FXML
	private Button btnSalir;

	/** The text area desc. */
	@FXML
	private TextArea textAreaDesc;

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
	 * Ir atras.
	 *
	 * @param event the event
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@FXML
	void irAtras(ActionEvent event) throws IOException {
		App.setRoot("principal");
		App.cambiarResAEscena();
	}

	/**
	 * Crear un nuevo grupo.
	 *
	 * @param event the event
	 */
	@FXML
	void crearGrupo(ActionEvent event) {
		try (Connection con = new Conector().getMySQLConnection()) {
			if (textFieldNombreGrupo.getText() == "" ||textFieldNombreGrupo.getText()==null )
				throw new CustomException("Nombre de usuario en blanco");
			if (textFieldCategoria.getText() == "" ||textFieldCategoria.getText()==null )
				throw new CustomException("Categoria en blanco");
			String hoy = LocalDate.now().toString();
			PreparedStatement query = con
					.prepareStatement("INSERT INTO Grupos(creador,fecha,nombre,descrip,categoria) VALUES (?,?,?,?,?)");
			query.setString(1, SesionActual.getUsuarioActual().getNombreUsuario());
			query.setString(2, hoy.substring(0, 10));
			query.setString(3, textFieldNombreGrupo.getText());
			query.setString(4, textAreaDesc.getText());
			query.setString(5, textFieldCategoria.getText());
			query.executeUpdate();
			App.setRoot("principal");
			App.cambiarResAEscena();
		} catch (CustomException e) {
			new CustomAlerta(new Alert(AlertType.WARNING), "¡Cuidado!", "Datos incorrectos", e.getMessage());
		} catch (SQLException e) {
			new CustomAlerta(new Alert(AlertType.WARNING), "¡Cuidado!", "Datos incorrectos", e.getMessage());
		} catch (IOException e) {
			new CustomAlerta(new Alert(AlertType.WARNING), "Cuidado!", "Error al eliminar mensaje (IOException)", e.getMessage());
		}
	}

}
