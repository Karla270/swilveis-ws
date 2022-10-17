package pe.com.swilveis.ws.jwt;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import pe.com.swilveis.ws.dto.JwtDto;
import pe.com.swilveis.ws.entity.AuthUser;
import pe.com.swilveis.ws.utils.Properties;

@Component
public class JwtProvider {
	
	private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);
	
	@Autowired
	private Properties properties;
	
	public String generateToken(Authentication authentication) {
		AuthUser authUser = (AuthUser) authentication.getPrincipal();
		return Jwts.builder().setSubject(authUser.getUsername())
				.claim("nombre", authUser.getNombre())
				.claim("roles", authUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + properties.getJwtExpiration() * 1000))
				.signWith(SignatureAlgorithm.HS512, properties.getJwtSecret().getBytes())
				.compact();
	}
	
	public String getNombreUsuarioFromToken(String token) {
		return Jwts.parser().setSigningKey(properties.getJwtSecret().getBytes()).parseClaimsJws(token).getBody().getSubject();
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(properties.getJwtSecret().getBytes()).parseClaimsJws(token);
			return true;
		} catch (MalformedJwtException e) {
			logger.error("Token mal formado.");
		} catch (UnsupportedJwtException e) {
			logger.error("Token no soportado.");
		} catch (ExpiredJwtException e) {
			logger.error("Token expirado.");
		} catch (IllegalArgumentException e) {
			logger.error("Token vacío.");
		} catch (SignatureException e) {
			logger.error("Fallo en la firma.");
		}
		return false;
	}
	
	public String refreshToken(JwtDto jwtDto) throws ParseException {
		JWT jwt = JWTParser.parse(jwtDto.getToken());
		JWTClaimsSet claims = jwt.getJWTClaimsSet();
		String nombreUsuario = claims.getSubject();
		String nombre = (String) claims.getClaim("nombre");
		@SuppressWarnings("unchecked")
		List<String> roles = (List<String>) claims.getClaim("roles");
		
		return Jwts.builder().setSubject(nombreUsuario)
				.claim("nombre", nombre)
				.claim("roles", roles)
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + properties.getJwtExpiration() * 1000))
				.signWith(SignatureAlgorithm.HS512, properties.getJwtSecret().getBytes())
				.compact();
	}

}
