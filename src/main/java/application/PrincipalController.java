/*
 * @author Daniel Mijens Tutor
 */
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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.Duration;
import lombok.NoArgsConstructor;
import sesion.SesionActual;
import utils.CustomAlerta;
import utils.CustomException;

/**
 * Instantiates a new principal controller.
 */
@NoArgsConstructor
public class PrincipalController implements Initializable {

	/** The anchor. */
	@FXML
	private AnchorPane anchor;

	/** The separador header body. */
	@FXML
	private VBox separadorHeaderBody;

	/** The header. */
	@FXML
	private AnchorPane header;

	/** The btn salir. */
	@FXML
	private Button btnSalir;

	/** The btn cerrar sesion. */
	@FXML
	private Button btnCerrarSesion;

	/** The lbl bienvenida usuario. */
	@FXML
	private Label lblBienvenidaUsuario;

	/** The body. */
	@FXML
	private HBox body;

	/** The grupos. */
	@FXML
	private VBox grupos;

	/** The btn crear nuevo grupo. */
	@FXML
	private Button btnCrearNuevoGrupo;

	/** The btn unirse A grupo. */
	@FXML
	private Button btnUnirseAGrupo;

	/** The text field cat buscada. */
	@FXML
	private TextField textFieldCatBuscada;

	/** The chat. */
	@FXML
	private VBox chat;

	/** The lbl nombre grupo. */
	@FXML
	private Label lblNombreGrupo;

	/** The list view mis mensajes. */
	@FXML
	private ListView<Mensaje> listViewMisMensajes;

	/** The obs list mis mensajes. */
	ObservableList<Mensaje> obsListMisMensajes = FXCollections.observableArrayList();

	/** The text area mensaje. */
	@FXML
	private TextArea textAreaMensaje;

	/** The btn enviar mensaje. */
	@FXML
	private Button btnEnviarMensaje;

	/** The btn salir del grupo. */
	@FXML
	private Button btnSalirDelGrupo;

	/** The list view grupos. */
	@FXML
	private ListView<Grupos> listViewGrupos;

	/** The obs list grupos. */
	ObservableList<Grupos> obsListGrupos = FXCollections.observableArrayList();

	/**
	 * Initialize.
	 *
	 * @param location  the location
	 * @param resources the resources
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lblBienvenidaUsuario.setText("¡Bienvenido @" + SesionActual.getUsuarioActual().getNombreUsuario() + "!");
		efectoFadeNombreGrupo();
		listViewGrupos.setStyle("-fx-font-size: 1.5em;");
		listViewMisMensajes.setStyle("-fx-font-size: 1.3em;");
		listViewMisMensajes.setPadding(new Insets(0));
		chat.setVisible(false);
		mostrarMisGrupos();
	}

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
	 * Cerrar sesion.
	 *
	 * @param event the event
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@FXML
	void cerrarSesion(ActionEvent event) throws IOException {
		SesionActual.setUsuarioActual(null);
		App.setRoot("iniciarsesion");
		App.cambiarResAEscena();
	}

	/**
	 * Mostrar mis grupos.
	 */
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
		listViewGrupos.setItems(obsListGrupos);

	}

	/**
	 * Crear grupo.
	 *
	 * @param event the event
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@FXML
	void crearGrupo(ActionEvent event) throws IOException {
		App.setRoot("creacionGrupo");
		App.cambiarResAEscena();
	}

	/**
	 * Unirse A grupo.
	 *
	 * @param event the event
	 */
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

	/**
	 * Salir de grupo.
	 *
	 * @param event the event
	 * @throws SQLException the SQL exception
	 */
	@FXML
	void salirDeGrupo(ActionEvent event) throws SQLException {
		Grupos grupoSeleccionado = listViewGrupos.getSelectionModel().getSelectedItem();
		String usuarioActual = SesionActual.getUsuarioActual().getNombreUsuario();
		String usuarioABorrar = "";
		if (grupoSeleccionado.getCreador().equals(usuarioActual)) {
			borrarGrupo(new ActionEvent()); // Si el creador se sale del grupo se borrara el mismo
		} else if (!grupoSeleccionado.getUsuario1().equals(null)
				|| grupoSeleccionado.getUsuario1().equals(usuarioActual))
			usuarioABorrar = "usuario1";
		else if (!grupoSeleccionado.getUsuario2().equals(null) || grupoSeleccionado.getUsuario2().equals(usuarioActual))
			usuarioABorrar = "usuario2";
		else if (!grupoSeleccionado.getUsuario3().equals(null) || grupoSeleccionado.getUsuario3().equals(usuarioActual))
			usuarioABorrar = "usuario3";
		System.out.println(usuarioABorrar);
		if (!usuarioABorrar.equals("creador")) {
			try (Connection con = new Conector().getMySQLConnection()) {
				PreparedStatement salirse = con
						.prepareStatement("UPDATE Grupos SET " + usuarioABorrar + " = ? " + "where idGrupo = ?");
				salirse.setNull(1, Types.VARCHAR);
				salirse.setInt(2, grupoSeleccionado.getIdGrupo());
				if (salirse.executeUpdate() > 0) {
					mostrarMisGrupos();
					chat.setVisible(false);
				}

			}
		}
	}

	/**
	 * Borrar grupo.
	 *
	 * @param event the event
	 * @return true, if successful
	 * @throws SQLException the SQL exception
	 */
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

	/**
	 * Sacar grupo random.
	 *
	 * @return the grupos
	 */
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

	/**
	 * Mostrar mensajes selec.
	 *
	 * @param event the event
	 */
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

	/**
	 * Cambiar mensajes mostrados.
	 *
	 * @param grupoSeleccionado the grupo seleccionado
	 * @throws SQLTimeoutException the SQL timeout exception
	 * @throws SQLException        the SQL exception
	 */
	private void cambiarMensajesMostrados(Grupos grupoSeleccionado) throws SQLTimeoutException, SQLException {

		obsListMisMensajes.clear();
		for (Mensaje mensaje : MensajeManager.sacarUltimosMensajesGrupoConLimite(grupoSeleccionado.getIdGrupo(), 10)) {
			if (mensaje.getEmisor().equals(SesionActual.getUsuarioActual().getNombreUsuario())) {
				mensaje.setEsDelUsuarioLoggeado(true);
				obsListMisMensajes.add(mensaje);

			} else {
				mensaje.setEsDelUsuarioLoggeado(false);
				obsListMisMensajes.add(mensaje);
			}
		}
		listViewMisMensajes.setItems(obsListMisMensajes);
		listViewMisMensajes.setCellFactory(gruposListView -> new ListViewCell());
		listViewMisMensajes.setPadding(new Insets(0));
	}

	/**
	 * Enviar mensaje.
	 *
	 * @param event the event
	 */
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

	/**
	 * Efecto fade nombre grupo.
	 */
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
