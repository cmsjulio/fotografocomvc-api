package com.fotografocomvc;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FotografocomvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(FotografocomvcApplication.class, args);
	}

	@Bean
	public OpenAPI openAPI(@Value("${springdoc.version}") String appVersion) {
		return new OpenAPI()
				.components(new Components())
				.info(new Info().title("Distribuição de Vagas API").version(appVersion));
	}

}
