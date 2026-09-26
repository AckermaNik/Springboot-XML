package hy452.ws.spring_client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "World Geography API", version = "1.0", description = "REST API for countries and rivers"))
public class SpringClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringClientApplication.class, args);
	}

}
