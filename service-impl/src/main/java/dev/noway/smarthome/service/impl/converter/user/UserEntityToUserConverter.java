package dev.noway.smarthome.service.impl.converter.user;

import dev.noway.smarthome.persistence.entity.UserEntity;
import dev.noway.smarthome.service.api.domain.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserEntityToUserConverter implements Converter<UserEntity, User> {

    @Override
    public User convert(UserEntity source){
        return User.builder()
                .id(source.getId())
                .username(source.getUsername())
                .password(source.getPassword())
                .email(source.getEmail())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .userRole(source.getUserRole())
                .sex(source.getSex())
                .build();
    }
}
