package sesion;

public class SesionActual {
	private static Mensaje usuarioActual = null;

	public static Mensaje getUsuarioActual() {
		return usuarioActual;
	}

	public static void setUsuarioActual(Mensaje usuarioActual) {
		SesionActual.usuarioActual = usuarioActual;
	}
}
