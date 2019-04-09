package dev.noway.smarthome.service.impl.config;

import dev.noway.smarthome.persistence.config.PersistenceConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableAutoConfiguration
@EnableScheduling
@Import(PersistenceConfiguration.class)
@ComponentScan("dev.noway.smarthome.service")
public class ServiceConfiguration {
}
