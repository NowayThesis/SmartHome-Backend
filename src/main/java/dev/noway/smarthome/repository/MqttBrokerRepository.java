package dev.noway.smarthome.repository;

import dev.noway.smarthome.model.MqttBrokerModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.MANDATORY)
public interface MqttBrokerRepository extends CrudRepository<MqttBrokerModel, Integer> {

    MqttBrokerModel findByNetwork(String network);

    MqttBrokerModel findByHardwer(String hardwer);
}