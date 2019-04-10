package dev.noway.smarthome.backend.mqtt;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.*;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttConnect implements Callable<Void> {

    public static String getTopic() {
        return topic;
    }

    public static void setTopic(String topic) {
        MqttConnect.topic = topic;
    }

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        MqttConnect.message = message;
    }

    private static String topic = "noway/home/relay";
    private static String message = "Robi teszt";

    private IMqttClient client;
    private Random rnd = new Random();

    private MqttConnect(IMqttClient client) {
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
        client.publish(topic,msg);
        return null;
    }

    private MqttMessage post() {
        byte[] payload = String.format(this.message).getBytes();
        MqttMessage msg = new MqttMessage(payload);
        return msg;
    }

    public static void publishMqttMessage(String serverURL, String topic, String message) throws Exception {
        setTopic(topic);
        setMessage(message);
        String publisherId = UUID.randomUUID().toString();
        MqttClient publisher = new MqttClient(serverURL, publisherId);
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        publisher.connect(options);
        Callable<Void> target = new MqttConnect(publisher);
        target.call();

    }

    public static void subscribMqttMessage(String serverURL, String topic1) throws Exception {
        String subscriberId = UUID.randomUUID().toString();
        MqttClient subscriber = new MqttClient(serverURL, subscriberId);
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        subscriber.connect(options);
        CountDownLatch receivedSignal = new CountDownLatch(1);
        subscriber.subscribe(MqttConnect.topic, (topic, msg) -> {
            byte[] payload = msg.getPayload();
            System.out.format("MQTT message: topic={%s}, payload={%s}\n",topic,new String(payload));
            receivedSignal.countDown();
        });
        receivedSignal.await(1, TimeUnit.MINUTES);
    }

    public void whenSendSingleMessage_thenSuccess() throws Exception {
        String publisherId = UUID.randomUUID().toString();
        MqttClient publisher = new MqttClient("tcp://192.168.1.11:1883",publisherId);
        String subscriberId = UUID.randomUUID().toString();
        MqttClient subscriber = new MqttClient("tcp://192.168.1.11:1883",subscriberId);
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        subscriber.connect(options);
        publisher.connect(options);
        CountDownLatch receivedSignal = new CountDownLatch(1);
        subscriber.subscribe(MqttConnect.topic, (topic, msg) -> {
            byte[] payload = msg.getPayload();
            System.out.printf("[I46] Message received: topic={}, payload={}", topic, new String(payload));
            receivedSignal.countDown();
        });
        Callable<Void> target = new MqttConnect(publisher);
        target.call();
        receivedSignal.await(1, TimeUnit.MINUTES);
    }

    public void whenSendMultipleMessages_thenSuccess() throws Exception {
        String publisherId = UUID.randomUUID().toString();
        MqttClient publisher = new MqttClient("tcp://192.168.1.11:1883",publisherId);
        String subscriberId = UUID.randomUUID().toString();
        MqttClient subscriber = new MqttClient("tcp://192.168.1.11:1883",subscriberId);
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        publisher.connect(options);
        subscriber.connect(options);
        CountDownLatch receivedSignal = new CountDownLatch(10);
        subscriber.subscribe(MqttConnect.topic, (topic, msg) -> {
            byte[] payload = msg.getPayload();
            System.out.printf("[I82] Message received: topic={}, payload={}", topic, new String(payload));
            receivedSignal.countDown();
        });
        Callable<Void> target = new MqttConnect(publisher);
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
              try {
                  target.call();
              }
              catch(Exception ex) {
                  throw new RuntimeException(ex);
              }
          }, 1, 1, TimeUnit.SECONDS);
        receivedSignal.await(1, TimeUnit.MINUTES);
        executor.shutdown();
    }
}