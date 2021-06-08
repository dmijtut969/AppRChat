package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.DoubleBinding;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.TextAlignment;

public class LayoutMensajesController {

	@FXML
	private Group grupoLabel;

	public void setGrupoLabel(Group grupo) {
		grupoLabel = grupo;
	}

	public Group crearLabel(String mensaje, String styleClass) {

		Label label = new Label(mensaje);

		Ellipse ellipse = new Ellipse();
		ellipse.getStyleClass().add("ellipse");

		DoubleBinding halfWidth = label.widthProperty().divide(2).add(10);
		DoubleBinding halfHeight = label.widthProperty().divide(10).add(10);

		ellipse.radiusXProperty().bind(halfWidth);
		ellipse.radiusYProperty().bind(halfHeight);

		ellipse.centerXProperty().bind(halfWidth);
		ellipse.centerYProperty().bind(halfHeight);
		ellipse.setFill(javafx.scene.paint.Color.web("#616ac6"));

		label.setLayoutX(10);
		label.layoutYProperty().bind(halfHeight.subtract(label.heightProperty().divide(2)));
		label.setWrapText(true);
		label.setMaxWidth(600);
		label.setTextAlignment(TextAlignment.CENTER);
		label.setStyle("-fx-text-fill: white;");

		Group grupo = new Group(ellipse, label);
		grupo.getStyleClass().add(styleClass);

		return grupo;
	}

}
