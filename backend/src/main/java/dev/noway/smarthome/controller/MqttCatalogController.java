package dev.noway.smarthome.controller;

import dev.noway.smarthome.model.MqttCatalogModel;
import dev.noway.smarthome.service.MqttCatalogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDateTime;

@Controller
@ComponentScan
public class MqttCatalogController {

    private static final Logger logger = LoggerFactory.getLogger(MqttCatalogController.class);

    @Autowired
    private MqttCatalogService mqttCatalogService;
    @Autowired
    private GlobalController globalController;

    @RequestMapping(value = {"/api/mqtt/saveCatalog"}, method = RequestMethod.POST)
    public String saveMqtt(@ModelAttribute("reqMqtt") MqttCatalogModel reqMqttCatalogModel,
                           final RedirectAttributes redirectAttributes) {
        logger.info("/mqtt/save");
        if (globalController.isLogin()) {
            try {
                reqMqttCatalogModel.setAddDate(LocalDateTime.now());
                mqttCatalogService.save(reqMqttCatalogModel);
                redirectAttributes.addFlashAttribute("msg", "success");
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("msg", "fail");
                logger.error("save: " + e.getMessage());
            }
        }
        return "redirect:/home";
    }

    public static Logger getLogger() {
        return logger;
    }

    public MqttCatalogService getMqttCatalogService() {
        return mqttCatalogService;
    }

    public void setMqttCatalogService(MqttCatalogService mqttCatalogService) {
        this.mqttCatalogService = mqttCatalogService;
    }

    public GlobalController getGlobalController() {
        return globalController;
    }

    public void setGlobalController(GlobalController globalController) {
        this.globalController = globalController;
    }

    public MqttCatalogController() {
    }

    @Override
    public String toString() {
        return "MqttCatalogController{" +
                "mqttCatalogService=" + mqttCatalogService +
                ", globalController=" + globalController +
                '}';
    }
}