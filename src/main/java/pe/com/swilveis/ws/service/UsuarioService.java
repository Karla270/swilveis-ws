package pe.com.swilveis.ws.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.swilveis.ws.dao.UsuarioDAO;
import pe.com.swilveis.ws.entity.Usuario;
import pe.com.swilveis.ws.entity.general.ResponseBase;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioDAO usuarioDAO;

	public ResponseBase<Usuario> obtener(String nombreUsuario) {
		ResponseBase<Usuario> response = new ResponseBase<Usuario>(usuarioDAO.obtener(nombreUsuario));
		if (response.getData() != null) {
			response.ok("Usuario obtenido correctamente.");
		} else {
			response.errorNegocio("No se encontró al usuario.");
		}
		return response;
	}
	
	public ResponseBase<?> insertar(Usuario usuario) {
		return new ResponseBase<>(usuarioDAO.insertar(usuario));
	}
	
	public ResponseBase<List<Usuario>> listar(String nombreUsuario, String nombre, Integer idRol, Integer estado) {
		ResponseBase<List<Usuario>> response = new ResponseBase<List<Usuario>>(usuarioDAO.listar(nombreUsuario, nombre, idRol, estado));
		if(response.getData().size() > 0) {
			response.ok("Lista de Usuarios obtenida correctamente.");
		} else {
			response.errorNegocio("Lista de Usuarios vacía.");
		}
		return response;
	}
	
	public ResponseBase<?> activarUsuario(Integer idUsuario) {
		return new ResponseBase<>(usuarioDAO.actualizarEstado(idUsuario, 1));
	}
	
	public ResponseBase<?> desactivarUsuario(Integer idUsuario) {
		return new ResponseBase<>(usuarioDAO.actualizarEstado(idUsuario, 0));
	}

}
