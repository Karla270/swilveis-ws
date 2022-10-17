package pe.com.swilveis.ws.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import pe.com.swilveis.ws.dao.PersonaDAO;
import pe.com.swilveis.ws.entity.Persona;
import pe.com.swilveis.ws.entity.general.PersonaSunat;
import pe.com.swilveis.ws.entity.general.ResponseBase;

@Service
public class PersonaService {
	
	private final String urlApiRuc = "https://dniruc.apisperu.com/api/v1/";
	private final String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImthcmxhLmdvbnphbGVzQHVycC5lZHUucGUifQ.6iVnIqa0yIbzHNu-hkbWLonknsm2c3kRj1EdXiP3-eo";
	
	@Autowired
	private PersonaDAO personaDAO;
	
	public ResponseBase<Persona> obtener(String ruc) {
		ResponseBase<Persona> response = new ResponseBase<Persona>(personaDAO.obtener(ruc));
		if (response.getData() != null) {
			response.ok("Persona obtenida correctamente.");
		} else {
			PersonaSunat responsePersonaSunat = this.buscarPersonaSunat(ruc);
			if(responsePersonaSunat != null) {
				if(responsePersonaSunat.getRuc() != null) {
					Persona persona = new Persona();
					persona.setRuc(responsePersonaSunat.getRuc());
					persona.setRazonSocial(responsePersonaSunat.getRazonSocial());
					// persona.setDirecionLocal(responsePersonaSunat.getDireccion());
					
					response = new ResponseBase<Persona>(persona);
					response.ok("Persona obtenida correctamente.");
				} else {
					response.errorNegocio("No se encontró a la Persona.");
				}
			} else {
				response.errorNegocio("RUC incorrecto.");
			}
		}
		return response;
	}
	
	private PersonaSunat buscarPersonaSunat(String ruc) {
		RestTemplate restTemplate = new RestTemplate();
		try {
			ResponseEntity<PersonaSunat> response = restTemplate
					  .exchange(urlApiRuc + "ruc/" + ruc + "?token=" + token, HttpMethod.GET, null, PersonaSunat.class);
			return response.getBody();
		} catch (Exception e) {
			return null;
		}
	}
	
	public ResponseBase<?> insertar(Persona persona) {
		return new ResponseBase<>(personaDAO.insertar(persona));
	}

}
