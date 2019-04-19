package dev.noway.smarthome.repository;

import dev.noway.smarthome.model.UserModel;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserModel, Integer> {

    UserModel findByEmail(String email);
}