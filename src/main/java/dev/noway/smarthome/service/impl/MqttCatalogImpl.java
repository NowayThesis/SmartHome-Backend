package dev.noway.smarthome.service.impl;

import dev.noway.smarthome.model.MqttCatalogModel;
import dev.noway.smarthome.repository.MqttCatalogRepository;
import dev.noway.smarthome.service.MqttCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class MqttCatalogImpl implements MqttCatalogService {

    @Autowired
    private MqttCatalogRepository mqttCatalogRepository;

    @Override
    public MqttCatalogModel save(MqttCatalogModel catalog) {
        return mqttCatalogRepository.save(catalog);
    }

    @Override
    @Transactional(readOnly = true)
    public MqttCatalogModel findById(int id) {
        return mqttCatalogRepository.findById(id).get();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MqttCatalogModel> findTopic(String topic_text) {
        return mqttCatalogRepository.findTopicFilter(topic_text);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MqttCatalogModel> findMessage(String message) {
        return mqttCatalogRepository.findMessageFilter(message);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<MqttCatalogModel> findAll() {
        Iterable<MqttCatalogModel> itr = mqttCatalogRepository.findAll();
        return (Collection<MqttCatalogModel>) itr;
    }
}
