package dev.noway.smarthome.mqtt;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("mqtt")
public class MqttSubscriber {

    @GetMapping("/sub")
    public String MqttSubscriber() {
        String text = "<html><head><style>table {font-family: arial, sans-serif;border-collapse: collapse;width: 100%;}td, th {border: 1px solid #dddddd;text-align: left;padding: 8px;}tr:nth-child(even) {background-color: #dddddd;}</style></head><body><table style='width: 100%;'><tr><th>Id</th><th>Topic</th><th>Message</th><th>Date</th></tr>";
//        for (MqttModel aa : MqttServiceImpl.findAll()) {
//            text += aa.htmlTableRow() + "\n";
//        };
        return text + "</body></table>";
    }
}
