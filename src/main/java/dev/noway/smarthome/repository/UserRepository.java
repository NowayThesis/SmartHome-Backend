package dev.noway.smarthome.repository;

import dev.noway.smarthome.model.UserModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Transactional(propagation = Propagation.MANDATORY)
public interface UserRepository extends CrudRepository<UserModel, Integer> {

    UserModel findByEmail(String email);

    Collection<UserModel> findByRole(int role);
}