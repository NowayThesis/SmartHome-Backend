package dev.noway.smarthome.service.impl.converter.appointment;

import dev.noway.smarthome.persistence.entity.AppointmentEntity;
import dev.noway.smarthome.service.api.domain.Appointment;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AppointmentEntityToAppointmentConverter implements Converter<AppointmentEntity, Appointment> {


    @Override
    public Appointment convert(AppointmentEntity appointmentEntity) {
        return Appointment.builder()
                .id(appointmentEntity.getId())
                .address(appointmentEntity.getAddress())
                .location(appointmentEntity.getLocation())
                .date(appointmentEntity.getDate())
                .time(appointmentEntity.getTime())
                .description(appointmentEntity.getDescription())
                .prio(appointmentEntity.getPrio())
                .build();
    }
}
