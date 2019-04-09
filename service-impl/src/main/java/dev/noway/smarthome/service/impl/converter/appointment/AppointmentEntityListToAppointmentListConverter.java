package dev.noway.smarthome.service.impl.converter.appointment;

import dev.noway.smarthome.persistence.entity.AppointmentEntity;
import dev.noway.smarthome.service.api.domain.Appointment;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AppointmentEntityListToAppointmentListConverter implements Converter<List<AppointmentEntity>,List<Appointment>> {

    private final AppointmentEntityToAppointmentConverter toAppointmentConverter;

    @Override
    public List<Appointment> convert(List<AppointmentEntity> appointmentEntities) {
        return appointmentEntities.stream().map(toAppointmentConverter::convert).collect(Collectors.toList());
    }
}
