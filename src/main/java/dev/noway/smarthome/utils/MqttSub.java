package dev.noway.smarthome.utils;

import dev.noway.smarthome.model.MqttCatalogModel;
import dev.noway.smarthome.model.MqttLastMessageModel;
import dev.noway.smarthome.service.MqttBrokerService;
import dev.noway.smarthome.service.MqttCatalogService;
import dev.noway.smarthome.service.MqttLastMessageService;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Service
public class MqttSub{

//    @Autowired
//    private LocalMachineInformationService localMachineInformationService;
    @Autowired
    private MqttCatalogService mqttCatalogService;
    @Autowired
    private MqttLastMessageService mqttLastMessageService;
    @Autowired
    private MqttBrokerService mqttBrokerService;
    @Autowired
    private MqttConnect mqttConnect;

    private boolean start = false;
    private int mqttSubQos = 2;
    private int hardwerId = 0;

    void save(MqttCatalogModel mqttCatalogModel) {
        try {
            mqttCatalogService.save(mqttCatalogModel);
            System.out.format("-----------------------\nMQTT save: topic={%s}, \tpayload={%s}\n-----------------------\n", mqttCatalogModel.getTopic(), mqttCatalogModel.getMessage());
        } catch (NullPointerException e) {
            System.out.println("MQTT esemeny mentes hiba: " + e.toString());
        }
        try {
            MqttLastMessageModel mqttLast = new MqttLastMessageModel(mqttCatalogModel.getTopic(), mqttCatalogModel.getMessage());
            if (mqttLastMessageService.findTopic(mqttLast.getTopic()) == null) {
                mqttLastMessageService.save(mqttLast);
            } else {
                mqttLastMessageService.update(mqttLast);
            }
        } catch (NullPointerException e) {
            System.out.println(e.toString());
        }
    }

    public void mqttSubStart(String getTopic) throws Exception {
        if (mqttConnect.getMqttClient().isConnected()) {
            // visszahívás és a fő végrehajtási szál közötti szinkronizálási mechanizmus,
            // minden alkalommal új üzenet érkezésekor csökken
            CountDownLatch receivedSignal = new CountDownLatch(1);
            // második érvként egy IMqttMessageListener példányt vesz fel
            // egy egyszerű lambda függvényt használunk, amely feldolgozza a hasznos
            // terhelést és csökkenti a számlálót. Ha a megadott időablakban nem érkezik
            // elég üzenet (1 perc), a  várakozási () módszer kivételeket dob.
            mqttConnect.getMqttClient().subscribe(getTopic, mqttSubQos, (topic, msg) -> {
                byte[] payload = msg.getPayload();
                save(new MqttCatalogModel(topic, new String(payload), 0, 0));
                receivedSignal.countDown();
            });
            receivedSignal.await(1, TimeUnit.MINUTES);
        } else {
            System.out.println("MQTT client connect error!");
        }
    }

    public void mqttSubStop(String getTopic) throws MqttException {
        if (mqttConnect.getMqttClient().isConnected()) {
            mqttConnect.getMqttClient().unsubscribe(getTopic);
        } else {
            System.out.println("MQTT client connect error!");
        }

    }
//    private int getHardwerId(){
//        if (!start) {
//            try {
//                MqttBrokerModel mb;
//                try {
//                    mb = mqttBrokerService.findHardwer(localMachineInformationService.getHardwerAddress());
//                    hardwerId = mb.getId();
//                } catch  (Exception e) {
//                    hardwerId = mqttBrokerService.findHardwer(mqttBrokerService.save(new MqttBrokerModel(localMachineInformationService.getNetwork().toString(), localMachineInformationService.getHardwerAddress())).getHardwer()).getId();
//                }
//            } catch (Exception e) {
//                hardwerId = 0;
//            }
//            start = true;
////        }
//        return hardwerId;
//    }
}
