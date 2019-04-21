package dev.noway.smarthome.utils;

import dev.noway.smarthome.service.LocalMachineInformationService;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@Service
@ComponentScan
public class MqttConnect {

    @Autowired
    private LocalMachineInformationService localMachineInformationService;
    @Autowired
    private MqttBrokerActual mqttBrokerActual;

    private MqttClient mqttClient;
    private String mqttBrokerUrl = "";

    // !!! A mintakódban egy másik IMqttClient példányt használtunk üzenetek fogadásához.
    // Csak annyit tettünk, hogy egyértelműbbé tegyük, hogy melyik kliens mit csinál,
    // de ez nem Paho-korlátozás - ha akarja, ugyanazt az ügyfelet használhatja üzenetek
    // közzétételéhez és fogadásához
    protected MqttClient getMqttClient(){
        if (mqttClient == null) {
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
                mqttClient = new MqttClient(getMqttBrokerUrl() + ":1883", localMachineInformationService.getClientId(), dataStore);
                // Connect to Broker
                mqttClient.connect(options);
                mqttBrokerActual.setactualMqttBroker(localMachineInformationService);
            } catch (Exception e) {
                System.out.println("Local MQTT client connect error!");
            }
        }
        return mqttClient;
    }

    public String getMqttBrokerUrl() {
        if (mqttBrokerUrl==""){
//            mqttBrokerUrl = "tcp://127.0.0.1";
            mqttBrokerUrl = "tcp://192.168.1.15";
        }
        return mqttBrokerUrl;
    }
}