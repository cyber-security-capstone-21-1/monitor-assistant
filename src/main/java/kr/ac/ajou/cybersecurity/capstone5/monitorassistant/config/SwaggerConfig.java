package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI monitorAssistantAPI() {
        Server server = new Server();
        server.setUrl("https://monitor-assistant.com");
        // server.setUrl("http://localhost");
        return new OpenAPI()
                .servers(List.of(server))
                .info(new Info()
                        .title("사이버수사관모니터링업무보조시스템 API")
                        .description("API 명세")
                        .version("v1")
                        .license(new License().name("Apache 2.0").url("http://www.apache.org/licenses/LICENSE-2.0.html"))
                );
    }

}
