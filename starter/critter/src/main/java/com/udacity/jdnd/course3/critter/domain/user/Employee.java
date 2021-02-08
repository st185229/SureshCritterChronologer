package com.udacity.jdnd.course3.critter.domain.user;

import com.udacity.jdnd.course3.critter.domain.schedule.Schedule;

import javax.persistence.*;
import java.sql.Date;
import java.time.DayOfWeek;
import java.util.Set;

@Entity
@Table(name = "EMPLOYEE")
public class Employee extends User {
    @ElementCollection(targetClass = EmployeeSkill.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "EMPLOYEE_SKILLS")
    @Column(name = "EMPLOYEE_SKILL_TYPE")
    private Set<EmployeeSkill> skills;
    @ElementCollection(targetClass = DayOfWeek.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "DAYS_OF_WEEK")
    @Column(name = "AVAILABLE_DAYS")
    private Set<DayOfWeek> daysAvailable;
    @ManyToMany(mappedBy = "employees", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<Schedule> employeesScheduleSet;

    public Employee() {
    }

    public Employee(Long id) {
        super.setId(id);
        this.setId(id);
    }

    public Employee(Set<EmployeeSkill> skills, Set<DayOfWeek> daysAvailable) {
        this.skills = skills;
        this.daysAvailable = daysAvailable;
    }

    public Set<Schedule> getScheduleSet() {
        return employeesScheduleSet;
    }

    public void setScheduleSet(Set<Schedule> scheduleSet) {
        this.employeesScheduleSet = scheduleSet;
    }

    public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }

    public Set<DayOfWeek> getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }

    @Override
    public Date getCreatedOn() {
        return super.getCreatedOn();
    }

    @Override
    public void setCreatedOn(Date createdOn) {
        super.setCreatedOn(createdOn);
    }

    @Override
    public long getId() {
        return super.getId();
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }
}
