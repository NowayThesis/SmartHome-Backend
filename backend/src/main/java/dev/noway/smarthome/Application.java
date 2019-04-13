package dev.noway.smarthome;

import dev.noway.smarthome.mqtt.MqttConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

@SpringBootApplication
public class Application implements WebMvcConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(dev.noway.smarthome.Application.class);

    public static void main(String[] args) throws Throwable {
        SpringApplication.run(Application.class, args);
        MqttConnection.MqttSubscriber("#");
        MqttConnection.MqttPublisher("noway/home/relay", "Teszt üzenet");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("/webjars/");

    }

    @Bean
    public Java8TimeDialect java8TimeDialect() {
        return new Java8TimeDialect();
    }

}
