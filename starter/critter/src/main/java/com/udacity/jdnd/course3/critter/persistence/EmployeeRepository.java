package com.udacity.jdnd.course3.critter.persistence;

import com.udacity.jdnd.course3.critter.domain.pet.Pet;
import com.udacity.jdnd.course3.critter.domain.schedule.Schedule;
import com.udacity.jdnd.course3.critter.domain.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.domain.user.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {


    @Query("SELECT employee.employeesScheduleSet from Employee employee where employee.id = :#{#employeeId}")
    List<Schedule> getScheduleForEmployee(@Param("employeeId")  long employeeId);
}




