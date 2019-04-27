package dev.noway.smarthome.controller;

import dev.noway.smarthome.model.MqttCatalogModel;
import dev.noway.smarthome.model.MqttLastMessageModel;
import dev.noway.smarthome.service.MqttCatalogService;
import dev.noway.smarthome.service.MqttLastMessageService;
import dev.noway.smarthome.utils.MqttConnect;
import dev.noway.smarthome.utils.MqttPub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;

@Controller
@RequestMapping("api/mqtt/pub")
public class ApiMqttPubController {

    @Autowired
    private MqttPub mqttPub;
    @Autowired
    private MqttConnect mqttConnect;
    @Autowired
    private MqttLastMessageService lastMessageService;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(path = "", method = RequestMethod.GET)
    @ResponseBody
    public String getFoosBySimplePath() {
        return "Post Mqtt data!";
    }

    @RequestMapping(path = {""}, method = RequestMethod.POST,
            consumes = {"application/json", MediaType.APPLICATION_FORM_URLENCODED_VALUE},
            produces = "application/json")
    @ResponseBody
    public MqttLastMessageModel publisher(@RequestBody MqttCatalogModel reqMqtt) throws Exception {
        mqttPub.sendPublish(reqMqtt);
        return lastMessageService.findTopic(reqMqtt.getTopic());
    }

    @RequestMapping(path = "/but", method = RequestMethod.POST)
    public String saveProfileJson(MqttCatalogModel reqMqtt) throws Exception {
        reqMqtt.setTopic("cmnd/"+reqMqtt.getTopic()+"/POWER");
        mqttPub.sendPublish(reqMqtt);
        return "redirect:/home";
    }

    @RequestMapping(path = "/cleen", method = RequestMethod.POST)
    public String cleenProfileJson(MqttCatalogModel reqMqtt) throws Exception {
        reqMqtt.setTopic(reqMqtt.getTopic());
        mqttConnect.getMqttClient().publish(reqMqtt.getTopic(), new byte[0],0,true);
        return "redirect:/home";
    }
}