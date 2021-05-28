package rchat;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class CrearGrupoTest {
	boolean verdad;
	
	@BeforeEach
	void verdadero() {
		verdad=true;
	}
	@Test
	void crearGrupo(){
		assertTrue(verdad);
		System.out.println("Verdad");
	}
	
	@Test
	void falso() {
		System.out.println("Falso");
		assertFalse(verdad);
		
	}
}
