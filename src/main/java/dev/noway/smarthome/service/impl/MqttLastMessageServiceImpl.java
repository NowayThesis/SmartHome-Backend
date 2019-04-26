package dev.noway.smarthome.service.impl;

import dev.noway.smarthome.model.MqttLastMessageModel;
import dev.noway.smarthome.repository.MqttLastMessageRepository;
import dev.noway.smarthome.service.MqttLastMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
public class MqttLastMessageServiceImpl implements MqttLastMessageService {

    @Autowired
    private MqttLastMessageRepository mqttLastMessageRepository;

    @Override
    public MqttLastMessageModel save(MqttLastMessageModel catalog) {
        return mqttLastMessageRepository.save(catalog);
    }

    @Override
    public Boolean delete(int id) {
        if (mqttLastMessageRepository.existsById(id)) {
            mqttLastMessageRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public void update(MqttLastMessageModel catalog) {
        delete(findTopic(catalog.getTopic()).getId());
        mqttLastMessageRepository.save(catalog);
    }

    @Override
    @Transactional(readOnly = true)
    public MqttLastMessageModel findById(int id) {
        return mqttLastMessageRepository.findById(id).get();
    }

    @Override
    @Transactional(readOnly = true)
    public MqttLastMessageModel findTopic(String topic_text) {
        return mqttLastMessageRepository.findTopicFilter(topic_text);
    }

    @Override
    @Transactional(readOnly = true)
    public MqttLastMessageModel findMessage(String message) {
        return mqttLastMessageRepository.findMessageFilter(message);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<MqttLastMessageModel> findAll() {
        Iterable<MqttLastMessageModel> itr = mqttLastMessageRepository.findAll();
        return (Collection<MqttLastMessageModel>) itr;
    }
}
