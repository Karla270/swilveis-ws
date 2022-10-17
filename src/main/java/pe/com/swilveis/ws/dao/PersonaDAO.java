package pe.com.swilveis.ws.dao;

import pe.com.swilveis.ws.entity.Persona;
import pe.com.swilveis.ws.entity.general.Confirmacion;

public interface PersonaDAO {
	
	public Persona obtener(String ruc);
	public Confirmacion insertar(Persona persona);

}
