package dev.noway.smarthome.service.impl;

import dev.noway.smarthome.model.UserModel;
import dev.noway.smarthome.repository.UserRepository;
import dev.noway.smarthome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserModel save(UserModel user) {
        return userRepository.save(user);
    }

    @Override
    public Boolean delete(int id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public UserModel update(UserModel user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserModel findById(int id) {
        return userRepository.findById(id).get();
    }

    @Override
    @Transactional(readOnly = true)
    public UserModel findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<UserModel> findAll() {
        Iterable<UserModel> itr = userRepository.findAll();
        return (Collection<UserModel>) itr;
    }
}
