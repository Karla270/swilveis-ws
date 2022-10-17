package pe.com.swilveis.ws.entity;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthUser implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private String nombre;
	private String nombreUsuario;
	private String password;
	private String email;
	private Collection<? extends GrantedAuthority> authorities;
	
	public AuthUser(String nombre, String nombreUsuario, String password, String email,
			Collection<? extends GrantedAuthority> authorities) {
		this.nombre = nombre;
		this.nombreUsuario = nombreUsuario;
		this.password = password;
		this.email = email;
		this.authorities = authorities;
	}
	
	public static AuthUser build(Usuario usuario) {
		List<GrantedAuthority> authorities = usuario.getRoles().stream().map(rol ->
		new SimpleGrantedAuthority(rol.name())).collect(Collectors.toList());
		return new AuthUser(usuario.getNombre(), usuario.getNombreUsuario(),
				usuario.getPassword(), usuario.getEmail(), authorities);
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return nombreUsuario;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	public String getNombre() {
		return nombre;
	}
	public String getEmail() {
		return email;
	}
	
}
