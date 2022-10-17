package pe.com.swilveis.ws.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import pe.com.swilveis.ws.dao.FormularioDAO;
import pe.com.swilveis.ws.dto.ListarFormularioResponse;
import pe.com.swilveis.ws.entity.Formulario;
import pe.com.swilveis.ws.entity.general.Confirmacion;

@Repository
@Transactional
public class FormularioDAOImpl implements FormularioDAO {
	
	@Autowired
	private SqlSession session;
	
	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public Confirmacion insertar(Formulario formulario) {
		String json;
		try {
			json = objectMapper.writeValueAsString(formulario);
		} catch (JsonProcessingException e) {
			return Confirmacion.errorAplicacion("Error al transformar la data");
		}
		Map<String, Object> map = new HashMap<>();
		map.put("json", json);
		return session.selectOne("FormularioMapper.insertar", map);
	}

	@Override
	public ListarFormularioResponse listar(Integer idUsuario, String fechaInicio, String fechaFin) {
		Map<String, Object> map = new HashMap<>();
		map.put("idUsuario", idUsuario);
		map.put("fechaInicio", fechaInicio);
		map.put("fechaFin", fechaFin);
		return session.selectOne("FormularioMapper.listar", map);
	}

}
