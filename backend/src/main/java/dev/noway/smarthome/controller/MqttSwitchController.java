package dev.noway.smarthome.controller;

//import dev.noway.smarthome.controller.GlobalController;
//import dev.noway.smarthome.model.MqttCatalogModel;
//import dev.noway.smarthome.service.MqttCatalogService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import java.time.LocalDateTime;
//
//@Controller
//@ComponentScan
//public class MqttSwitchController {
//
//    private static final Logger logger = LoggerFactory.getLogger(MqttSwitchController.class);
//
//    @Autowired
//    private MqttCatalogService mqttCatalogService;
//    @Autowired
//    private GlobalController globalController;
//
//    @RequestMapping(value = {"/task/saveTask"}, method = RequestMethod.POST)
//    public String saveMqtt(@ModelAttribute("reqMqtt") MqttCatalogModel reqMqttCatalogModel,
//                           final RedirectAttributes redirectAttributes) {
//        logger.info("/task/save");
//        try {
//            reqMqttCatalogModel.setAddDate(LocalDateTime.now());
//            reqMqttCatalogModel.setTopic("/././.");
//            reqMqttCatalogModel.setMessage("");
//            mqttCatalogService.save(reqMqttCatalogModel);
//            redirectAttributes.addFlashAttribute("msg", "success");
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("msg", "fail");
//            logger.error("save: " + e.getMessage());
//        }
//
//        return "redirect:/home";
//    }
//
//    @RequestMapping(value = {"/task/editTask"}, method = RequestMethod.POST)
//    public String editMqtt(@ModelAttribute("editTask") MqttSwitchModel editMqttSwitchModel, Model model) {
//        logger.info("/task/editTask");
//        try {
//            MqttSwitchModel mqttSwitchModel = mqttService.findById(editMqttSwitchModel.getId());
//            if (!mqttSwitchModel.equals(editMqttSwitchModel)) {
//                mqttService.update(editMqttSwitchModel);
//                model.addAttribute("msg", "success");
//            } else {
//                model.addAttribute("msg", "same");
//            }
//        } catch (Exception e) {
//            model.addAttribute("msg", "fail");
//            logger.error("editTask: " + e.getMessage());
//        }
//        model.addAttribute("editTodo", editMqttSwitchModel);
//        return "edit";
//    }
//
//    @RequestMapping(value = "/task/{operation}/{id}", method = RequestMethod.GET)
//    public String mqttOperation(@PathVariable("operation") String operation,
//                                @PathVariable("id") int id, final RedirectAttributes redirectAttributes,
//                                Model model) {
//
//        logger.info("/task/operation: {} ", operation);
//        if (operation.equals("trash")) {
//            MqttSwitchModel mqttSwitchModel = mqttService.findById(id);
//            if (mqttSwitchModel != null) {
//                mqttService.update(mqttSwitchModel);
//                redirectAttributes.addFlashAttribute("msg", "trash");
//            } else {
//                redirectAttributes.addFlashAttribute("msg", "notfound");
//            }
//        }
//        if (operation.equals("restore")) {
//            MqttSwitchModel mqttSwitchModel = mqttService.findById(id);
//            if (mqttSwitchModel != null) {
//                mqttService.update(mqttSwitchModel);
//                redirectAttributes.addFlashAttribute("msg", "active");
//                redirectAttributes.addFlashAttribute("msgText", "Topic " + mqttSwitchModel.getTopic() + " Restored Successfully.");
//            } else {
//                redirectAttributes.addFlashAttribute("msg", "active_fail");
//                redirectAttributes.addFlashAttribute("msgText", "Topic Activation failed !!! Task:" + mqttSwitchModel.getTopic());
//
//            }
//        } else if (operation.equals("delete")) {
//            if (mqttService.delete(id)) {
//                redirectAttributes.addFlashAttribute("msg", "del");
//                redirectAttributes.addFlashAttribute("msgText", "Mqtt deleted permanently");
//            } else {
//                redirectAttributes.addFlashAttribute("msg", "del_fail");
//                redirectAttributes.addFlashAttribute("msgText", "Mqtt could not deleted. Please try later");
//            }
//        } else if (operation.equals("edit")) {
//            MqttSwitchModel editMqttSwitchModel = mqttService.findById(id);
//            if (editMqttSwitchModel != null) {
//                model.addAttribute("editMqtt", editMqttSwitchModel);
//                return "edit";
//            } else {
//                redirectAttributes.addFlashAttribute("msg", "notfound");
//            }
//        }
//        return "redirect:/mqtt";
//    }
//}
