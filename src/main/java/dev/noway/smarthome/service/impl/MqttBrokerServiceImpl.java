package dev.noway.smarthome.service.impl;

import dev.noway.smarthome.model.MqttBrokerModel;
import dev.noway.smarthome.repository.MqttBrokerRepository;
import dev.noway.smarthome.service.MqttBrokerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
public class MqttBrokerServiceImpl implements MqttBrokerService {

    @Autowired
    private MqttBrokerRepository brokerRepository;

    @Override
    public MqttBrokerModel save(MqttBrokerModel broker) {
        return brokerRepository.save(broker);
    }

    @Override
    public Boolean delete(int id) {
        if (brokerRepository.existsById(id)) {
            brokerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public MqttBrokerModel update(MqttBrokerModel broker) {
        return brokerRepository.save(broker);
    }

    @Override
    @Transactional(readOnly = true)
    public MqttBrokerModel findById(int id) {
        return brokerRepository.findById(id).get();
    }

    @Override
    @Transactional(readOnly = true)
    public MqttBrokerModel findNetwork(String network) {
        return brokerRepository.findByNetwork(network);
    }

    @Override
    @Transactional(readOnly = true)
    public MqttBrokerModel findHardwer(String hardwer) {
        return brokerRepository.findByHardwer(hardwer);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<MqttBrokerModel> findAll() {
        Iterable<MqttBrokerModel> itr = brokerRepository.findAll();
        return (Collection<MqttBrokerModel>) itr;
    }
}
