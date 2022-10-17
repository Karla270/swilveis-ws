package pe.com.swilveis.ws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.com.swilveis.ws.dto.FormatoResponse;
import pe.com.swilveis.ws.dto.ListarFormularioResponse;
import pe.com.swilveis.ws.entity.Persona;
import pe.com.swilveis.ws.entity.general.ResponseBase;
import pe.com.swilveis.ws.service.FormatoService;

@RestController
@RequestMapping("formato")
public class FormatoController {

	@Autowired
	private FormatoService formatoService;

	@PostMapping("licencia")
	public ResponseBase<FormatoResponse> traerFormato(@RequestBody Persona request) {
		return formatoService.traerFormato(request);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("listar")
	public ResponseBase<ListarFormularioResponse> listarFormulario(
			@RequestParam(name = "idUsuario", required = false) Integer idUsuario,
			@RequestParam(name = "fechaInicio", required = false) String fechaInicio,
			@RequestParam(name = "fechaFin", required = false) String fechaFin) {
		return formatoService.listar(idUsuario, fechaInicio, fechaFin);
	}

}
