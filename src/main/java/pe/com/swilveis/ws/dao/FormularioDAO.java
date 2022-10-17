package pe.com.swilveis.ws.dao;

import pe.com.swilveis.ws.dto.ListarFormularioResponse;
import pe.com.swilveis.ws.entity.Formulario;
import pe.com.swilveis.ws.entity.general.Confirmacion;

public interface FormularioDAO {
	
	public Confirmacion insertar(Formulario formulario);
	public ListarFormularioResponse listar(Integer idUsuario, String fechaInicio, String fechaFin);

}
