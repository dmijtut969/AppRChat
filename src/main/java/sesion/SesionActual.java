package sesion;

public class SesionActual {
	private static Usuario usuarioActual = null;

	public static Usuario getUsuarioActual() {
		return usuarioActual;
	}

	public static void setUsuarioActual(Usuario usuarioActual) {
		SesionActual.usuarioActual = usuarioActual;
	}
}
