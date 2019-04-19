package dev.noway.smarthome.utils;

import dev.noway.smarthome.model.MqttCatalogModel;
import dev.noway.smarthome.service.MqttCatalogService;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import java.time.LocalDateTime;
import java.util.concurrent.Callable;

@ComponentScan
public class MqttPub implements Callable<Void> {

    @Autowired
    private MqttConnect mqttConnect;
    @Autowired
    private static MqttCatalogService mqttCatalogService;
    private String myTopic = "noway/";
    private String myMessage = "test";

    public MqttPub(String getTopic, String getMessage) throws Exception {
        setMyTopic(getTopic);
        setMyMessage(getMessage);
        MqttCatalogModel mqttCatalogeModel = new MqttCatalogModel(getTopic, getMessage);
        try {
            mqttCatalogeModel.setAddDate(LocalDateTime.now());
            mqttCatalogService.save(mqttCatalogeModel);
        } catch (Exception e) {
            System.out.println("Hiba!");
        }
        MqttClient mqttclient = this.mqttConnect.getMqttClient();
        Callable<Void> target = (Callable<Void>) new MqttConnect(mqttclient);
        target.call();
    }

    @Override
    public Void call() throws Exception {
        if ( !mqttConnect.getMqttClient().isConnected()) {
            System.out.println("Client not connected.");
            return null;
        }
        byte[] payload = String.format(this.myMessage).getBytes();
        MqttMessage msg = new MqttMessage(payload);
//        0 - „legfeljebb egyszer” szemantika, más néven „tűz-elfelejt”. Használja ezt az opciót, ha az üzenetvesztés elfogadható, mivel nem igényel semmilyen nyugtázást vagy kitartást
//        1 - „legalább egyszer” szemantika. Használja ezt az opciót, ha az üzenetvesztés nem elfogadható,  és az előfizetői képesek kezelni a másolatokat
//        2 - „pontosan egyszer” szemantika. Használja ezt az opciót, ha az üzenetvesztés nem elfogadható,  és az előfizetői nem képesek kezelni a másolatokat
        msg.setQos(0);
        msg.setRetained(true);
        mqttConnect.getMqttClient().publish(myTopic,msg);
        return null;
    }

    public String getMyTopic() {
        return myTopic;
    }

    public void setMyTopic(String myTopic) {
        this.myTopic = myTopic;
    }

    public String getMyMessage() {
        return myMessage;
    }

    public void setMyMessage(String myMessage) {
        this.myMessage = myMessage;
    }
}