package pe.com.swilveis.ws.entity.general;

public class ResponseBase<T> {
	
	private Integer estado;
	private String mensaje;
	private T data;
	
	public ResponseBase() {
	}
	
	private ResponseBase(Integer estado, String mensaje) {
		this.estado = estado;
		this.mensaje = mensaje;
	}
	
	public ResponseBase(T data) {
		this.data = data;
	}
	
	public ResponseBase(Confirmacion confirmacion) {
		this.estado = confirmacion.getIdResult();
		this.mensaje = confirmacion.getMensaje();
	}
	
	public void ok(String mensaje) {
		this.estado = 1;
		this.mensaje = mensaje;
	}
	
	public void errorNegocio(String mensaje) {
		this.estado = 0;
		this.mensaje = mensaje;
	}
	
	public static ResponseBase<?> errorAplicacion(String mensaje) {
		return new ResponseBase<>(-1, mensaje);
	}

	public Integer getEstado() {
		return estado;
	}

	public String getMensaje() {
		return mensaje;
	}

	public T getData() {
		return data;
	}

}
