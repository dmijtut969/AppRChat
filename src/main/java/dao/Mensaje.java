package dao;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Mensaje {
	
	

	private int idGrupo;
	private String emisor;
	private String mensaje;
	private String hora;
	
	public Mensaje(int idGrupo, String emisor, String mensaje) {
		super();
		this.idGrupo = idGrupo;
		this.emisor = emisor;
		this.mensaje = mensaje;
		this.hora = LocalTime.now().toString().substring(0,8);
	}
	
	public Mensaje() {
		super();
		this.emisor = "";
		this.mensaje = "";
		this.hora = "";
	}
	
	@Override
	public String toString() {					
		return emisor + "     " + mensaje + "     " + this.hora; 
	}
	
	
	
}
