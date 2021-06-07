/*
 * @author Daniel Mijens Tutor
 */
package dao;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Gets the hora.
 *
 * @return the hora
 */
@Getter

/**
 * Sets the hora.
 *
 * @param hora the new hora
 */
@Setter

/**
 * Instantiates a new mensaje.
 *
 * @param idMensaje the id mensaje
 * @param idGrupo the id grupo
 * @param emisor the emisor
 * @param mensaje the mensaje
 * @param hora the hora
 */
@AllArgsConstructor
public class Mensaje {
	
	
	/** The id mensaje. */
	private int idMensaje;
	
	/** The id grupo. */
	private int idGrupo;
	
	/** The emisor. */
	private String emisor;
	
	/** The mensaje. */
	private String mensaje;
	
	/** The hora. */
	private String hora;
	
	private boolean usuarioActual;
	
	/**
	 * Instantiates a new mensaje.
	 *
	 * @param idGrupo the id grupo
	 * @param emisor the emisor
	 * @param mensaje the mensaje
	 */
	public Mensaje(int idGrupo, String emisor, String mensaje) {
		super();
		this.idGrupo = idGrupo;
		this.emisor = emisor;
		this.mensaje = mensaje;
		this.hora = LocalTime.now().toString().substring(0,8);
	}
	
//	public Mensaje(int idMensaje,int idGrupo, String emisor, String mensaje) {
//		super();
//		this.idMensaje = idMensaje;
//		this.idGrupo = idGrupo;
//		this.emisor = emisor;
//		this.mensaje = mensaje;
//		this.hora = LocalTime.now().toString().substring(0,8);
//	}
	
	/**
 * Instantiates a new mensaje.
 */
public Mensaje() {
		super();
		this.emisor = "";
		this.mensaje = "";
		this.hora = "";
	}
	
	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {					
		return emisor + "     " + mensaje + "     " + this.hora; 
	}
	@Override
	public boolean equals(Object obj) {
	
		Mensaje other = (Mensaje) obj;
		if (idMensaje != other.idMensaje)
			return false;		
		return true;
	}
	
	
	
}
