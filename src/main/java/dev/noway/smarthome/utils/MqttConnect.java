package dev.noway.smarthome.utils;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@ComponentScan
public class MqttConnect {

    private MqttClient mqttClient;
    private String mqttBrokerUrl = "";

    // !!! A mintakódban egy másik IMqttClient példányt használtunk üzenetek fogadásához.
    // Csak annyit tettünk, hogy egyértelműbbé tegyük, hogy melyik kliens mit csinál,
    // de ez nem Paho-korlátozás - ha akarja, ugyanazt az ügyfelet használhatja üzenetek
    // közzétételéhez és fogadásához
    protected MqttClient getMqttClient(){
        if (mqttClient == null) {
            String clientId = UUID.randomUUID().toString();
            // Kapcsolódás a kiszolgálóhoz, opciókat  melyek információk továbbítására
            // használhatjuk, mint a biztonsági hitelesítő adatok, a munkamenet-helyreállítási
            // mód, az újbóli csatlakoztatási mód
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
            String tmpDir = System.getProperty("user.dir") + "/target"; //System.getProperty("java.io.tmpdir");
            MqttDefaultFilePersistence dataStore = new MqttDefaultFilePersistence(tmpDir);
            try {
                mqttClient = new MqttClient("tcp://192.168.1.15:1883", clientId, dataStore);
                // Connect to Broker
                mqttClient.connect(options);
            } catch (Exception e) {
                System.out.println("Local MQTT client connect error!");
            }
        }
        return mqttClient;
    }
}