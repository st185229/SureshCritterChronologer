package com.udacity.jdnd.course3.critter.domain.user;

import com.udacity.jdnd.course3.critter.domain.schedule.Schedule;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="EMPLOYEE")
public class Employee extends User {
    public Employee() {
    }

    @ElementCollection(targetClass = EmployeeSkill.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name="EMPLOYEE_SKILLS")
    @Column(name="EMPLOYEE_SKILL_TYPE")
    private Set<EmployeeSkill> skills;

    @ElementCollection(targetClass = DayOfWeek.class,fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name="DAYS_OF_WEEK")
    @Column(name="AVAILABLE_DAYS")
    private Set<DayOfWeek> daysAvailable;

    public Employee(Set<EmployeeSkill> skills, Set<DayOfWeek> daysAvailable) {
        this.skills = skills;
        this.daysAvailable = daysAvailable;
    }

    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JoinColumn(name="USER_ID", nullable=false)
    List<Schedule> scheduleList;


    /*

     @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JoinColumn(name="SCHEDULE_ID", nullable=false)
    List<Employee> employees ;

    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JoinColumn(name="SCHEDULE_ID", nullable=false)
    List<Pet> pets ;
     */


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

}
