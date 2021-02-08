package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.domain.schedule.Schedule;
import com.udacity.jdnd.course3.critter.persistence.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;


    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;

    }

    public Optional<Schedule> createSchedule(Schedule schedule) {

        var result = scheduleRepository.save(schedule);
        return schedule != null ? Optional.of(schedule) : Optional.empty();
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public Optional<List<Schedule>> getScheduleForPet(long petId) {

        return scheduleRepository.getScheduleForPet(petId);
    }


    public Optional<List<Schedule>> getScheduleForEmployee(long employeeId) {
        return scheduleRepository.getScheduleForEmployee(employeeId);
    }


    public Optional<List<Schedule>> getScheduleForCustomer(long customerId) {
        return scheduleRepository.getScheduleForCustomer(customerId);


    }
}
