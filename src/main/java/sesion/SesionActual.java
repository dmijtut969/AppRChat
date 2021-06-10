/*
 * @author Daniel Mijens Tutor
 */
package sesion;

/**
 * The Class SesionActual.
 */
public class SesionActual {
	
	/** Usuario actual. */
	private static Usuario usuarioActual = null;

	/**
	 * Gets the usuario actual.
	 *
	 * @return the usuario actual
	 */
	public static Usuario getUsuarioActual() {
		return usuarioActual;
	}

	/**
	 * Sets the usuario actual.
	 *
	 * @param usuarioActual the new usuario actual
	 */
	public static void setUsuarioActual(Usuario usuarioActual) {
		SesionActual.usuarioActual = usuarioActual;
	}
}
