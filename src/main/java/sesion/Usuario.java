/*
 * @author Daniel Mijens Tutor
 */
package sesion;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Gets the email.
 *
 * @return the email
 */
@Getter

/**
 * Sets the email.
 *
 * @param email the new email
 */
@Setter

/**
 * Instantiates a new mensaje.
 */
@NoArgsConstructor
public class Usuario implements Comparable<Usuario>{
	
	/** The id. */
	private Integer id;
	
	/** The nombre usuario. */
	private String nombreUsuario;
	
	/** The password. */
	private String password;
	
	/** The email. */
	private String email;
	
	/**
	 * Instantiates a new mensaje.
	 *
	 * @param id the id
	 * @param nombreUsuario the nombre usuario
	 * @param password the password
	 * @param email the email
	 */
	public Usuario(int id,String nombreUsuario,String password,String email) {
		this.id = id;
		this.nombreUsuario = nombreUsuario;
		this.password = password;
		this.email = email;
	}

	/**
	 * Compare to.
	 *
	 * @param usuario the usuario
	 * @return the int
	 */
	@Override
	public int compareTo(Usuario usuario) {
		
		return this.getId().compareTo(usuario.getId());
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombreUsuario=" + nombreUsuario + ", password=" + password + ", email=" + email
				+ "]";
	}
	
}
