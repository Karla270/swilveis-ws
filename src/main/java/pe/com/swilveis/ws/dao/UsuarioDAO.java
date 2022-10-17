package pe.com.swilveis.ws.dao;

import java.util.List;

import pe.com.swilveis.ws.entity.Usuario;
import pe.com.swilveis.ws.entity.general.Confirmacion;

public interface UsuarioDAO {
	
	public Usuario obtener(String nombreUsuario);
	public Confirmacion insertar(Usuario usuario);
	public List<Usuario> listar(String nombreUsuario, String nombre, Integer idRol, Integer estado);
	public Confirmacion actualizarEstado(Integer idUsuario, Integer estado);

}
