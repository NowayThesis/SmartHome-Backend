package dev.noway.smarthome.controller;

import dev.noway.smarthome.model.MqttBrokerModel;
import dev.noway.smarthome.service.MqttBrokerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;

@Controller
@RequestMapping("api/mqtt/broker")
public class ApiMqttBrokerController {

    @Autowired
    private MqttBrokerService mqttBrokerService;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"/all"}, method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> brokers() {
        Collection<MqttBrokerModel> mqttBrokerList = mqttBrokerService.findAll();
        return  ResponseEntity.accepted().body(mqttBrokerList);
    }
}
