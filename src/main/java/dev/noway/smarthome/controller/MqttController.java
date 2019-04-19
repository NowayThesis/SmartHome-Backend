package dev.noway.smarthome.controller;

import dev.noway.smarthome.model.MqttCatalogModel;
import dev.noway.smarthome.service.MqttCatalogService;
import dev.noway.smarthome.utils.MqttConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@Controller
@ComponentScan
@RequestMapping("mqtt")
public class MqttController {

    @Autowired
    MqttCatalogService mqttCatalogService;

    @GetMapping("/sub/start")
    @ResponseBody
    public String MqttCatalogStart() throws Exception {
        MqttConnection.MqttSubscriber("#");
        return "Mqtt Subscriber Run!";
    }

    @GetMapping("/pub/on81")
    @ResponseBody
    public String Nowaydb_1081_On() throws Exception {
        MqttConnection.MqttPublisher("cmnd/nowaydb_1081/POWER", "ON");
        return "81: ON";
    }

    @GetMapping("/pub/off81")
    @ResponseBody
    public String Nowaydb_1081_Off() throws Exception {
        MqttConnection.MqttPublisher("cmnd/nowaydb_1081/POWER", "OFF");
        return "81: OFF";
    }

    @RequestMapping(value = "/pub", method = RequestMethod.GET)
    @ResponseBody
    public String getFoosBySimplePath() {
        return "Get some data";
    }

    @RequestMapping(value = {"/pub"}, method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String  publisher(@RequestBody MqttCatalogModel reqMqtt) throws Exception {
        System.out.println("POST: " + reqMqtt.getTopic() + " - " + reqMqtt.getMessage());
        mqttCatalogService.save(new MqttCatalogModel(reqMqtt.getTopic(), reqMqtt.getMessage()));
        MqttConnection.MqttPublisher("" + reqMqtt.getTopic(), "" + reqMqtt.getMessage());
        return reqMqtt.getMessage();
    }
}