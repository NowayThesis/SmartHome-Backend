package dev.noway.smarthome.controller;

import dev.noway.smarthome.model.MqttCatalogModel;
import dev.noway.smarthome.utils.MqttConnect;
import dev.noway.smarthome.utils.MqttPub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/mqtt/pub")
public class ApiMqttPubController {

    @Autowired
    private MqttPub mqttPub;
    @Autowired
    private MqttConnect mqttConnect;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public String getFoosBySimplePath() {
        return "Post Mqtt data!";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {""}, method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String publisher(@RequestBody MqttCatalogModel reqMqtt) throws Exception {
        mqttPub.sendPublish(reqMqtt);
        return reqMqtt.getMessage();
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"/cleen"}, method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String del(@RequestBody MqttCatalogModel reqMqtt) throws Exception {
        mqttPub.sendPublish(reqMqtt);
        mqttConnect.getMqttClient().publish(reqMqtt.getTopic(), new byte[0],0,true);
        return reqMqtt.getMessage();
    }
}