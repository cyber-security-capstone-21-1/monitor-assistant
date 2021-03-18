package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "사이버수사관모니터링업무보조시스템 API",
        description = "API 명세",
        version = "v1",
        contact = @Contact(name = "admin", email = "admin@police.go.kr"),
        license = @License(name = "Apache 2.0", url = "http://www.apache.org/licenses/LICENSE-2.0.html"))
)
@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi testApi() {
        String[] paths = { "/test/**" };
        return GroupedOpenApi.builder()
                .setGroup("TEST API")
                .pathsToMatch(paths)
                .build();
    }
}
