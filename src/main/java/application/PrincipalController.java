package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import conector.Conector;
import dao.Grupos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.NoArgsConstructor;
import sesion.SesionActual;

@NoArgsConstructor
public class PrincipalController implements Initializable {

	@FXML
	private AnchorPane anchor;

	@FXML
	private VBox separadorHeaderBody;

	@FXML
	private AnchorPane header;

	@FXML
	private Button btnSalir;

	@FXML
	private Button btnCerrarSesion;

	@FXML
	private Label lblBienvenidaUsuario;

	@FXML
	private HBox body;

	@FXML
	private VBox grupos;

	@FXML
	private ListView<Grupos> listViewGrupos;

	ObservableList<Grupos> obsListGrupos = FXCollections.observableArrayList();

	@FXML
	private Button btnCrearNuevoGrupo;

	@FXML
	private Button btnUnirseAGrupo;

	@FXML
	private VBox chat;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lblBienvenidaUsuario.setText("¡Bienvenido @" + SesionActual.getUsuarioActual().getNombreUsuario() + "!");
		mostrarMisGrupos();

	}

	@FXML
	void cerrarApp(ActionEvent event) {
		App.salirStage();
	}

	@FXML
	void cerrarSesion(ActionEvent event) throws IOException {
		SesionActual.setUsuarioActual(null);
		App.setRoot("iniciarsesion");
		App.cambiarResAEscena();
	}

	void mostrarMisGrupos() {
		try (Connection con = new Conector().getMySQLConnection()) {
			PreparedStatement sacarGruposUsuario = con.prepareStatement(
					"SELECT * FROM Grupos WHERE creador = ? " + "or usuario1 = ? or usuario2 = ? or usuario3 = ?");
			String nombreUsu = SesionActual.getUsuarioActual().getNombreUsuario();
			sacarGruposUsuario.setString(1, nombreUsu);
			sacarGruposUsuario.setString(2, nombreUsu);
			sacarGruposUsuario.setString(3, nombreUsu);
			sacarGruposUsuario.setString(4, nombreUsu);
			ResultSet result = sacarGruposUsuario.executeQuery();
			obsListGrupos.removeAll(obsListGrupos);
			while (result.next()) {
				obsListGrupos.add(new Grupos(result.getString(1), result.getString(2),
						result.getString(3), result.getString(4), result.getString(5), result.getString(6),
						result.getString(7), result.getString(8)));
			}
			listViewGrupos.setItems(obsListGrupos);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	@FXML
	void crearGrupo(ActionEvent event) throws IOException {
//		Stage creadorGrupo = new Stage();
//		Scene insertGrupo = new Scene(loadFXML("creacionGrupo"));
//		creadorGrupo.setScene(insertGrupo);
//		creadorGrupo.initStyle(StageStyle.UNDECORATED);
//		creadorGrupo.show();
		App.setRoot("creacionGrupo");
		App.cambiarResAEscena();
	}

	@FXML
	void unirseAGrupo(ActionEvent event) {

	}

}
