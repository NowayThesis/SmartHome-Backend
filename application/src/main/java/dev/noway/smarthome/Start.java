package dev.noway.smarthome;

import dev.noway.smarthome.mqtt.MqttConnection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Start {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Start.class, args);
        MqttConnection.MqttSubscriber("#");
        MqttConnection.MqttPublisher("noway/home/relay", "Teszt üzenet");
    }
}