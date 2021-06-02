package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLTimeoutException;
import java.sql.Types;
import java.util.ResourceBundle;

import conector.Conector;
import conectorManager.MensajeManager;
import dao.Grupos;
import dao.Mensaje;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import lombok.NoArgsConstructor;
import sesion.SesionActual;
import utils.CustomAlerta;
import utils.CustomException;

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
	private Button btnCrearNuevoGrupo;

	@FXML
	private Button btnUnirseAGrupo;

	@FXML
	private TextField textFieldCatBuscada;

	@FXML
	private VBox chat;

	@FXML
	private Label lblNombreGrupo;

	@FXML
	private ListView<Mensaje> listViewExtMensajes;

	ObservableList<Mensaje> obsListExtMensajes = FXCollections.observableArrayList();

	@FXML
	private ListView<Mensaje> listViewMisMensajes;

	ObservableList<Mensaje> obsListMisMensajes = FXCollections.observableArrayList();

	@FXML
	private TextArea textAreaMensaje;

	@FXML
	private Button btnEnviarMensaje;

	@FXML
	private Button btnSalirDelGrupo;

	@FXML
	private ListView<Grupos> listViewGrupos;

	ObservableList<Grupos> obsListGrupos = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lblBienvenidaUsuario.setText("¡Bienvenido @" + SesionActual.getUsuarioActual().getNombreUsuario() + "!");
		efectoFadeNombreGrupo();
		listViewGrupos.setStyle("-fx-font-size: 1.5em;");
		listViewMisMensajes.setStyle("-fx-font-size: 1.3em;");
		listViewExtMensajes.setStyle("-fx-font-size: 1.3em;");
		listViewMisMensajes.setPadding(new Insets(0));
		listViewExtMensajes.setPadding(new Insets(0));
		chat.setVisible(false);
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
				obsListGrupos.add(new Grupos(result.getString(1), result.getString(2), result.getString(3),
						result.getString(4), result.getString(5), result.getString(6), result.getString(7),
						result.getString(8), result.getInt(9)));
			}
			listViewGrupos.setItems(obsListGrupos);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	@FXML
	void crearGrupo(ActionEvent event) throws IOException {
		App.setRoot("creacionGrupo");
		App.cambiarResAEscena();
	}

	@FXML
	void unirseAGrupo(ActionEvent event) {

		try (Connection con = new Conector().getMySQLConnection()) {
			Grupos grupoRandom = sacarGrupoRandom();
			if (grupoRandom != null) {
				String usuarioNulo = "";
				if (grupoRandom.getUsuario1() == null) {
					usuarioNulo = "usuario1";
				} else if (grupoRandom.getUsuario2() == null) {
					usuarioNulo = "usuario2";
				} else {
					usuarioNulo = "usuario3";
				}
				PreparedStatement unirse = con.prepareStatement(
						"UPDATE Grupos SET " + usuarioNulo + " = ? " + "where creador = ? and fecha = ?");
				unirse.setString(1, SesionActual.getUsuarioActual().getNombreUsuario());
				unirse.setString(2, grupoRandom.getCreador());
				unirse.setString(3, grupoRandom.getFecha());
				unirse.executeUpdate();
				System.out.println("Ejecutada update");
				mostrarMisGrupos();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void salirDeGrupo(ActionEvent event) throws SQLException {
		Grupos grupoSeleccionado = listViewGrupos.getSelectionModel().getSelectedItem();
		String usuarioActual = SesionActual.getUsuarioActual().getNombreUsuario();
		String usuarioABorrar = "";
		if (grupoSeleccionado.getCreador().equals(usuarioActual)) {
			borrarGrupo(new ActionEvent()); // Si el creador se sale del grupo se borrara el mismo
		}else if (!grupoSeleccionado.getUsuario1().equals(null) || grupoSeleccionado.getUsuario1().equals(usuarioActual))
			usuarioABorrar = "usuario1";
		else if (!grupoSeleccionado.getUsuario2().equals(null) || grupoSeleccionado.getUsuario2().equals(usuarioActual))
			usuarioABorrar = "usuario2";
		else if (!grupoSeleccionado.getUsuario3().equals(null) || grupoSeleccionado.getUsuario3().equals(usuarioActual))
			usuarioABorrar = "usuario3";		System.out.println(usuarioABorrar);
		if (!usuarioABorrar.equals("creador")) {
			try (Connection con = new Conector().getMySQLConnection()) {
				PreparedStatement salirse = con
						.prepareStatement("UPDATE Grupos SET " + usuarioABorrar + " = ? " + "where idGrupo = ?");
				salirse.setNull(1, Types.VARCHAR);
				salirse.setInt(2, grupoSeleccionado.getIdGrupo());
				if (salirse.executeUpdate()>0) {
					mostrarMisGrupos();
					chat.setVisible(false);
				}

			}
		}
	}

	@FXML
	boolean borrarGrupo(ActionEvent event) throws SQLException {
		Grupos grupoSeleccionado = listViewGrupos.getSelectionModel().getSelectedItem();
		try (Connection con = new Conector().getMySQLConnection()) {
			PreparedStatement query = con.prepareStatement("DELETE FROM Grupos WHERE idGrupo = ?");
			query.setInt(1, grupoSeleccionado.getIdGrupo());
			if (query.executeUpdate() > 0) {
				mostrarMisGrupos();
				chat.setVisible(false);
				return true;
			} else
				return false;
		}
	}

	private Grupos sacarGrupoRandom() {
		try (Connection con = new Conector().getMySQLConnection()) {
			PreparedStatement sacarGrupoRandom = con.prepareStatement(
					"select * from Grupos where categoria like ? and creador <> ? and ((usuario1 is null or usuario1 <> ?) and (usuario2 is null or usuario2 <> ?) and "
							+ "(usuario3 is null or usuario3 <> ?)) order by rand() limit 1");
			sacarGrupoRandom.setString(1, "%" + textFieldCatBuscada.getText() + "%");
			sacarGrupoRandom.setString(2, SesionActual.getUsuarioActual().getNombreUsuario());
			sacarGrupoRandom.setString(3, SesionActual.getUsuarioActual().getNombreUsuario());
			sacarGrupoRandom.setString(4, SesionActual.getUsuarioActual().getNombreUsuario());
			sacarGrupoRandom.setString(5, SesionActual.getUsuarioActual().getNombreUsuario());
			ResultSet result = sacarGrupoRandom.executeQuery();
			if (!result.next())
				throw new CustomException("No hay grupos con huecos o con esa categoria, ¡crea uno!");

			return new Grupos(result.getString(1), result.getString(2), result.getString(3), result.getString(4),
					result.getString(5), result.getString(6), result.getString(7), result.getString(8),
					result.getInt(9));
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (CustomException e) {
			new CustomAlerta(new Alert(AlertType.WARNING), "Cuidado!", "Error con los grupos", e.getMessage());
		}
		return null;
	}

	@FXML
	void mostrarMensajesSelec(MouseEvent event) {
		chat.setVisible(true);
		Grupos grupoSeleccionado = listViewGrupos.getSelectionModel().getSelectedItem();
		lblNombreGrupo.setText(grupoSeleccionado.getNombre());
		try {
			cambiarMensajesMostrados(grupoSeleccionado);
		} catch (SQLTimeoutException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void cambiarMensajesMostrados(Grupos grupoSeleccionado) throws SQLTimeoutException, SQLException {
		obsListExtMensajes.clear();
		obsListMisMensajes.clear();
		for (Mensaje mensaje : MensajeManager.sacarUltimosMensajesGrupoConLimite(grupoSeleccionado.getIdGrupo(), 10)) {
			if (mensaje.getEmisor().equals(SesionActual.getUsuarioActual().getNombreUsuario())) {
				obsListMisMensajes.add(mensaje);
				obsListExtMensajes.add(new Mensaje());
			} else {
				obsListExtMensajes.add(mensaje);
				obsListMisMensajes.add(new Mensaje());
			}
		}
		listViewExtMensajes.setItems(obsListExtMensajes);
		listViewMisMensajes.setItems(obsListMisMensajes);
	}

	@FXML
	void enviarMensaje(ActionEvent event) {
		try {
			Grupos grupoSeleccionado = listViewGrupos.getSelectionModel().getSelectedItem();
			MensajeManager.crearMensaje(new Mensaje(grupoSeleccionado.getIdGrupo(),
					SesionActual.getUsuarioActual().getNombreUsuario(), textAreaMensaje.getText()));
			textAreaMensaje.clear();
			cambiarMensajesMostrados(grupoSeleccionado);
		} catch (SQLIntegrityConstraintViolationException e) {
			e.printStackTrace();
		} catch (SQLTimeoutException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CustomException e) {
			e.printStackTrace();
		}
	}

	private void efectoFadeNombreGrupo() {
		FadeTransition fade = new FadeTransition();
		fade.setDuration(Duration.millis(2000));
		fade.setFromValue(10);
		fade.setToValue(0);
		fade.setCycleCount(Animation.INDEFINITE);
		fade.setAutoReverse(true);
		fade.setNode(lblNombreGrupo);
		fade.play();
	}
}
