package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.shape.Ellipse;

public class LayoutMensajesController implements Initializable {

	@FXML
	private Ellipse fondoMensaje;

	@FXML
	private Label lblMensaje;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fondoMensaje.prefWidth(lblMensaje.getWidth());
	}

	public void setLblMensaje(String mensaje) {
		lblMensaje.setText(mensaje);
	}
	
	public void unirAnchoMensaje() {
		lblMensaje.setMinWidth(Region.USE_PREF_SIZE);
		fondoMensaje.minWidth(lblMensaje.getMinWidth());
		fondoMensaje.prefWidth(lblMensaje.getWidth());
	}

}
