package dev.noway.smarthome.controller;

import dev.noway.smarthome.model.MqttCatalogModel;
import dev.noway.smarthome.model.MqttLastMessageModel;
import dev.noway.smarthome.service.MqttCatalogService;
import dev.noway.smarthome.service.MqttLastMessageService;
import dev.noway.smarthome.utils.MqttSub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("api/mqtt/sub")
public class ApiMqttSubController {

    @Autowired
    private MqttCatalogService mqttCatalogService;
    @Autowired
    private MqttLastMessageService mqttLastMessageService;
    @Autowired
    private MqttSub mqttSub;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(path = {""}, method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> calalogStatus() {
        Collection<MqttCatalogModel> mqttList = mqttCatalogService.findAll();
        return  ResponseEntity.accepted().body(mqttList);
    }

    @RequestMapping(path = {"/last"}, method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> lastMessageStatus() {
        Collection<MqttLastMessageModel> mqttList = mqttLastMessageService.findAll();
        return  ResponseEntity.accepted().body(mqttList);
    }

    @RequestMapping(path = {"/start"}, method = RequestMethod.POST)
    public String catalogStart() throws Exception {
        mqttSub.mqttSubStart("#");
        return "redirect:/home";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(path = {"/stop"}, method = RequestMethod.POST)
    public String catalogStop() throws Exception {
        mqttSub.mqttSubStop("#");
        return "redirect:/home";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(path = {"/topic"}, method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public List<MqttCatalogModel> calalogStatus(@RequestBody MqttCatalogModel reqMqtt) {
        return mqttCatalogService.findTopic(reqMqtt.getTopic());
    }
}
