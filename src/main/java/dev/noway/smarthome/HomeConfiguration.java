package dev.noway.smarthome;

import dev.noway.smarthome.service.MqttLastMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HomeConfiguration {

    @Autowired
    private MqttLastMessageService mqttLastMessageService;

    @Bean("homeFirstService")
    public String homeFirstService() {
        return mqttLastMessageService.findTopic("stat/84/POWER").getMessage();
    }
}
