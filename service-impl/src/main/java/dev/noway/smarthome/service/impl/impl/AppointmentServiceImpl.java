package dev.noway.smarthome.service.impl.impl;

import dev.noway.smarthome.commons.pojo.request.AppointmentRequest;
import dev.noway.smarthome.persistence.entity.AppointmentEntity;
import dev.noway.smarthome.persistence.repository.AppointmentRepository;
import dev.noway.smarthome.service.api.domain.Appointment;
import dev.noway.smarthome.service.api.service.AppointmentService;
import dev.noway.smarthome.service.impl.converter.appointment.AppointmentEntityListToAppointmentListConverter;
import dev.noway.smarthome.service.impl.converter.appointment.AppointmentEntityToAppointmentConverter;
import dev.noway.smarthome.service.impl.converter.appointment.AppointmentRequestToAppointmentConverter;
import dev.noway.smarthome.service.impl.converter.appointment.AppointmentToAppointmentEntityConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentRequestToAppointmentConverter fromRequest;
    private final AppointmentEntityToAppointmentConverter toDomain;
    private final AppointmentToAppointmentEntityConverter toEntity;
    private final AppointmentEntityListToAppointmentListConverter toDomainList;

    @Override
    public void saveAppointment(AppointmentRequest appointmentRequest) {
        Appointment appointment = fromRequest.convert(appointmentRequest);
        assert appointment != null;
        AppointmentEntity appointmentEntity = toEntity.convert(appointment);
        assert appointmentEntity != null;
        appointmentRepository.save(appointmentEntity);
    }

    @Override
    public Appointment findAppointmentById(Long id) {
        AppointmentEntity appointmentEntity = appointmentRepository.getOne(id);
        return toDomain.convert(appointmentEntity);
    }

    @Override
    public List<Appointment> findAllAppointment() {
        List<AppointmentEntity> appointmentEntities = appointmentRepository.findAll();
        return toDomainList.convert(appointmentEntities);
    }
}
