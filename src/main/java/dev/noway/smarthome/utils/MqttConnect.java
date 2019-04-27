package dev.noway.smarthome.utils;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@ComponentScan
public class MqttConnect {

    private MqttClient mqttClient;
    private String mqttBrokerUrl = "192.168.1.15";

    public MqttClient getMqttClient(){
        if (mqttClient == null) {
            String clientId = UUID.randomUUID().toString();
            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(3600);
            options.setKeepAliveInterval(3600);
//        options.setUserName("");
//        options.setPassword("");
            String tmpDir = System.getProperty("user.dir") + "/target";
            MqttDefaultFilePersistence dataStore = new MqttDefaultFilePersistence(tmpDir);
            try {
                mqttClient = new MqttClient("tcp://" + mqttBrokerUrl + ":1883", clientId, dataStore);
                mqttClient.connect(options);
            } catch (Exception e) {
                System.out.println("Local MQTT client connect error!");
            }
        }
        return mqttClient;
    }
}