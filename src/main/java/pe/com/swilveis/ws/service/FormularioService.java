package pe.com.swilveis.ws.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.swilveis.ws.dao.FormularioDAO;
import pe.com.swilveis.ws.dto.ListarFormularioResponse;
import pe.com.swilveis.ws.entity.Formulario;
import pe.com.swilveis.ws.entity.general.ResponseBase;

@Service
public class FormularioService {
	
	@Autowired
	private FormularioDAO formularioDAO;
	
	public ResponseBase<?> insertar(Formulario formulario) {
		return new ResponseBase<>(formularioDAO.insertar(formulario));
	}
	
	public ResponseBase<ListarFormularioResponse> listar(Integer idUsuario, String fechaInicio, String fechaFin) {
		ResponseBase<ListarFormularioResponse> response = new ResponseBase<ListarFormularioResponse>(formularioDAO.listar(idUsuario, fechaInicio, fechaFin));
		if(response.getData().getFormularioList().size() > 0) {
			response.ok("Lista de Formularios obtenida correctamente.");
		} else {
			response.errorNegocio("Lista de Formularios vacía.");
		}
		return response;
	}

}
