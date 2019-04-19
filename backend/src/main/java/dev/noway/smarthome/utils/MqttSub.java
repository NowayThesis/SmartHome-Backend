package dev.noway.smarthome.utils;

import dev.noway.smarthome.service.MqttCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@ComponentScan
public class MqttSub {

    @Autowired
    private MqttConnect mqttConnect;
    @Autowired
    private MqttCatalogService mqttCatalogService;

    public MqttSub() throws Exception {
        // visszahívás és a fő végrehajtási szál közötti szinkronizálási mechanizmus, minden alkalommal új üzenet érkezésekor csökken
        CountDownLatch receivedSignal = new CountDownLatch(10);
        // második érvként egy IMqttMessageListener példányt vesz fel
        // egy egyszerű lambda függvényt használunk, amely feldolgozza a hasznos terhelést és csökkenti a számlálót. Ha a megadott időablakban nem érkezik elég üzenet (1 perc), a  várakozási () módszer kivételeket dob.
        this.mqttConnect
                .getMqttClient()
                .subscribe("#", (topic, msg) -> {
            byte[] payload = msg.getPayload();
            System.out.format("MQTT: \ttopic={%s},\n\t\tpayload={%s}\n",topic,new String(payload));
            receivedSignal.countDown();
        });
        receivedSignal.await(1, TimeUnit.MINUTES);
    }
}