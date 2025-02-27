package sebtask.exrates.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
    @Bean
    fun customOpenAPI(): OpenAPI =
        OpenAPI()
            .info(
                Info()
                    .title("Exahange rates portal API")
                    .version("0.0.0")
                    .description(
                        "Can be used by http://localhost:4200 exrates-client (front)",
                    ),
            )
}
