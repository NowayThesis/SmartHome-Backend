package dev.noway.smarthome.service;

import dev.noway.smarthome.model.MqttCatalogModel;

import java.util.Collection;
import java.util.List;

public interface MqttCatalogService {

    MqttCatalogModel save(MqttCatalogModel catalog);

    MqttCatalogModel findById(int id);

    List<MqttCatalogModel> findTopic(String topic_text);

    List<MqttCatalogModel> findMessage(String message);

    Collection<MqttCatalogModel> findAll();
}
