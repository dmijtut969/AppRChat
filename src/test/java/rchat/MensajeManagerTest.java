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

@ExtendWith(MockitoExtension.class)
public class MensajeManagerTest {
	Mensaje mensaje;

	@Mock
	Connection con;

	@Mock
	PreparedStatement prepStat;

	@InjectMocks
	Mensaje usuarioMock = new Mensaje();

	@BeforeEach
	void antesDeTest() {
		System.out.println("--------------INICIO TEST--------------");
	}

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
	@BeforeEach
	void despuesDeTest() {
		System.out.println("--------------FIN DEL TEST--------------");
	}

}
