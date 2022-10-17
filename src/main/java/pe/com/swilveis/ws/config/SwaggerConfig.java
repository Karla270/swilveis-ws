package pe.com.swilveis.ws.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public Docket api() throws Exception {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("pe.com.swilveis.ws.controller")).paths(PathSelectors.any())
				.build().apiInfo(getApiInfo()).globalOperationParameters(headers());
	}

	private ApiInfo getApiInfo() {
		return new ApiInfo("SwilveIS", "REST API SwilveIS", "1.0.0", "Terminos y Condiciones",
				new Contact("developers", "developers.com", "developers@gmail.com"), "Licencia de API",
				"URL de la Licencia", Collections.emptyList());
	}
	
	private List<Parameter> headers() {
		ParameterBuilder aParameterBuilder = new ParameterBuilder();
	    List<Parameter> aParameters = new ArrayList<>();
	    aParameters.clear();
	    aParameterBuilder.name("token").modelRef(new ModelRef("string")).parameterType("header").required(true).build();
	    aParameters.add(aParameterBuilder.build());
	    return aParameters;
	}

}
