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
import java.util.ArrayList;
import java.util.List;

import conector.Conector;
import dao.Mensaje;
import utils.CustomException;

/**
 * The Class MensajeManager.
 */
public class MensajeManager {

	/**
	 * Find mensaje by grupo.
	 *
	 * @param idGrupo the id grupo
	 * @return the list
	 * @throws SQLTimeoutException the SQL timeout exception
	 * @throws SQLException the SQL exception
	 */
	public static List<Mensaje> findMensajeByGrupo(int idGrupo) throws SQLTimeoutException, SQLException {
		try (Connection con = new Conector().getMySQLConnection()) {
			PreparedStatement query = con.prepareStatement("select * from Mensaje where idGrupo = ?");
			query.setInt(1, idGrupo);
			query.execute();
			ResultSet result = query.getResultSet();
			ArrayList<Mensaje> mensajesDelGrupo = new ArrayList<>();
			while (result.next()) {
				mensajesDelGrupo.add(new Mensaje(result.getInt("idMensaje"),result.getInt("idGrupo"), result.getString("emisor"),
						result.getString("mensaje"), result.getString("fecha"),false));
			}
			return mensajesDelGrupo;
		}
	}

	/**
	 * Get last group messages with limit.
	 *
	 * @param idGrupo the id grupo
	 * @param limiteMensajes the limite mensajes
	 * @return the list
	 * @throws SQLTimeoutException the SQL timeout exception
	 * @throws SQLException the SQL exception
	 */
	public static List<Mensaje> sacarUltimosMensajesGrupoConLimite(Integer idGrupo, int limiteMensajes)
			throws SQLTimeoutException, SQLException {
		try (Connection con = new Conector().getMySQLConnection()) {
			PreparedStatement query = con
					.prepareStatement("select * from Mensaje where idGrupo = ? order by idMensaje desc limit ?");
			query.setInt(1, idGrupo);
			query.setInt(2, limiteMensajes);
			query.execute();
			ResultSet result = query.getResultSet();
			ArrayList<Mensaje> mensajesDelGrupoLimitados = new ArrayList<>();
			while (result.next()) {
				mensajesDelGrupoLimitados.add(new Mensaje(result.getInt("idMensaje"),result.getInt("idGrupo"), result.getString("emisor"),
						result.getString("mensaje"), result.getString("fecha"),false));
			}
			return mensajesDelGrupoLimitados;
		}
	}

	/**
	 * Create message.
	 *
	 * @param mensaje the mensaje
	 * @return true, if successful
	 * @throws SQLIntegrityConstraintViolationException the SQL integrity constraint violation exception
	 * @throws SQLTimeoutException the SQL timeout exception
	 * @throws SQLException the SQL exception
	 * @throws CustomException the custom exception
	 */
	public static boolean crearMensaje(Mensaje mensaje)
			throws SQLIntegrityConstraintViolationException, SQLTimeoutException, SQLException, CustomException {
		try (Connection con = new Conector().getMySQLConnection()) {
			PreparedStatement query = con
					.prepareStatement("INSERT INTO Mensaje(idGrupo,emisor,mensaje,fecha) VALUES (?,?,?,?)");
			query.setInt(1, mensaje.getIdGrupo());
			query.setString(2, mensaje.getEmisor());
			query.setString(3, mensaje.getMensaje());
			query.setString(4, mensaje.getHora());
			if (query.executeUpdate() > 0)
				return true;
			else
				return false;
		}
	}
	
	/**
	 * Create message.
	 *
	 * @param mensaje the mensaje
	 * @return true, if successful
	 * @throws SQLIntegrityConstraintViolationException the SQL integrity constraint violation exception
	 * @throws SQLTimeoutException the SQL timeout exception
	 * @throws SQLException the SQL exception
	 * @throws CustomException the custom exception
	 */
	public Mensaje crearMensajeMock(Mensaje mensaje)
			throws SQLIntegrityConstraintViolationException, SQLTimeoutException, SQLException, CustomException {
		try (Connection con = new Conector().getMySQLConnection()) {
			PreparedStatement query = con
					.prepareStatement("INSERT INTO Mensaje(idGrupo,emisor,mensaje,fecha) VALUES (?,?,?,?)");
			query.setInt(1, mensaje.getIdGrupo());
			query.setString(2, mensaje.getEmisor());
			query.setString(3, mensaje.getMensaje());
			query.setString(4, mensaje.getHora());
			if (query.executeUpdate() > 0)
				return mensaje;
			else
				return new Mensaje();
		}
	}
	
	/**
	 * Delete message.
	 *
	 * @param mensaje the mensaje
	 * @return true, if successful
	 * @throws SQLIntegrityConstraintViolationException the SQL integrity constraint violation exception
	 * @throws SQLTimeoutException the SQL timeout exception
	 * @throws SQLException the SQL exception
	 * @throws CustomException the custom exception
	 */
	public static boolean eliminarMensaje(Mensaje mensaje)
			throws SQLIntegrityConstraintViolationException, SQLTimeoutException, SQLException, CustomException {
		try (Connection con = new Conector().getMySQLConnection()) {
			PreparedStatement query = con
					.prepareStatement("DELETE FROM Mensaje WHERE idMensaje = ?");
			query.setInt(1, mensaje.getIdMensaje());
			if (query.executeUpdate() > 0)
				return true;
			else
				return false;
		}
	}
}
