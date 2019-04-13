package dev.noway.smarthome.service;

import dev.noway.smarthome.model.UserModel;
import java.util.Collection;

public interface UserService {

    UserModel save(UserModel user);

    Boolean delete(int id);

    UserModel update(UserModel user);

    UserModel findById(int id);

    UserModel findByEmail(String email);

    Collection<UserModel> findAll();
}
