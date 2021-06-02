package rchat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import conectorManager.UsuarioManager;
import sesion.Mensaje;
import utils.CustomException;

@ExtendWith(MockitoExtension.class)
public class UsuarioManagerTest {
	Mensaje usuario;

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
	void buscarUsuarioPorID_OK(){
		try {
			usuario = UsuarioManager.findById(2);
		} catch (SQLTimeoutException e) {
			usuario = null;
		} catch (SQLException e) {
			usuario = null;
		}
		assertEquals(2, usuario.getId());
	}
	
	@Test
	void buscarUsuarioPorID_NOT_OK(){
		try {
			usuario = UsuarioManager.findById(2);
		} catch (SQLTimeoutException e) {
			usuario = null;
		} catch (SQLException e) {
			usuario = null;
		}
		assertNotEquals(1, usuario.getId());
	}
	
	@Test
	void buscarUsuarioPoreEmail_OK() {
		try {
			usuario = UsuarioManager.findByEmail("danimijtut@gmail.com");
		} catch (SQLTimeoutException e) {
			usuario = null;
		} catch (SQLException e) {
			usuario = null;
		} catch (CustomException e) {
			usuario = null;
		}
		assertEquals("danimijtut@gmail.com", usuario.getEmail());
	}
	
	@Test
	void buscarUsuarioPorEmail_NOT_OK(){
		try {
			usuario = UsuarioManager.findByEmail("danimijtut@gmail.com");
		} catch (SQLTimeoutException e) {
			usuario = null;
		} catch (SQLException e) {
			usuario = null;
		} catch (CustomException e) {
			usuario = null;
		}
		assertNotEquals("noexiste@gmail.com", usuario.getEmail());
	}
	
	@Test
	void buscarUsuarioPorNombre_OK(){
		try {
			usuario = UsuarioManager.findByNombre("Dani");
		} catch (SQLTimeoutException e) {
			usuario = null;
		} catch (SQLException e) {
			usuario = null;
		} catch (CustomException e) {
			usuario = null;
		}
		assertEquals("Dani", usuario.getNombreUsuario());
	}
	
	@Test
	void buscarUsuarioPorNombre_NOT_OK(){
		try {
			usuario = UsuarioManager.findByNombre("Dani");
		} catch (SQLTimeoutException e) {
			usuario = null;
		} catch (SQLException e) {
			usuario = null;
		} catch (CustomException e) {
			usuario = null;
		}
		assertNotEquals("Vaya, No Existo", usuario.getEmail());
	}
////	
//	@Test
//	void crearUsuario_OK() {
//		MockitoAnnotations.initMocks(this);
//		try {
//			try {
//				
//				PreparedStatement query = con.prepareStatement("");
//				query.setString(1, Mockito.anyString());
//				query.setString(2, Mockito.anyString());
//				query.setString(3, Mockito.anyString());
//				query.executeUpdate();
//
//			}
//		} catch (SQLIntegrityConstraintViolationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SQLTimeoutException e) {
//			e.printStackTrace();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} catch (CustomException e) {
//			e.printStackTrace();
//		}
//	}

	@BeforeEach
	void despuesDeTest() {
		System.out.println("--------------FIN DEL TEST--------------");
	}

}
