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

public class MensajeManager {

	public static List<Mensaje> findMensajeByGrupo(int idGrupo) throws SQLTimeoutException, SQLException {
		try (Connection con = new Conector().getMySQLConnection()) {
			PreparedStatement query = con.prepareStatement("select * from Mensaje where idGrupo = ?");
			query.setInt(1, idGrupo);
			query.execute();
			ResultSet result = query.getResultSet();
			ArrayList<Mensaje> mensajesDelGrupo = new ArrayList<>();
			while (result.next()) {
				mensajesDelGrupo.add(new Mensaje(result.getInt("idGrupo"), result.getString("emisor"),
						result.getString("mensaje"), result.getString("fecha")));
			}
			return mensajesDelGrupo;
		}
	}

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
				mensajesDelGrupoLimitados.add(new Mensaje(result.getInt("idGrupo"), result.getString("emisor"),
						result.getString("mensaje"), result.getString("fecha")));
			}
			return mensajesDelGrupoLimitados;
		}
	}

	public static boolean create(Mensaje mensaje)
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
}
