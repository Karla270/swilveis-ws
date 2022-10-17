package pe.com.swilveis.ws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.com.swilveis.ws.entity.Persona;
import pe.com.swilveis.ws.entity.general.ResponseBase;
import pe.com.swilveis.ws.service.PersonaService;

@RestController
@RequestMapping("persona")
public class PersonaController {
	
	@Autowired
	private PersonaService personaService;
	
	@GetMapping
	public ResponseBase<Persona> obtener(@RequestParam("ruc") String ruc) {
		return personaService.obtener(ruc);
	}
	
	@PostMapping
	public ResponseBase<?> insertar(@RequestBody Persona persona) {
		return personaService.insertar(persona);
	}

}
