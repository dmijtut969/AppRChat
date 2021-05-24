package utils;

import javafx.scene.control.Alert;

public class CustomAlerta {
	
	public CustomAlerta (Alert alerta, String title,String header,String content) {
		alerta.setTitle(title);
		alerta.setHeaderText(header);
		alerta.setContentText(content);
		alerta.show();
	}
}
