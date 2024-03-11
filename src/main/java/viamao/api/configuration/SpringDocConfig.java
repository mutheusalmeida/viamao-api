package viamao.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SpringDocConfig {

	@Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info()
                    .title("ViaMão API")
                    .description("Travel planner built to help users manage their trips.")
                    .contact(new Contact()
                    .name("Matheus Almeida")
                    .email("mutheusalmeida@gmail.com")));
	}
	
}
