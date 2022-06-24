package com.dev.covid.service;


import com.dev.covid.DTO.InfectionTrackingDTO;
import com.dev.covid.model.InfectionTracking;
import com.dev.covid.model.Patient;
import com.dev.covid.repository.InfectionTrackingRepository;
import com.dev.covid.repository.PatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Slf4j
@Service
public class InfectionTrackingService {

    @Autowired
    private InfectionTrackingRepository repository;

    @Autowired
    private PatientRepository patientRepository;

    public List<InfectionTracking> findAll() {
        return repository.findAll();
    }

    public InfectionTracking save(InfectionTrackingDTO infectionTrackingDTO) {
        try {
            Patient patient = patientRepository.findById(infectionTrackingDTO.getPatientPeopleId()).get();

            InfectionTracking infectionTracking = InfectionTracking
                    .builder()
                    .infectionTrackingArea(infectionTrackingDTO.getInfectionTrackingArea())
                    .infectionTrackingCause(infectionTrackingDTO.getInfectionTrackingCause())
                    .infectionTrackingDate(infectionTrackingDTO.getInfectionTrackingDate())
                    .infectionTrackingId(infectionTrackingDTO.getInfectionTrackingId())
                    .infectionTrackingName(infectionTrackingDTO.getInfectionTrackingName())
                    .patient(patient)
                    .build();
            return repository.save(infectionTracking);


        } catch (Exception e){
            log.warn("Not found Patient ID {}", infectionTrackingDTO.getPatientPeopleId());
            throw new RuntimeException(e.getMessage());
        }

    }

    public List<InfectionTracking> update(InfectionTracking infectionTracking) {
        final Optional<InfectionTracking> findInfectionTracking = repository.findById(infectionTracking.getInfectionTrackingId());

        findInfectionTracking.ifPresent(newInfectionTracking -> {
            newInfectionTracking.setInfectionTrackingName(infectionTracking.getInfectionTrackingName());
            newInfectionTracking.setInfectionTrackingArea(infectionTracking.getInfectionTrackingArea());
            newInfectionTracking.setInfectionTrackingDate(infectionTracking.getInfectionTrackingDate());
            newInfectionTracking.setInfectionTrackingCause(infectionTracking.getInfectionTrackingCause());

            repository.save(newInfectionTracking);
        });
        return repository.findAll();
    }

    public List<InfectionTracking> delete(Long id) {
        final Optional<InfectionTracking> findInfectionTracking = repository.findById(id);

        findInfectionTracking.ifPresent(infectionTracking -> {
            // programList : 삭제하고자 하는 엔터티
            repository.delete(infectionTracking);
        });

        return repository.findAll();
    }

}
