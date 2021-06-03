/*
 * @author Daniel Mijens
 */
package rchat;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import conectorManager.MensajeManager;
import conectorManager.UsuarioManager;
import sesion.Mensaje;
import utils.CustomException;

/**
 * Class MensajeManagerTest.
 */
@ExtendWith(MockitoExtension.class)
public class MensajeManagerTest {
	
	/** The mensaje. */
	Mensaje mensaje;

	/** The con. */
	@Mock
	Connection con;

	/** The prep stat. */
	@Mock
	PreparedStatement prepStat;

	/** The usuario mock. */
	@InjectMocks
	Mensaje usuarioMock = new Mensaje();

	/**
	 * Antes del test.
	 */
	@BeforeEach
	void antesDeTest() {
		System.out.println("--------------INICIO TEST--------------");
	}

	/**
	 * Find mensaje by grupo OK.
	 */
	@Test
	void findMensajeByGrupo_OK(){
		try {
			List<dao.Mensaje> listadoMensajes = MensajeManager.findMensajeByGrupo(0);
			
			for (dao.Mensaje mensaje : listadoMensajes) {
				assertTrue(mensaje.getIdGrupo()==0);
			}
		} catch (SQLTimeoutException e) {
			e.getStackTrace();
		} catch (SQLException e) {
			e.getStackTrace();
		}
	}
	
	/**
	 * Find mensaje by grupo NOT OK.
	 */
	@Test
	void findMensajeByGrupo_NOT_OK(){
		try {
			List<dao.Mensaje> listadoMensajes = MensajeManager.findMensajeByGrupo(0);
			
			for (dao.Mensaje mensaje : listadoMensajes) {
				assertFalse(mensaje.getIdGrupo()!=0);
			}
		} catch (SQLTimeoutException e) {
			e.getStackTrace();
		} catch (SQLException e) {
			e.getStackTrace();
		}
	}
	
	/**
	 * Sacar ultimos mensajes grupo con limite OK.
	 */
	@Test
	void sacarUltimosMensajesGrupoConLimite_OK(){
		try {
			List<dao.Mensaje> listadoMensajes = MensajeManager.sacarUltimosMensajesGrupoConLimite(0, 1);
			assertTrue(listadoMensajes.size()<2);
		} catch (SQLTimeoutException e) {
			e.getStackTrace();
		} catch (SQLException e) {
			e.getStackTrace();
		}
	}
	
	/**
	 * Sacar ultimos mensajes grupo con limite NOT OK.
	 */
	@Test
	void sacarUltimosMensajesGrupoConLimite_NOT_OK(){
		try {
			List<dao.Mensaje> listadoMensajes = MensajeManager.sacarUltimosMensajesGrupoConLimite(0, 1);
			assertFalse(listadoMensajes.size()>2);
		} catch (SQLTimeoutException e) {
			e.getStackTrace();
		} catch (SQLException e) {
			e.getStackTrace();
		}
	}
	
	/**
	 * Despues de test.
	 */
	@BeforeEach
	void despuesDeTest() {
		System.out.println("--------------FIN DEL TEST--------------");
	}

}
