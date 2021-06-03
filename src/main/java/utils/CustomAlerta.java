/*
 * 
 */
package utils;

import javafx.scene.control.Alert;

/**
 * Class CustomAlerta.
 * @author Daniel Mijens Tutor
 */
public class CustomAlerta {
	
	/**
	 * Instantiates a new custom alerta.
	 *
	 * @param alerta que se mostrara
	 * @param titulo de la alerta
	 * @param header de la alerta
	 * @param contenido de la alerta
	 */
	public CustomAlerta (Alert alerta, String title,String header,String content) {
		alerta.setTitle(title);
		alerta.setHeaderText(header);
		alerta.setContentText(content);
		alerta.show();
	}
}
