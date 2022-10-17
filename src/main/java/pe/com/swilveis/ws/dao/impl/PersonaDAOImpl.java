package pe.com.swilveis.ws.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import pe.com.swilveis.ws.dao.PersonaDAO;
import pe.com.swilveis.ws.entity.Persona;
import pe.com.swilveis.ws.entity.general.Confirmacion;

@Repository
@Transactional
public class PersonaDAOImpl implements PersonaDAO {
	
	@Autowired
	private SqlSession session;
	
	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public Persona obtener(String ruc) {
		Map<String, Object> map = new HashMap<>();
		map.put("ruc", ruc);
		return session.selectOne("PersonaMapper.obtenerPersona", map);
	}

	@Override
	public Confirmacion insertar(Persona persona) {
		String json;
		try {
			json = objectMapper.writeValueAsString(persona);
		} catch (JsonProcessingException e) {
//			e.printStackTrace();
			return Confirmacion.errorAplicacion("Error al transformar la data");
		}
		Map<String, Object> map = new HashMap<>();
		map.put("personajson", json);
		return session.selectOne("PersonaMapper.insertarPersona", map);
	}

}
