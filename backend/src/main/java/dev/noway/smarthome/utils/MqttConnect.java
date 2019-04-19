package dev.noway.smarthome.utils;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import org.springframework.context.annotation.ComponentScan;
import java.util.UUID;

@ComponentScan
public class MqttConnect {

    private IMqttClient client;
    private MqttClient mqttClient;
    private static String URL = "tcp://127.0.0.1";
//    private String URL = "tcp://192.168.1.11";


    public MqttConnect(IMqttClient client) {
        this.client = client;
    }

    // !!! A mintakódban egy másik IMqttClient példányt használtunk üzenetek fogadásához. Csak annyit tettünk, hogy egyértelműbbé tegyük, hogy melyik kliens mit csinál, de ez nem Paho-korlátozás - ha akarja, ugyanazt az ügyfelet használhatja üzenetek közzétételéhez és fogadásához
    public MqttClient getMqttClient() throws MqttException {
        if (true) {
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

    public IMqttClient getClient() {
        return client;
    }

    public void setClient(IMqttClient client) {
        this.client = client;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}