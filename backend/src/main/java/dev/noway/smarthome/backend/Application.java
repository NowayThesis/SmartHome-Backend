package dev.noway.smarthome.backend;

import dev.noway.smarthome.backend.mqtt.MqttConnection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Application {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
        MqttConnection.MqttSubscriber("#");
        MqttConnection.MqttPublisher("noway/home/relay", "Teszt üzenet");
    }
}