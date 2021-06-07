/*
 * @author Daniel Mijens Tutor
 */
package conectorManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLTimeoutException;

import conector.Conector;
import sesion.Mensaje;
import utils.CustomException;

/**
 * The Class UsuarioManager.
 */
public class UsuarioManager {

	/**
	 * Find by id.
	 *
	 * @param idUsuario the id usuario
	 * @return the mensaje
	 * @throws SQLTimeoutException the SQL timeout exception
	 * @throws SQLException the SQL exception
	 */
	public static Mensaje findById(int idUsuario) throws SQLTimeoutException, SQLException {
		try (Connection con = new Conector().getMySQLConnection()) {
			PreparedStatement query = con.prepareStatement("SELECT * FROM Usuario WHERE idUsuario = ?");
			query.setInt(1, idUsuario);
			query.execute();
			ResultSet result = query.getResultSet();
			if (!result.next()) {
				System.out.println("No existe el usuario con esa id");
			}
			return new Mensaje(result.getInt("idUsuario"), result.getString("nombre_usuario"),
					result.getString("password"), result.getString("email"));
		}
	}

	/**
	 * Find by nombre.
	 *
	 * @param nombreUsu the nombre usu
	 * @return the mensaje
	 * @throws SQLTimeoutException the SQL timeout exception
	 * @throws SQLException the SQL exception
	 * @throws CustomException the custom exception
	 */
	public static Mensaje findByNombre(String nombreUsu) throws SQLTimeoutException, SQLException, CustomException {
		try (Connection con = new Conector().getMySQLConnection()) {
			PreparedStatement query = con.prepareStatement("SELECT * FROM Usuario WHERE nombre_usuario = ?");
			query.setString(1, nombreUsu);
			query.execute();
			ResultSet result = query.getResultSet();
			if (!result.next()) {
				throw new CustomException("No existe el usuario");
			}
			return new Mensaje(result.getInt("idUsuario"), result.getString("nombre_usuario"),
					result.getString("password"), result.getString("email"));
		}
	}
	
	/**
	 * Find by nombre boolean.
	 *
	 * @param nombreUsu the nombre usu
	 * @return true, if successful
	 * @throws SQLTimeoutException the SQL timeout exception
	 * @throws SQLException the SQL exception
	 * @throws CustomException the custom exception
	 */
	public static boolean findByNombreBoolean(String nombreUsu) throws SQLTimeoutException, SQLException, CustomException {
		try (Connection con = new Conector().getMySQLConnection()) {
			PreparedStatement query = con.prepareStatement("SELECT * FROM Usuario WHERE nombre_usuario = ?");
			query.setString(1, nombreUsu);
			query.execute();
			ResultSet result = query.getResultSet();
			return !result.next() ? false : true;
		}
	}

	/**
	 * Find by email.
	 *
	 * @param email the email
	 * @return the mensaje
	 * @throws SQLTimeoutException the SQL timeout exception
	 * @throws SQLException the SQL exception
	 * @throws CustomException the custom exception
	 */
	public static Mensaje findByEmail(String email) throws SQLTimeoutException, SQLException, CustomException {
		try (Connection con = new Conector().getMySQLConnection()) {
			PreparedStatement query = con.prepareStatement("SELECT * FROM Usuario WHERE email = ?");
			query.setString(1, email);
			query.execute();
			ResultSet result = query.getResultSet();
			if (!result.next()) {
				throw new CustomException("No existe ese usuario con ese email");
			}
			return new Mensaje(result.getInt("idUsuario"), result.getString("nombre_usuario"),
					result.getString("password"), result.getString("email"));
		}
	}

	/**
	 * Find by email boolean.
	 *
	 * @param email the email
	 * @return true, if successful
	 * @throws SQLTimeoutException the SQL timeout exception
	 * @throws SQLException the SQL exception
	 * @throws CustomException the custom exception
	 */
	public static boolean findByEmailBoolean(String email) throws SQLTimeoutException, SQLException, CustomException {
		try (Connection con = new Conector().getMySQLConnection()) {
			PreparedStatement query = con.prepareStatement("SELECT * FROM Usuario WHERE email = ?");
			query.setString(1, email);
			query.execute();
			ResultSet result = query.getResultSet();
			return !result.next() ? false : true;
		}
	}

	/**
	 * Creates the.
	 *
	 * @param nombreUsu the nombre usu
	 * @param pass the pass
	 * @param email the email
	 * @return true, if successful
	 * @throws SQLIntegrityConstraintViolationException the SQL integrity constraint violation exception
	 * @throws SQLTimeoutException the SQL timeout exception
	 * @throws SQLException the SQL exception
	 * @throws CustomException the custom exception
	 */
	public static boolean create(String nombreUsu, String pass, String email)
			throws SQLIntegrityConstraintViolationException, SQLTimeoutException, SQLException, CustomException {
		try (Connection con = new Conector().getMySQLConnection()) {
			PreparedStatement query = con.prepareStatement(
					"INSERT INTO Usuario(nombre_usuario, password , email) VALUES (?, ?,  ?)", new String[] { "id" });
			query.setString(1, nombreUsu);
			query.setString(2, pass);
			query.setString(3, email);
			query.executeUpdate();
			ResultSet result = query.getGeneratedKeys();
			if (!result.next())
				throw new CustomException("Error al registrarse");
			else {
				return true;
			}

		}
	}
}
