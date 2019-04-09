package dev.noway.smarthome.service.impl.impl;

import dev.noway.smarthome.commons.pojo.request.RegistrationRequest;
import dev.noway.smarthome.service.api.service.RegistrationService;
import dev.noway.smarthome.service.api.service.UserService;
import dev.noway.smarthome.service.impl.converter.user.RegistrationRequestToUserConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final UserService userService;

    private final RegistrationRequestToUserConverter converter;

    @Override
    public void register(RegistrationRequest registrationRequest) {
        userService.addUser(converter.convert(registrationRequest));
    }
}
