package pe.com.swilveis.ws.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
	
	@Bean
	public CorsFilter corsFilter() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		final CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("OPTIONS");
		config.addAllowedMethod("HEAD");
		config.addAllowedMethod("GET");
		config.addAllowedMethod("PUT");
		config.addAllowedMethod("POST");
		config.addAllowedMethod("DELETE");
		config.addAllowedMethod("PATCH");
		config.addAllowedOrigin("X-Auth-Token");
		config.addAllowedOrigin("x-xsrf-token");
		config.addAllowedOrigin("Content-Type");
		config.addAllowedOrigin("Access-Control-Allow-Headers");
		config.addAllowedOrigin("X-Requested-With");
		config.addAllowedOrigin("Authorization");
		config.addAllowedOrigin("Origin");
		config.addAllowedOrigin("Accept");
		config.addAllowedOrigin("Access-Control-Request-Method");
		config.addAllowedOrigin("Access-Control-Request-Headers");
		config.addAllowedOrigin("Access-Control-Allow-Origin");
//		config.addAllowedOrigin("idUsuario");
		config.addAllowedOrigin("token");
		
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}


}
