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
public class UserListToUserEntityConverter implements Converter<List<User>, List<UserEntity>> {

    private final UserToUserEntityConverter toUserEntityConverter;

    @Override
    public List<UserEntity> convert(List<User> users) {
        return users.stream().map(toUserEntityConverter::convert).collect(Collectors.toList());
    }
}
