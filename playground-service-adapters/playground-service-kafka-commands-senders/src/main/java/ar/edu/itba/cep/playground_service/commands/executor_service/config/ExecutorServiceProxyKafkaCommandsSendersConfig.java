package ar.edu.itba.cep.playground_service.commands.executor_service.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for the executor service proxy for command messages.
 */
@Configuration
@ComponentScan(basePackages = {
        "ar.edu.itba.cep.playground_service.commands.executor_service"
})
public class ExecutorServiceProxyKafkaCommandsSendersConfig {
}
