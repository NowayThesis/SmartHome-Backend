package dev.noway.smarthome.utils;

import dev.noway.smarthome.model.MqttCatalogModel;
import dev.noway.smarthome.model.MqttLastMessageModel;
import dev.noway.smarthome.service.MqttCatalogService;
import dev.noway.smarthome.service.MqttLastMessageService;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Service
public class MqttSub{

    @Autowired
    private MqttCatalogService mqttCatalogService;
    @Autowired
    private MqttLastMessageService mqttLastMessageService;
    @Autowired
    private MqttConnect mqttConnect;

    private int mqttSubQos = 2;

    void save(MqttCatalogModel mqttCatalogModel) {
        try {
            mqttCatalogService.save(mqttCatalogModel);
            System.out.format("-----------------------\n| MQTT save: topic={%s}, \tpayload={%s}\n-----------------------\n", mqttCatalogModel.getTopic(), mqttCatalogModel.getMessage());
        } catch (NullPointerException e) {
            System.out.println("MQTT esemeny mentes hiba: " + e.toString());
        }
        try {
            MqttLastMessageModel mqttLast = new MqttLastMessageModel(mqttCatalogModel.getTopic(), mqttCatalogModel.getMessage());
            if (mqttLastMessageService.findTopic(mqttCatalogModel.getTopic()) == null) {
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
            CountDownLatch receivedSignal = new CountDownLatch(1);
            mqttConnect.getMqttClient().subscribe(getTopic, mqttSubQos, (topic, msg) -> {
                byte[] payload = msg.getPayload();
                save(new MqttCatalogModel(topic, new String(payload)));
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
}
