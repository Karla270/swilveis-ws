package pe.com.swilveis.ws.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import pe.com.swilveis.ws.dao.UsuarioDAO;
import pe.com.swilveis.ws.entity.Usuario;
import pe.com.swilveis.ws.entity.general.Confirmacion;

@Repository
@Transactional
public class UsuarioDAOImpl implements UsuarioDAO {
	
	@Autowired
	private SqlSession session;
	
	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public Usuario obtener(String nombreUsuario) {
		Map<String, Object> map = new HashMap<>();
		map.put("nombreUsuario", nombreUsuario);
		return session.selectOne("UsuarioMapper.obtener", map);
	}

	@Override
	public Confirmacion insertar(Usuario usuario) {
		String json;
		try {
			json = objectMapper.writeValueAsString(usuario);
		} catch (JsonProcessingException e) {
//			e.printStackTrace();
			return Confirmacion.errorAplicacion("Error al transformar la data");
		}
		Map<String, Object> map = new HashMap<>();
		map.put("json", json);
		return session.selectOne("UsuarioMapper.insertar", map);
	}

	@Override
	public List<Usuario> listar(String nombreUsuario, String nombre, Integer idRol, Integer estado) {
		Map<String, Object> map = new HashMap<>();
		map.put("nombreUsuario", nombreUsuario);
		map.put("nombre", nombre);
		map.put("idRol", idRol);
		map.put("estado", estado);
		return session.selectList("UsuarioMapper.listar", map);
	}

	@Override
	public Confirmacion actualizarEstado(Integer idUsuario, Integer estado) {
		Map<String, Object> map = new HashMap<>();
		map.put("idUsuario", idUsuario);
		map.put("estado", estado);
		return session.selectOne("UsuarioMapper.actualizarEstado", map);
	}

}
