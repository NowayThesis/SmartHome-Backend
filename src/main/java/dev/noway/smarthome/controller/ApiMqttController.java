package dev.noway.smarthome.controller;

import dev.noway.smarthome.model.MqttCatalogModel;
import dev.noway.smarthome.service.MqttCatalogService;
import dev.noway.smarthome.utils.MqttPub;
import dev.noway.smarthome.utils.MqttSub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("api/mqtt")
public class ApiMqttController {

    @Autowired
    private MqttCatalogService mqttCatalogService;
    @Autowired
    private MqttSub mqttSub;
    @Autowired
    private MqttPub mqttPub;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"/sub/start"}, method = RequestMethod.GET)
    public String catalogStart() throws Exception {
        mqttSub.mqttSubStart("#");
        return "redirect:/home";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"/sub/stop"}, method = RequestMethod.GET)
    public String catalogStop() throws Exception {
        mqttSub.mqttSubStop("#");
        return "redirect:/home";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"/sub"}, method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> calalogStatus() {
        Collection<MqttCatalogModel> mqttList = mqttCatalogService.findAll();
        return  ResponseEntity.accepted().body(mqttList);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"/sub/topic"}, method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public List<MqttCatalogModel> calalogStatus(@RequestBody MqttCatalogModel reqMqtt) throws Exception {
        return mqttCatalogService.findTopic(reqMqtt.getTopic());
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/pub", method = RequestMethod.GET)
    @ResponseBody
    public String getFoosBySimplePath() {
        return "Post Mqtt data!";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"/pub"}, method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String publisher(@RequestBody MqttCatalogModel reqMqtt) throws Exception {
        mqttPub.sendPublish(reqMqtt);
        return reqMqtt.getMessage();
    }
}