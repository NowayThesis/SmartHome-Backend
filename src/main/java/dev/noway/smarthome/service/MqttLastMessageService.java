package dev.noway.smarthome.service;

import dev.noway.smarthome.model.MqttLastMessageModel;

import java.util.Collection;

public interface MqttLastMessageService {

    MqttLastMessageModel save(MqttLastMessageModel catalog);

    Boolean delete(int id);

    void update(MqttLastMessageModel catalog);

    MqttLastMessageModel findById(int id);

    MqttLastMessageModel findTopic(String topic_text);

    MqttLastMessageModel findMessage(String message);

    Collection<MqttLastMessageModel> findAll();
}
