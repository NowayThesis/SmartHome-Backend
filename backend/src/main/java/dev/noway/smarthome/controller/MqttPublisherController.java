package dev.noway.smarthome.controller;

import dev.noway.smarthome.model.MqttCatalogModel;
import dev.noway.smarthome.service.MqttCatalogService;
import dev.noway.smarthome.utils.MqttPub;
import dev.noway.smarthome.utils.MqttSub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDateTime;

@RestController
@Controller
@ComponentScan
@RequestMapping("mqtt")
public class MqttPublisherController {

    @Autowired
    MqttCatalogService mqttCatalogService;

    @GetMapping("/sub/start")
    public void MqttCatalogStart() throws Exception {
        new MqttSub();
    }

    @GetMapping("/pub/on")
    public void Nowaydb_1081_On() throws Exception {
        new MqttPub("cmnd/nowaydb_1081/POWER", "ON");
    }

    @GetMapping("/pub/off")
    public void Nowaydb_1081_Off() throws Exception {
        new MqttPub("cmnd/nowaydb_1081/POWER", "OFF");
    }


    @RequestMapping(value = {"/sub/save"}, method = RequestMethod.POST)
    public void saveMqtt(@ModelAttribute("reqMqtt") MqttCatalogModel reqMqttCatalogModel,
                           final RedirectAttributes redirectAttributes) {
        try {
            reqMqttCatalogModel.setAddDate(LocalDateTime.now());
            mqttCatalogService.save(reqMqttCatalogModel);
        } catch (Exception e) {
            System.out.println("Hiba!");
        }
    }
}