package sesion;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Mensaje implements Comparable<Mensaje>{
	
	private Integer id;
	private String nombreUsuario;
	private String password;
	private String email;
	
	public Mensaje(int id,String nombreUsuario,String password,String email) {
		this.id = id;
		this.nombreUsuario = nombreUsuario;
		this.password = password;
		this.email = email;
	}

	@Override
	public int compareTo(Mensaje usuario) {
		
		return this.getId().compareTo(usuario.getId());
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombreUsuario=" + nombreUsuario + ", password=" + password + ", email=" + email
				+ "]";
	}
	
}
