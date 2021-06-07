/*
 * 
 */
package sesion;

/**
 * The Class SesionActual.
 * @author Daniel Mijens Tutor
 */
public class SesionActual {
	
	/** Usuario actual. */
	private static Mensaje usuarioActual = null;

	/**
	 * Gets the usuario actual.
	 *
	 * @return the usuario actual
	 */
	public static Mensaje getUsuarioActual() {
		return usuarioActual;
	}

	/**
	 * Sets the usuario actual.
	 *
	 * @param usuarioActual the new usuario actual
	 */
	public static void setUsuarioActual(Mensaje usuarioActual) {
		SesionActual.usuarioActual = usuarioActual;
	}
}
