package dev.noway.smarthome.service.impl.converter.user;

import dev.noway.smarthome.persistence.entity.UserEntity;
import dev.noway.smarthome.service.api.domain.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToUserEntityConverter implements Converter<User, UserEntity> {

    @Override
    public UserEntity convert(User user){
        return UserEntity.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .sex(user.getSex())
                .userRole(user.getUserRole())
                .build();
    }

}
