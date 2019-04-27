package dev.noway.smarthome.repository;

import dev.noway.smarthome.model.MqttLastMessageModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Transactional(propagation = Propagation.MANDATORY)
public interface MqttLastMessageRepository  extends CrudRepository<MqttLastMessageModel, Integer> {

    MqttLastMessageModel findByTopic(String topic);

    MqttLastMessageModel findByMessage(String message);

}
