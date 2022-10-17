package pe.com.swilveis.ws.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Properties {
	
	@Value("${jwt.header.name}")
	private String jwtHeaderName;
	
	@Value("${jwt.prefix}")
	private String jwtPrefix;
	
	@Value("${jwt.secret}")
	private String jwtSecret;
	
	@Value("${jwt.expiration}")
	private int jwtExpiration;
	
	public String getJwtHeaderName() {
		return jwtHeaderName;
	}
	public void setJwtHeaderName(String jwtHeaderName) {
		this.jwtHeaderName = jwtHeaderName;
	}
	public String getJwtPrefix() {
		return jwtPrefix;
	}
	public void setJwtPrefix(String jwtPrefix) {
		this.jwtPrefix = jwtPrefix;
	}
	public String getJwtSecret() {
		return jwtSecret;
	}
	public void setJwtSecret(String jwtSecret) {
		this.jwtSecret = jwtSecret;
	}
	public int getJwtExpiration() {
		return jwtExpiration;
	}
	public void setJwtExpiration(int jwtExpiration) {
		this.jwtExpiration = jwtExpiration;
	}

}
