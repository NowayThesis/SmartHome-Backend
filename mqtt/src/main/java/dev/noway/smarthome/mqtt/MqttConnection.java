package dev.noway.smarthome.mqtt;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

import dev.noway.smarthome.mqtt.model.MqttModel;
import org.eclipse.paho.client.mqttv3.*;

public class MqttConnection implements Callable<Void> {

    private static List<MqttModel> mqttmodels = new ArrayList<MqttModel>();

    private static final String URL = "tcp://192.168.1.11:1883";
    private static String TOPIC = "noway/home/relay";
    private static String myMessage = "Robi teszt";
    private IMqttClient client;
    MqttMessage mqttMessage;

    public static List<MqttModel> getMqttmodels() {
        return mqttmodels;
    }

    public static void setMqttmodels(MqttModel mqttmodel) {
        MqttConnection.mqttmodels.add(mqttmodel);
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
            dbMqttModel(topic, payload);
            receivedSignal.countDown();
        });
        receivedSignal.await(1, TimeUnit.MINUTES);
    }

    private static void dbMqttModel(String topic, byte[] payload) {
        setMqttmodels(new MqttModel(topic, new String(payload)));
    }

    public static void MqttPublisher(String getTopic, String message) throws Exception {
        MqttClient mqttclient = getMqttClient();

        Callable<Void> target = new MqttConnection(mqttclient);
        target.call();
    }

    // !!! A mintakódban egy másik IMqttClient példányt használtunk üzenetek fogadásához. Csak annyit tettünk, hogy egyértelműbbé tegyük, hogy melyik kliens mit csinál, de ez nem Paho-korlátozás - ha akarja, ugyanazt az ügyfelet használhatja üzenetek közzétételéhez és fogadásához
    private static MqttClient getMqttClient() throws MqttException {
        // Ügyfél azonosító, véletlenszerű UUID, minden futáskor új kliensazonosító jön létre
        String clientId = UUID.randomUUID().toString();
        // Legegyszerűbb kivitelező, MQTT bróker végpont cím + ügyfél azonosító
        MqttClient mqttclient = new MqttClient(URL, clientId);
        // Kapcsolódás a kiszolgálóhoz, opciókat  melyek információk továbbítására használhatjuk, mint a biztonsági hitelesítő adatok, a munkamenet-helyreállítási mód, az újbóli csatlakoztatási mód
        MqttConnectOptions options = new MqttConnectOptions();
        // A könyvtár automatikusan megpróbálja újra csatlakozni a kiszolgálóhoz hálózati hiba esetén
        options.setAutomaticReconnect(true);
        // Elveti az el nem küldött üzeneteket egy korábbi futásból
        options.setCleanSession(true);
        // A kapcsolat időkorlátja 10 másodperc
        options.setConnectionTimeout(10);
        mqttclient.connect(options);
        return mqttclient;
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
        msg.setQos(2);
        msg.setRetained(true);
        client.publish(TOPIC,msg);
        return null;
    }
}