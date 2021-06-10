/*
 * @author Daniel Mijens Tutor
 */
package application;

import dao.Grupos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

/**
 * The Class ListViewGrupos.
 */
public class ListViewGrupos extends ListCell<Grupos> {

	/**
	 * Update item(cell).
	 *
	 * @param grupo the grupo
	 * @param empty the empty
	 */
	@Override
	public void updateItem(Grupos grupo, boolean empty) {
		super.updateItem(grupo, empty);
		this.setStyle("-fx-background-color:#65c4c7");
		if (grupo != null) {
			HBox hbox = new HBox();
			Label lblNombreGrupo = new Label(grupo.getNombre());
			Label lblPersonasGrupo = new Label("(" + grupo.contarPersonasDelGrupo() +"/4)");
			lblNombreGrupo.setStyle("-fx-text-fill: white;");
			lblPersonasGrupo.setStyle("-fx-text-fill: white;");
			hbox.getChildren().add(lblNombreGrupo);
			hbox.getChildren().add(lblPersonasGrupo);
			hbox.setSpacing(5);
			hbox.setPadding(new Insets(0));
			setGraphic(hbox);

		} else {
			setGraphic(null);
		}
	}
}
