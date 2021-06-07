package application;

import java.io.IOException;

import dao.Mensaje;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;

public class ListViewMensajes extends ListCell<Mensaje> {

	@Override
	public void updateItem(Mensaje mensaje, boolean empty) {
		super.updateItem(mensaje, empty);
		this.setStyle("-fx-background-color:#65c4c7");
		if (mensaje != null) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("mensajes.fxml"));
				AnchorPane cellLayout = (AnchorPane) loader.load();
				LayoutMensajesController controller = loader.getController();
				
				controller.setLblMensaje(mensaje.getMensaje());
				controller.unirAnchoMensaje();
			;
			if (mensaje.isUsuarioActual()) {
				this.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);				
			} else {
				this.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
			}
			setGraphic(cellLayout);
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		} else {
			setGraphic(null);
		}
	}
}
