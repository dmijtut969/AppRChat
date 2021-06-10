/*
 * @author Daniel Mijens Tutor
 */
package dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Instantiates a new grupos.
 */
@AllArgsConstructor

/**
 * Instantiates a new grupos.
 */
@NoArgsConstructor

/**
 * Gets the id grupo.
 *
 * @return the id grupo
 */
@Getter

/**
 * Sets the id grupo.
 *
 */
@Setter
public class Grupos {

	/** The creador. */
	private String creador;

	/** The fecha. */
	private String fecha;

	/** The nombre. */
	private String nombre;

	/** The desc. */
	private String desc;

	/** The categoria. */
	private String categoria;

	/** The usuario 1. */
	private String usuario1;

	/** The usuario 2. */
	private String usuario2;

	/** The usuario 3. */
	private String usuario3;

	/** The id grupo. */
	private Integer idGrupo;
	
	public Grupos(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		int contadorPersonas = contarPersonasDelGrupo();
		return ("(" + contadorPersonas + "/4) " + nombre);
	}

	/**
	 * Count how many people are in a group.
	 *
	 * @return People in a group.
	 */
	public int contarPersonasDelGrupo() {
		int contadorPersonas = 1;
		if (usuario1 != null)
			contadorPersonas++;
		if (usuario2 != null)
			contadorPersonas++;
		if (usuario3 != null)
			contadorPersonas++;
		return contadorPersonas;
	}
}
