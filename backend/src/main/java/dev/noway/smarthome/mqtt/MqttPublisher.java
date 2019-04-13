package dev.noway.smarthome.mqtt;

import dev.noway.smarthome.controller.GlobalController;
import dev.noway.smarthome.controller.MqttCatalogController;
import dev.noway.smarthome.model.MqttCatalogModel;
import dev.noway.smarthome.service.MqttCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@RestController
@Controller
@ComponentScan
@RequestMapping("mqtt")
public class MqttPublisher {

    @Autowired
    private MqttCatalogService mqttCatalogService;
    @Autowired
    private GlobalController globalController;

    @GetMapping("/pub/on")
    public void Nowaydb_1081_On() throws Exception {
        MqttConnection.MqttPublisher("cmnd/nowaydb_1081/POWER", "ON");
//        MqttSwitchModel mqttSwitchModel = new MqttSwitchModel("cmnd/nowaydb_1081/POWER", "ON");
//        try {
//            mqttSwitchModel.setAddDate(LocalDateTime.now());
//            mqttSwitchService.save(mqttSwitchModel);
//        } catch (Exception e) {
//            System.out.println("Hiba!");
//        }
    }

    @GetMapping("/pub/off")
    public void Nowaydb_1081_Off() throws Exception {
        MqttConnection.MqttPublisher("cmnd/nowaydb_1081/POWER", "OFF");
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