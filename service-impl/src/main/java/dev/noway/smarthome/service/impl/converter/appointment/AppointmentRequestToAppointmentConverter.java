package dev.noway.smarthome.service.impl.converter.appointment;

import dev.noway.smarthome.commons.pojo.request.AppointmentRequest;
import dev.noway.smarthome.service.api.domain.Appointment;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AppointmentRequestToAppointmentConverter implements Converter<AppointmentRequest,Appointment> {

    @Override
    public Appointment convert(AppointmentRequest appointmentRequest) {
        return Appointment.builder()
                .address(appointmentRequest.getAddress())
                .location(appointmentRequest.getLocation())
                .date(appointmentRequest.getDate())
                .time(appointmentRequest.getTime())
                .description(appointmentRequest.getDescription())
                .prio(appointmentRequest.getPrio())
                .build();
    }
}
