package pe.com.swilveis.ws.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.com.swilveis.ws.dto.RegistrarUsuarioRequest;
import pe.com.swilveis.ws.entity.Usuario;
import pe.com.swilveis.ws.entity.general.ResponseBase;
import pe.com.swilveis.ws.service.UsuarioService;

@RestController
@RequestMapping("usuario")
public class UsuarioController {
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UsuarioService usuarioService;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseBase<?> registrarUsuario(@Valid @RequestBody RegistrarUsuarioRequest request,
			BindingResult bindingResult) {
		ResponseBase<?> response = new ResponseBase<>();
		if (bindingResult.hasErrors()) {
			response.errorNegocio("Campos inválidos.");
		} else {
			Usuario usuario = new Usuario(request.getNombreUsuario(), passwordEncoder.encode(request.getPassword()),
					request.getNombre(), request.getEmail(), request.getIdRol());
			response = usuarioService.insertar(usuario);
		}
		return response;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("{idUsuario}")
	public ResponseBase<?> actualizarUsuario(@PathVariable("idUsuario") Integer idUsuario, @Valid @RequestBody RegistrarUsuarioRequest request,
			BindingResult bindingResult) {
		ResponseBase<?> response = new ResponseBase<>();
		if (bindingResult.hasErrors()) {
			response.errorNegocio("Campos inválidos.");
		} else {
			Usuario usuario = new Usuario(request.getNombreUsuario(), passwordEncoder.encode(request.getPassword()),
					request.getNombre(), request.getEmail(), request.getIdRol());
			usuario.setIdUsuario(idUsuario);
			response = usuarioService.insertar(usuario);
		}
		return response;
	}
	
	@GetMapping("{nombreUsuario}")
	public ResponseBase<Usuario> obtener(@PathVariable("nombreUsuario") String nombreUsuario) {
		ResponseBase<Usuario> response = usuarioService.obtener(nombreUsuario);
		response.getData().setPassword(null);
		return response;
	}
	
	@GetMapping
	public ResponseBase<List<Usuario>> listar(@RequestParam(name = "nombreUsuario", required = false) String nombreUsuario,
			@RequestParam(name = "nombre", required = false) String nombre,
			@RequestParam(name = "idRol", required = false) Integer idRol,
			@RequestParam(name = "estado", required = false) Integer estado) {
		ResponseBase<List<Usuario>> response = usuarioService.listar(nombreUsuario, nombre, idRol, estado);
		return response;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("activar/{idUsuario}")
	public ResponseBase<?> activarUsuario(@PathVariable("idUsuario") Integer idUsuario) {
		return usuarioService.activarUsuario(idUsuario);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("desactivar/{idUsuario}")
	public ResponseBase<?> desactivarUsuario(@PathVariable("idUsuario") Integer idUsuario) {
		return usuarioService.desactivarUsuario(idUsuario);
	}
	
}
