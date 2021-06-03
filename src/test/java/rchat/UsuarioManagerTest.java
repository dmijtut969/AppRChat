/*
 * 
 */
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

/**
 * Class UsuarioManagerTest.
 * @author Daniel Mijens Tutor
 */
@ExtendWith(MockitoExtension.class)
public class UsuarioManagerTest {
	
	/** The usuario. */
	Mensaje usuario;

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
	 * Buscar usuario por ID OK.
	 */
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
	
	/**
	 * Buscar usuario por ID NOT OK.
	 */
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
	
	/**
	 * Buscar usuario por email OK.
	 */
	@Test
	void buscarUsuarioPorEmail_OK() {
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
	
	/**
	 * Buscar usuario por email NOT OK.
	 */
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
	
	/**
	 * Buscar usuario por nombre OK.
	 */
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
	
	/**
	 * Buscar usuario por nombre NOT OK.
	 */
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

	/**
 * Despues del test.
 */
@BeforeEach
	void despuesDeTest() {
		System.out.println("--------------FIN DEL TEST--------------");
	}

}
