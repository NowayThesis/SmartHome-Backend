package dev.noway.smarthome.service;

import dev.noway.smarthome.model.MqttBrokerModel;
import java.util.Collection;

public interface MqttBrokerService {

    MqttBrokerModel save(MqttBrokerModel broker);

    Boolean delete(int id);

    MqttBrokerModel update(MqttBrokerModel broker);

    MqttBrokerModel findById(int id);

    MqttBrokerModel findNetwork(String network);

    MqttBrokerModel findHardwer(String hardwer);

    Collection<MqttBrokerModel> findAll();
}
