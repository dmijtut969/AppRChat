/*
 * @author Daniel Mijens Tutor
 */
package utils;

import javafx.scene.control.Alert;

/**
 * Class CustomAlerta.
 */
public class CustomAlerta {
	
	/**
	 * Instantiates a new custom alerta.
	 */
	public CustomAlerta (Alert alerta, String title,String header,String content) {
		alerta.setTitle(title);
		alerta.setHeaderText(header);
		alerta.setContentText(content);
		alerta.show();
	}
}
