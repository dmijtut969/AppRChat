package dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Grupos {
	
	private String creador;
	private String fecha;
	private String nombre;
	private String desc;
	private String categoria;
	private String usuario1;
	private String usuario2;
	private String usuario3;

	@Override
	public String toString() {
		int contadorPersonas = 1;
		if (usuario1!=null)
			contadorPersonas++;
		if (usuario2!=null)
			contadorPersonas++;
		if (usuario3!=null)
			contadorPersonas++;
		return ("(" + contadorPersonas + "/4) " +nombre);
	}
}
