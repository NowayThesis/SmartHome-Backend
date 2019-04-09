package dev.noway.smarthome.service.impl.converter.appointment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppointmentToAppointmentEntityConverter implements Converter<Appointment,AppointmentEntity> {

    @Override
    public AppointmentEntity convert(Appointment appointment) {
        return AppointmentEntity.builder()
                .id(appointment.getId())
                .address(appointment.getAddress())
                .location(appointment.getLocation())
                .date(appointment.getDate())
                .time(appointment.getTime())
                .description(appointment.getDescription())
                .prio(appointment.getPrio())
                .build();
    }
}
