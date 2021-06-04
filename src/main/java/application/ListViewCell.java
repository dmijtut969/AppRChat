package application;

import dao.Grupos;
import dao.Mensaje;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;

public class ListViewCell extends ListCell<Mensaje> {

	@Override
	public void updateItem(Mensaje men, boolean empty) {
		super.updateItem(men, empty);
		if (men != null) {
			if (men.isEsDelUsuarioLoggeado()) {
				this.setNodeOrientation(getNodeOrientation().RIGHT_TO_LEFT);
				Label lbl = new Label();
				lbl.setText(men.getMensaje() + " " + men.getHora());
				HBox hbox = new HBox();
				hbox.getChildren().add(lbl);
				hbox.setStyle("-fx-text-background-color:green;");
				setGraphic(hbox);
			} else {
				this.setNodeOrientation(getNodeOrientation().LEFT_TO_RIGHT);
				this.setStyle("");
				Label lbl = new Label();
				lbl.setText( men.getEmisor() + " --> " + men.getMensaje() + " " + men.getHora());
				HBox hbox = new HBox();
				hbox.getChildren().add(lbl);
				hbox.setStyle("-fx-text-background-color:blue;");
				setGraphic(hbox);
			}
		}
	}
}
