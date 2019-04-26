package dev.noway.smarthome.utils;

import dev.noway.smarthome.model.MqttCatalogModel;
import dev.noway.smarthome.model.MqttLastMessageModel;
import dev.noway.smarthome.service.MqttCatalogService;
import dev.noway.smarthome.service.MqttLastMessageService;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@Service
@ComponentScan
public class MqttPub extends MqttConnect {

    @Autowired
    private MqttCatalogService mqttCatalogService;
    @Autowired
    private MqttLastMessageService mqttLastMessageService;
    @Autowired
    private MqttConnect mqttConnect;
    private int mqttPubQos = 2;
    private boolean MQTT_PUT_RETAINED = true;

    void save(MqttCatalogModel mqttCatalogModel) {
        try {
            mqttCatalogService.save(mqttCatalogModel);
        } catch (NullPointerException e) {
            System.out.println("MQTT MqttCatalogModel save:" + e.toString());
        }
        try {
            MqttLastMessageModel mqttLast = new MqttLastMessageModel(mqttCatalogModel.getTopic(), mqttCatalogModel.getMessage());
            if (mqttLastMessageService.findTopic(mqttLast.getTopic()) == null) {
                mqttLastMessageService.save(mqttLast);
            } else {
                mqttLastMessageService.update(mqttLast);
            }
        } catch (NullPointerException e) {
            System.out.println("MQTT MqttLastMessageModel save:" + e.toString());
        }
    }

    public void sendPublish(MqttCatalogModel mqttCatalogModel) throws Exception {
        MqttCatalogModel newMqttCatalogModel1 = new MqttCatalogModel(mqttCatalogModel.getTopic(), mqttCatalogModel.getMessage());
        byte[] payload = String.format(newMqttCatalogModel1.getMessage()).getBytes();
        MqttMessage msg = new MqttMessage(payload);
//        0 - „legfeljebb egyszer” szemantika, más néven „tűz-elfelejt”. Használja ezt az opciót,
// ha az üzenetvesztés elfogadható, mivel nem igényel semmilyen nyugtázást vagy kitartást
//        1 - „legalább egyszer” szemantika. Használja ezt az opciót, ha az üzenetvesztés
// nem elfogadható,  és az előfizetői képesek kezelni a másolatokat
//        2 - „pontosan egyszer” szemantika. Használja ezt az opciót, ha az üzenetvesztés
// nem elfogadható,  és az előfizetői nem képesek kezelni a másolatokat
        msg.setQos(mqttPubQos);
        msg.setRetained(MQTT_PUT_RETAINED);
        mqttConnect.getMqttClient().publish(newMqttCatalogModel1.getTopic(),msg);
        save(newMqttCatalogModel1);
    }
}
