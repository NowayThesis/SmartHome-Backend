package dev.noway.smarthome.utils;

import dev.noway.smarthome.model.MqttCatalogModel;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@Service
@ComponentScan
public class MqttPub extends MqttConnect {

    @Autowired
    private MqttConnect mqttConnect;
    private int mqttPubQos = 2;
    private boolean MQTT_PUT_RETAINED = true;

    public void sendPublish(MqttCatalogModel mqttCatalogModel) throws Exception {
        byte[] payload = String.format(mqttCatalogModel.getMessage()).getBytes();
        MqttMessage msg = new MqttMessage(payload);
//        0 - „legfeljebb egyszer” szemantika, más néven „tűz-elfelejt”. Használja ezt az opciót,
// ha az üzenetvesztés elfogadható, mivel nem igényel semmilyen nyugtázást vagy kitartást
//        1 - „legalább egyszer” szemantika. Használja ezt az opciót, ha az üzenetvesztés
// nem elfogadható,  és az előfizetői képesek kezelni a másolatokat
//        2 - „pontosan egyszer” szemantika. Használja ezt az opciót, ha az üzenetvesztés
// nem elfogadható,  és az előfizetői nem képesek kezelni a másolatokat
        msg.setQos(mqttPubQos);
        msg.setRetained(MQTT_PUT_RETAINED);
        mqttConnect.getMqttClient().publish(mqttCatalogModel.getTopic(),msg);
    }
}
