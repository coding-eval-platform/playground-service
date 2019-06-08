package ar.edu.itba.cep.playground_service.spring_data.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Spring Data Jpa Repositories.
 */
@Configuration
@ComponentScan(basePackages = {
        "ar.edu.itba.cep.playground_service.spring_data"
})
// TODO: add @EnableJpaRepositories if using Spring Data JPA
// TODO: add @EntityScan or resources/META-INF/orm.xml file
public class SpringDataConfig {
}
