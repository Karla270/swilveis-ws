package pe.com.swilveis.ws.dto;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import pe.com.swilveis.ws.entity.general.PersonaSunat;

public class PersonaSunatResponse {
	
	private Boolean success;
	private Object data;
	private PersonaSunat dataObj;
	private Boolean dataBoolean;
	private List<String> error;
	private String msg;
	
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
		try {
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(data);
			this.setDataObj(mapper.readValue(json, PersonaSunat.class));
		} catch (Exception e) {
			setDataBoolean(false);
		}
	}
	public PersonaSunat getDataObj() {
		return dataObj;
	}
	public void setDataObj(PersonaSunat dataObj) {
		this.dataObj = dataObj;
	}
	public Boolean getDataBoolean() {
		return dataBoolean;
	}
	public void setDataBoolean(Boolean dataBoolean) {
		this.dataBoolean = dataBoolean;
	}
	public List<String> getError() {
		return error;
	}
	public void setError(List<String> error) {
		this.error = error;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

}
