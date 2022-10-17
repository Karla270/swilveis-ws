package pe.com.swilveis.ws.entity.general;

public class Confirmacion {
	
	private Integer idResult;
	private String mensaje;
	
	public Confirmacion() {
	}
	
	public Confirmacion(Integer idResult, String mensaje) {
		this.idResult = idResult;
		this.mensaje = mensaje;
	}
	
	public static Confirmacion ok(String mensaje) {
		return new Confirmacion(1, mensaje);
	}
	
	public static Confirmacion errorNegocio(String mensaje) {
		return new Confirmacion(0, mensaje);
	}
	
	public static Confirmacion errorAplicacion(String mensaje) {
		return new Confirmacion(-1, mensaje);
	}
	
	public Integer getIdResult() {
		return idResult;
	}
	public void setIdResult(Integer idResult) {
		this.idResult = idResult;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

}
