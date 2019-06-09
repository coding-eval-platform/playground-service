package ar.edu.itba.cep.playground_service.commands.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for the Kafka Command senders module.
 */
@Configuration
@ComponentScan(basePackages = {
        "ar.edu.itba.cep.playground_service.commands.*.config"
})
public class KafkaCommandsSendersConfig {
}
