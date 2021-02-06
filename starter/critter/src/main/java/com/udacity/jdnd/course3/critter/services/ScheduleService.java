package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.domain.schedule.Schedule;
import com.udacity.jdnd.course3.critter.domain.user.Customer;
import com.udacity.jdnd.course3.critter.persistence.EmployeeRepository;
import com.udacity.jdnd.course3.critter.persistence.PetRepository;
import com.udacity.jdnd.course3.critter.persistence.ScheduleRepository;
import com.udacity.jdnd.course3.critter.persistence.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final PetRepository petRepository;
    private final EmployeeRepository employeeRepository;
    public final UserRepository userRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, PetRepository petRepository, EmployeeRepository employeeRepository, UserRepository userRepository) {
        this.scheduleRepository = scheduleRepository;
        this.petRepository = petRepository;
        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;
    }

    public Schedule createSchedule(Schedule schedule) {

        var result = scheduleRepository.saveAndFlush(schedule);
        return result;
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }
    public List<Schedule> getScheduleForPet( long petId) {

        return petRepository.getScheduleForPet(petId);
    }


    public List<Schedule> getScheduleForEmployee(long employeeId) {
       return employeeRepository.getScheduleForEmployee(employeeId);
    }


    public List<Schedule> getScheduleForCustomer( long customerId) {
      return null;


    }
}
