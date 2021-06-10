/*
 * @author Daniel Mijens Tutor
 */
package application;

import java.io.IOException;

import dao.Mensaje;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;

/**
 * The Class ListViewMensajes.
 */
public class ListViewMensajes extends ListCell<Mensaje> {

	/**
	 * Update item(cell).
	 *
	 * @param mensaje the mensaje
	 * @param empty the empty
	 */
	@Override
	public void updateItem(Mensaje mensaje, boolean empty) {
		super.updateItem(mensaje, empty);
		this.setStyle("-fx-background-color:#65c4c7");
		if (mensaje != null) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("mensajes.fxml"));
				loader.load();
				LayoutMensajesController controller = loader.getController();
				controller.setGrupoLabel(controller.crearGroup(mensaje, "elliptical-label"));
				VBox vbox = new VBox();
				Label lblEmisor = new Label();
				vbox.getChildren().add(controller.crearGroup(mensaje, "elliptical-label"));
				lblEmisor.setText(mensaje.getEmisor() + " - " + mensaje.getHora());
				lblEmisor.setStyle("-fx-text-fill: white;");
				vbox.getChildren().add(lblEmisor);
				if (mensaje.isUsuarioActual()) {
					this.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
				} else {
					this.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
				}
				vbox.setPadding(new Insets(0));
				setGraphic(vbox);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			setGraphic(null);
		}
	}
}
