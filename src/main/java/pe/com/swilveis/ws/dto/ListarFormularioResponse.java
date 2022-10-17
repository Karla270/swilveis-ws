package pe.com.swilveis.ws.dto;

import java.util.List;

import pe.com.swilveis.ws.entity.Formulario;

public class ListarFormularioResponse {
	
	private Integer cantidadFormulario;
	private Integer cantidadPersona;
	
	private List<Formulario> formularioList;

	public Integer getCantidadFormulario() {
		return cantidadFormulario;
	}

	public void setCantidadFormulario(Integer cantidadFormulario) {
		this.cantidadFormulario = cantidadFormulario;
	}

	public Integer getCantidadPersona() {
		return cantidadPersona;
	}

	public void setCantidadPersona(Integer cantidadPersona) {
		this.cantidadPersona = cantidadPersona;
	}

	public List<Formulario> getFormularioList() {
		return formularioList;
	}

	public void setFormularioList(List<Formulario> formularioList) {
		this.formularioList = formularioList;
	}

}
