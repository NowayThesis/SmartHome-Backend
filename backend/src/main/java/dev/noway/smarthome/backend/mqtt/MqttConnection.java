package dev.noway.smarthome.backend.mqtt;

import java.util.UUID;
import java.util.concurrent.*;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttConnection implements Callable<Void> {

    private static final String URL = "tcp://192.168.1.11:1883";
    private static String TOPIC = "noway/home/relay";
    private static String message = "Robi teszt";
    private IMqttClient client;

    private MqttConnection(IMqttClient client) {
        this.client = client;
    }

    @Override
    public Void call() throws Exception {
        if ( !client.isConnected()) {
            System.out.println("Client not connected.");
            return null;
        }
        MqttMessage msg = post();
        msg.setQos(0);
        msg.setRetained(true);
        client.publish(TOPIC,msg);
        return null;
    }

    private MqttMessage post() {
        byte[] payload = String.format(this.message).getBytes();
        MqttMessage msg = new MqttMessage(payload);
        return msg;
    }

    public static void MqttSubscriber(String getTopic) throws Exception {
        String subscriberId = UUID.randomUUID().toString();
        MqttClient subscriber = new MqttClient(URL, subscriberId);
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        subscriber.connect(options);
        CountDownLatch receivedSignal = new CountDownLatch(1);
        subscriber.subscribe(getTopic, (topic, msg) -> {
            byte[] payload = msg.getPayload();
            System.out.format("MQTT message: topic={%s}, payload={%s}\n",topic,new String(payload));
            receivedSignal.countDown();
        });
        receivedSignal.await(1, TimeUnit.MINUTES);
    }

    public static void MqttPublisher(String getTopic, String message) throws Exception {
        String publisherId = UUID.randomUUID().toString();
        MqttClient publisher = new MqttClient(URL, publisherId);
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        publisher.connect(options);
        Callable<Void> target = new MqttConnection(publisher);
        target.call();
    }
}