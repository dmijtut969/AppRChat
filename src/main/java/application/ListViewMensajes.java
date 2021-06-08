package application;

import java.io.IOException;

import dao.Mensaje;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextAlignment;

public class ListViewMensajes extends ListCell<Mensaje> {

	@Override
	public void updateItem(Mensaje mensaje, boolean empty) {
		super.updateItem(mensaje, empty);
		this.setStyle("-fx-background-color:#65c4c7");
		if (mensaje != null) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("mensajes.fxml"));
				loader.load();
				LayoutMensajesController controller = loader.getController();
				controller.setGrupoLabel(controller.crearLabel(mensaje.getMensaje(), "elliptical-label"));
				
			if (mensaje.isUsuarioActual()) {
				this.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
			} else {
				this.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
			}
			setGraphic(controller.crearLabel(mensaje.getMensaje(), "elliptical-label"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			setGraphic(null);
		}
	}
}
