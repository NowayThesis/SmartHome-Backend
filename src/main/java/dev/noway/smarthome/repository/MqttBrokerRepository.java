package dev.noway.smarthome.repository;

import dev.noway.smarthome.model.MqttBrokerModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MqttBrokerRepository extends CrudRepository<MqttBrokerModel, Integer> {

    MqttBrokerModel findByNetwork(String network);

    MqttBrokerModel findByHardwer(String hardwer);
}