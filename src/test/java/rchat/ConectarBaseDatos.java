//package rchat;
//
//import static org.junit.jupiter.api.Assumptions.assumeTrue;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//
//import conector.Conector;
//
//public class ConectarBaseDatos {
//	Connection con;
//
//	@BeforeEach
//	public void iniciarConexion() {
//		System.out.println("-----PROBANDO CONEXION-----");
//		con = new Conector().getMySQLConnection();
//	}
//
//	@Test
//	public void testUsuarioPorId_OK() {
//		try (PreparedStatement prepStat = con.prepareStatement("select * from Usuario where idUsuario = 1")) {
//			ResultSet result = prepStat.executeQuery();
//			assumeTrue(result.next());
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//	@AfterEach
//	public void cerrarConexion() {
//		try {
//			con.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//}
