package dev.noway.smarthome;

import dev.noway.smarthome.model.MqttLastMessageModel;
import dev.noway.smarthome.service.MqttLastMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminConfiguration {

    @Autowired
    private MqttLastMessageService mqttLastMessageService;

    @Bean(name = "adminService")
    public String adminService() {
        String text = "";
        for (MqttLastMessageModel sor : mqttLastMessageService.findAll()) {
            text += "$ " + sor.getId() + ". " + sor.getAddDate().toString() + " " + sor.getTopic() + " :: " + sor.getMessage();
        }
        text += "\n";
        return text;
    }
}

