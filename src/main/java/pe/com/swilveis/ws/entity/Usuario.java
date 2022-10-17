package pe.com.swilveis.ws.entity;

import java.util.HashSet;
import java.util.Set;

import pe.com.swilveis.ws.enums.Rol;

public class Usuario {
	
	private Integer idUsuario;
	private String nombreUsuario;
	private String password;
	private String nombre;
	private String email;
	private Integer idRol;
	private Set<Rol> roles = new HashSet<>();
	private Integer estado;
	
	public Usuario() {
	}
	
	public Usuario(String nombreUsuario, String password, String nombre, String email, Integer idRol) {
		super();
		this.nombreUsuario = nombreUsuario;
		this.password = password;
		this.nombre = nombre;
		this.email = email;
		this.idRol = idRol;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getIdRol() {
		return idRol;
	}
	public void setIdRol(Integer idRol) {
		this.idRol = idRol;
	}
	public Set<Rol> getRoles() {
		roles = new HashSet<>();
		if(idRol != null) {
			if(idRol == 1) {
				roles.add(Rol.ROLE_ADMIN);
			}
			if(idRol == 2) {
				roles.add(Rol.ROLE_USER);
			}
		}
		return roles;
	}
	public void setRoles(Set<Rol> roles) {
		this.roles = roles;
	}
	public Integer getEstado() {
		return estado;
	}
	public void setEstado(Integer estado) {
		this.estado = estado;
	}

}
