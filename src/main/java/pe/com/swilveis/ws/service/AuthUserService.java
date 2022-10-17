package pe.com.swilveis.ws.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import pe.com.swilveis.ws.entity.AuthUser;
import pe.com.swilveis.ws.entity.Usuario;

@Service
public class AuthUserService implements UserDetailsService {
	
	@Autowired
	private UsuarioService usuarioService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioService.obtener(username).getData();
		return AuthUser.build(usuario);
	}
	
	public AuthUser getLoggedUser() {
		return (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

}
