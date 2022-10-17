package pe.com.swilveis.ws.controller;

import java.text.ParseException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.com.swilveis.ws.dto.JwtDto;
import pe.com.swilveis.ws.dto.LoginUsuarioRequest;
import pe.com.swilveis.ws.entity.Usuario;
import pe.com.swilveis.ws.entity.general.ResponseBase;
import pe.com.swilveis.ws.jwt.JwtProvider;
import pe.com.swilveis.ws.service.UsuarioService;

@RestController
@RequestMapping("auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private JwtProvider jwtProvider;
	
	@PostMapping("login")
	public ResponseBase<JwtDto> login(@Valid @RequestBody LoginUsuarioRequest request,
			BindingResult bindingResult) {
		ResponseBase<JwtDto> response = new ResponseBase<>();
		if (bindingResult.hasErrors()) {
			response.errorNegocio("Campos inválidos.");
		} else {
			ResponseBase<Usuario> responseUsuario = usuarioService.obtener(request.getNombreUsuario());
			if(responseUsuario.getEstado() == 1) {
				if(responseUsuario.getData().getEstado() == 1) {
					if(responseUsuario.getData().getPassword().equals(request.getPassword())) {
						Authentication authentication = authenticationManager.authenticate(
								new UsernamePasswordAuthenticationToken(request.getNombreUsuario(), request.getPassword()));
						SecurityContextHolder.getContext().setAuthentication(authentication);
						String jwt = jwtProvider.generateToken(authentication);
//						UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//						JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
						JwtDto jwtDto = new JwtDto(jwt);
						response = new ResponseBase<>(jwtDto);
						response.ok("Inició sesión correctamente.");
					} else {
						response = new ResponseBase<>();
						response.errorNegocio("Password incorrecto.");
					}
				} else {
					response = new ResponseBase<>();
					response.errorNegocio("Este usuario se encuentra inhabilitado.");
				}
			} else {
				response = new ResponseBase<>();
				response.errorNegocio("No se encontró al usuario");
			}
		}
		return response;
	}
	
	@PostMapping("refreshToken")
	public ResponseBase<JwtDto> refreshToken(@RequestBody JwtDto jwtDto) {
		ResponseBase<JwtDto> response = new ResponseBase<JwtDto>();
		try {
			String token = jwtProvider.refreshToken(jwtDto);
			JwtDto jwt = new JwtDto(token);
			response = new ResponseBase<>(jwt);
			response.ok("Token actualizado correctamente.");
		} catch (ParseException e) {
//			e.printStackTrace();
			response.errorNegocio("Error en el parseo del token.");
		}
		return response;
	}
}
