package dev.noway.smarthome.mqtt;

import dev.noway.smarthome.model.MqttCatalogModel;
import dev.noway.smarthome.service.MqttCatalogService;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@ComponentScan
public class MqttConnection implements Callable<Void> {

    @Autowired
    private static MqttCatalogService mqttCatalogService;
    private static final String URL = "tcp://192.168.1.11";
    private static String myTopic = "noway/home/relay";
    private static String myMessage = "Robi teszt";
    private IMqttClient client;
    private static MqttClient mqttClient;

    public static String getMyTopic() {
        return myTopic;
    }

    public static void setMyTopic(String myTopic) {
        MqttConnection.myTopic = myTopic;
    }

    public static void setMyMessage(String myMessage) {
        MqttConnection.myMessage = myMessage;
    }

    private MqttConnection(IMqttClient client) {
        this.client = client;
    }

    public static void MqttSubscriber(String getTopic) throws Exception {
        MqttClient mqttclient = getMqttClient();

        // visszahívás és a fő végrehajtási szál közötti szinkronizálási mechanizmus, minden alkalommal új üzenet érkezésekor csökken
        CountDownLatch receivedSignal = new CountDownLatch(10);
        // második érvként egy IMqttMessageListener példányt vesz fel
        // egy egyszerű lambda függvényt használunk, amely feldolgozza a hasznos terhelést és csökkenti a számlálót. Ha a megadott időablakban nem érkezik elég üzenet (1 perc), a  várakozási () módszer kivételeket dob.
        mqttclient.subscribe(getTopic, (topic, msg) -> {
            byte[] payload = msg.getPayload();
            System.out.format("MQTT: \ttopic={%s},\n\t\tpayload={%s}\n",topic,new String(payload));
            receivedSignal.countDown();
        });
        receivedSignal.await(1, TimeUnit.MINUTES);
    }

    public static void MqttPublisher(String getTopic, String getMessage) throws Exception {
        setMyTopic(getTopic);
        setMyMessage(getMessage);
        MqttCatalogModel mqttCatalogeModel = new MqttCatalogModel(getTopic, getMessage);
        try {
            mqttCatalogeModel.setAddDate(LocalDateTime.now());
            mqttCatalogService.save(mqttCatalogeModel);
        } catch (Exception e) {
            System.out.println("Hiba!");
        }
        MqttClient mqttclient = getMqttClient();
        Callable<Void> target = new MqttConnection(mqttclient);
        target.call();
    }

    // !!! A mintakódban egy másik IMqttClient példányt használtunk üzenetek fogadásához. Csak annyit tettünk, hogy egyértelműbbé tegyük, hogy melyik kliens mit csinál, de ez nem Paho-korlátozás - ha akarja, ugyanazt az ügyfelet használhatja üzenetek közzétételéhez és fogadásához
    private static MqttClient getMqttClient() throws MqttException {
        if (mqttClient == null) {
            // Ügyfél azonosító, véletlenszerű UUID, minden futáskor új kliensazonosító jön létre
            String clientId = UUID.randomUUID().toString();
            // Kapcsolódás a kiszolgálóhoz, opciókat  melyek információk továbbítására használhatjuk, mint a biztonsági hitelesítő adatok, a munkamenet-helyreállítási mód, az újbóli csatlakoztatási mód
            MqttConnectOptions options = new MqttConnectOptions();
            // A könyvtár automatikusan megpróbálja újra csatlakozni a kiszolgálóhoz hálózati hiba esetén
            options.setAutomaticReconnect(true);
            // Elveti az el nem küldött üzeneteket egy korábbi futásból
            options.setCleanSession(true);
            // A kapcsolat időkorlátja 10 másodperc
            options.setConnectionTimeout(3600);
            options.setKeepAliveInterval(3600);
//        options.setUserName("");
//        options.setPassword("");
            String tmpDir = System.getProperty("user.dir") + "/backend/target"; //System.getProperty("java.io.tmpdir");
            MqttDefaultFilePersistence dataStore = new MqttDefaultFilePersistence(tmpDir);
            mqttClient = new MqttClient(URL + ":1883", "3597829e-c999-4817-bb78-07c1a7ee7762-tcp1921681111883", dataStore);
            // Connect to Broker
            mqttClient.connect(options);
        }
        return mqttClient;
    }

    @Override
    public Void call() throws Exception {
        if ( !client.isConnected()) {
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
        client.publish(myTopic,msg);
        return null;
    }
}