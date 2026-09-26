package ht452.ws.rest_springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "World Geography API", version = "1.0", description = "REST API for countries and rivers"))
public class GeoSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeoSpringApplication.class, args);
	}

}
