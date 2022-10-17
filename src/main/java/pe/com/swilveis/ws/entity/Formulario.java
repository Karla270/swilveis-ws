package pe.com.swilveis.ws.entity;

public class Formulario {
	
	private Long idFormulario;
	private Integer idTipoFormulario;
	private String nombreArchivo;
	private String fhIns;
	
	private Usuario usuario;
	private Persona persona;
	
	public Long getIdFormulario() {
		return idFormulario;
	}
	public void setIdFormulario(Long idFormulario) {
		this.idFormulario = idFormulario;
	}
	public Integer getIdTipoFormulario() {
		return idTipoFormulario;
	}
	public void setIdTipoFormulario(Integer idTipoFormulario) {
		this.idTipoFormulario = idTipoFormulario;
	}
	public String getNombreArchivo() {
		return nombreArchivo;
	}
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
	public String getFhIns() {
		return fhIns;
	}
	public void setFhIns(String fhIns) {
		this.fhIns = fhIns;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}

}
