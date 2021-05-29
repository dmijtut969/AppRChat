package application;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLTimeoutException;
import java.util.ResourceBundle;

import conector.Conector;
import conectorManager.MensajeManager;
import dao.Grupos;
import dao.Mensaje;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    private ListView<Grupos> listViewGrupos;
	
	ObservableList<Grupos> obsListGrupos = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lblBienvenidaUsuario.setText("¡Bienvenido @" + SesionActual.getUsuarioActual().getNombreUsuario() + "!");
		listViewGrupos.setStyle("-fx-font-size: 1.5em;");
		listViewMisMensajes.setStyle("-fx-font-size: 1.5em;");
		listViewExtMensajes.setStyle("-fx-font-size: 1.5em;");
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
				obsListGrupos.add(
						new Grupos(result.getString(1), result.getString(2), result.getString(3), result.getString(4),
								result.getString(5), result.getString(6), result.getString(7), result.getString(8),result.getInt(9)));
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
					result.getString(5), result.getString(6), result.getString(7), result.getString(8),result.getInt(9));
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
			obsListExtMensajes.clear();
			obsListExtMensajes.addAll(MensajeManager.sacarUltimosMensajesGrupoConLimite(grupoSeleccionado.getIdGrupo(),10));
			listViewExtMensajes.setItems(obsListExtMensajes);
			obsListMisMensajes.clear();
			obsListMisMensajes.addAll(MensajeManager.sacarUltimosMensajesGrupoConLimite(grupoSeleccionado.getIdGrupo(),10));
			listViewMisMensajes.setItems(obsListMisMensajes);
		} catch (SQLTimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	void enviarMensaje(ActionEvent event) {
		try {
			MensajeManager.create(new Mensaje(listViewGrupos.getSelectionModel().getSelectedItem().getIdGrupo()
					,SesionActual.getUsuarioActual().getNombreUsuario(),textAreaMensaje.getText()));
			textAreaMensaje.clear();
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
}
