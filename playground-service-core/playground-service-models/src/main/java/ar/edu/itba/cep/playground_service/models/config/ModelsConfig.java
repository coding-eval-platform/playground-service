package ar.edu.itba.cep.playground_service.models.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for the model's module.
 */
@Configuration
@ComponentScan(basePackages = {
        "ar.edu.itba.cep.playground_service.models"
})
public class ModelsConfig {
}
