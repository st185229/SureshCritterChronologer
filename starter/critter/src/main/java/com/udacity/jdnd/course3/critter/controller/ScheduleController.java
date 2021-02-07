package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.domain.pet.Pet;
import com.udacity.jdnd.course3.critter.domain.schedule.Schedule;
import com.udacity.jdnd.course3.critter.domain.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.domain.user.Employee;
import com.udacity.jdnd.course3.critter.services.PetService;
import com.udacity.jdnd.course3.critter.services.ScheduleService;
import com.udacity.jdnd.course3.critter.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    final ScheduleService scheduleService;
    final UserService userService;
    final PetService petService;

    public ScheduleController(ScheduleService scheduleService, UserService userService, PetService petService) {
        this.scheduleService = scheduleService;
        this.userService = userService;
        this.petService = petService;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        var schedule = new Schedule();
        schedule.setDate(scheduleDTO.getDate());

        schedule.setActivities(scheduleDTO.getActivities());

        List<Long> employeeIds = scheduleDTO.getEmployeeIds();
        Set<Employee> employees = new HashSet<>();
        for (Long employeeId : employeeIds) employees.add(userService.getEmployeeById(employeeId));
        schedule.setEmployees(employees);

        List<Long> petIds = scheduleDTO.getPetIds();
        Set<Pet> pets = new HashSet<>();
        for (Long petId : petIds) pets.add(petService.getOne(petId));
        schedule.setPets(pets);

        var responseScheduleOptional = scheduleService.createSchedule(schedule);
        var scheduleResponseDTO = new ScheduleDTO();

        if(responseScheduleOptional.isPresent()){
            var responseSchedule = responseScheduleOptional.get();
            scheduleResponseDTO.setActivities(responseSchedule.getActivities());
            scheduleResponseDTO.setDate(responseSchedule.getDate());

            var employeeList = responseSchedule.getEmployees();
            employeeIds = new ArrayList<>();
            for (Employee employee : employeeList) employeeIds.add(employee.getId());
            scheduleResponseDTO.setEmployeeIds(employeeIds);

            var PetList = responseSchedule.getPets();
            petIds = new ArrayList<>();
            for (Pet pet : PetList) petIds.add(pet.getId());
            scheduleResponseDTO.setPetIds(petIds);
            return scheduleResponseDTO;
        }
       return new ScheduleDTO();
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        var scheduleList = scheduleService.getAllSchedules();
        var dtos = getScheduleDTOSFromSchedules(scheduleList);
        return dtos;

    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {

        var optionalSchedules = scheduleService.getScheduleForPet(petId);
        List<Schedule> scheduleList;
        if(optionalSchedules.isPresent()){
            scheduleList = optionalSchedules.get();
            return getScheduleDTOSFromSchedules(scheduleList);
        }
        throw new RuntimeException("No schedule exists for Pet  # "+ petId);

    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        var optionalSchedules = scheduleService.getScheduleForEmployee(employeeId);
        List<Schedule> scheduleList;
        if(optionalSchedules.isPresent()){
            scheduleList = optionalSchedules.get();
            return getScheduleDTOSFromSchedules(scheduleList);
        }
        throw new RuntimeException("No schedule exists for employee # "+ employeeId);
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        var optionalSchedules = scheduleService.getScheduleForCustomer(customerId);
        List<Schedule> scheduleList;
        if(optionalSchedules.isPresent()){
            scheduleList = optionalSchedules.get();
            return getScheduleDTOSFromSchedules(scheduleList);
        }
        throw new RuntimeException("No schedule exists for customer # "+ customerId);

    }

    private List<ScheduleDTO> getScheduleDTOSFromSchedules(List<Schedule> schedules) {
        List<ScheduleDTO> responseScheduleDTOS = new ArrayList<>();
            for(Schedule schedule :schedules){
                ScheduleDTO scheduleDTO = new ScheduleDTO();
                scheduleDTO.setActivities(schedule.getActivities());
                scheduleDTO.setDate(schedule.getDate());

                List<Long> empIds = new ArrayList<>();
                var employees = schedule.getEmployees();
                for (Employee employee: employees) empIds.add(employee.getId());
                scheduleDTO.setEmployeeIds(empIds);


                List<Long> petIds = new ArrayList<>();
                var pets = schedule.getPets();
                for (Pet pet: pets) petIds.add(pet.getId());
                scheduleDTO.setPetIds(petIds);


                responseScheduleDTOS.add(scheduleDTO);
            }
        return responseScheduleDTOS;
    }
}
