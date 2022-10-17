package pe.com.swilveis.ws.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import pe.com.swilveis.ws.dto.FormatoResponse;
import pe.com.swilveis.ws.dto.ListarFormularioResponse;
import pe.com.swilveis.ws.entity.AuthUser;
import pe.com.swilveis.ws.entity.Formulario;
import pe.com.swilveis.ws.entity.Persona;
import pe.com.swilveis.ws.entity.Usuario;
import pe.com.swilveis.ws.entity.general.ResponseBase;

@Service
public class FormatoService {
	
	private final String urlFormatoApi = "http://170.239.84.149/wordtopdf/api/";
	
	@Autowired
	private FormularioService formularioService;
	
	@Autowired
	private AuthUserService authUserService;
	
	public ResponseBase<FormatoResponse> traerFormato(Persona request) {
		
		ResponseBase<FormatoResponse> response = new ResponseBase<FormatoResponse>();
		
		AuthUser authUser = authUserService.getLoggedUser();
		
		Formulario formulario = new Formulario();
		formulario.setIdTipoFormulario(request.getTipoFormulario());
		formulario.setNombreArchivo((request.getRuc() == null ? request.getDni() : request.getRuc()) + "-" + new Date().getTime());
		formulario.setUsuario(new Usuario(authUser.getUsername(), null, null, null, null));
		formulario.setPersona(request);
		
		request.setNombreFormulario(formulario.getNombreArchivo());
		
		RestTemplate restTemplate = new RestTemplate();

		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
			HttpEntity<Persona> entity = new HttpEntity<Persona>(request, headers);
			ResponseEntity<FormatoResponse> responseEntity = restTemplate
					  .exchange(urlFormatoApi + "formulario", HttpMethod.POST, entity, FormatoResponse.class);
			response = new ResponseBase<>(responseEntity.getBody());
			
			if(response.getData().getResponse().getEstado() == 1) {
				ResponseBase<?> insertarFormularioResponse = formularioService.insertar(formulario);
				
				if(insertarFormularioResponse.getEstado() != 1) {
					response.errorNegocio(insertarFormularioResponse.getMensaje());
					return response;
				}
				
				response.ok("Formato obtenido correctamente.");
				return response;
			} else {
				response.errorNegocio("Ocurrió un error en el formato.");
				return response;
			}
		} catch (Exception e) {
			response.errorNegocio("Ocurrió un error en el formato.");
			return response;
		}
	}
	
	public ResponseBase<ListarFormularioResponse> listar(Integer idUsuario, String fechaInicio, String fechaFin) {
		return formularioService.listar(idUsuario, fechaInicio, fechaFin);
	}

}
