package dev.noway.smarthome.backend;
import dev.noway.smarthome.backend.mqtt.MqttConnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Application {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
        MqttConnect.publishMqttMessage("tcp://192.168.1.11:1883", "noway/home/relay", "Üzenet");
        MqttConnect.subscribMqttMessage("tcp://192.168.1.11:1883", "noway/home/relay");
    }

}