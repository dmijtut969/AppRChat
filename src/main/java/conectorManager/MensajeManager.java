package conectorManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLTimeoutException;

import conector.Conector;
import dao.Mensaje;
import utils.CustomException;

public class MensajeManager {
	
	public static boolean create(Mensaje mensaje)
			throws SQLIntegrityConstraintViolationException, SQLTimeoutException, SQLException, CustomException {
		try (Connection con = new Conector().getMySQLConnection()) {
			PreparedStatement query = con.prepareStatement(
					"INSERT INTO Mensaje(idGrupo,emisor,mensaje,fecha) VALUES (?,?,?,?)");
			query.setInt(1, mensaje.getIdGrupo());
			query.setString(2, mensaje.getEmisor());
			query.setString(3, mensaje.getMensaje());
			query.setString(4, mensaje.getHora());
			if(query.executeUpdate()>0)
				return true;
			else return false;
		}
	}
}
