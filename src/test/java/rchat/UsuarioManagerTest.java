package rchat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.sql.SQLException;
import java.sql.SQLTimeoutException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import conectorManager.UsuarioManager;
import sesion.Usuario;


@ExtendWith(MockitoExtension.class)
public class UsuarioManagerTest {
	Usuario usuario;
	
	@BeforeEach
	void antesDeTest() {
		System.out.println("--------------INICIO TEST--------------");
	}
	@Test
	void buscarUsuarioPorID_OK() throws SQLTimeoutException, SQLException{
		usuario = UsuarioManager.findById(2);
		assertEquals(2, usuario.getId());
	}
	
	@Test
	void buscarUsuarioPorID_NOT_OK() throws SQLTimeoutException, SQLException{
		usuario = UsuarioManager.findById(2);
		assertNotEquals(1, usuario.getId());
	}
	@BeforeEach
	void despuesDeTest() {
		System.out.println("--------------FIN DEL TEST--------------");
	}

}
