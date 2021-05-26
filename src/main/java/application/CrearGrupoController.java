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

public class CrearGrupoController {

	@FXML
	private AnchorPane anchor;

	@FXML
	private TextField textFieldNombreGrupo;

	@FXML
	private TextField textFieldCategoria;

	@FXML
	private Button btnCrearGrupo;
	
    @FXML
    private Button btnAtras;

	@FXML
	private Button btnSalir;

	@FXML
	private TextArea textAreaDesc;

	@FXML
	void cerrarApp(ActionEvent event) {
		App.salirStage();
	}
	
	@FXML
	void irAtras(ActionEvent event) throws IOException {
		App.setRoot("principal");
		App.cambiarResAEscena();
	}

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
			e.printStackTrace();
		}
	}

}
