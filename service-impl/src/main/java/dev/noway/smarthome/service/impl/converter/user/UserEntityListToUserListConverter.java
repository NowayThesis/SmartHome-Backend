package dev.noway.smarthome.service.impl.converter.user;

import dev.noway.smarthome.persistence.entity.UserEntity;
import dev.noway.smarthome.service.api.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserEntityListToUserListConverter implements Converter<List<UserEntity>, List<User>> {

    private final UserEntityToUserConverter toDomain;

    @Override
    public List<User> convert(List<UserEntity> userEntities) {
        return userEntities.stream().map(toDomain::convert).collect(Collectors.toList());
    }
}
