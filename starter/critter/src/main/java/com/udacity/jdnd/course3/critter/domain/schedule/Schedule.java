package com.udacity.jdnd.course3.critter.domain.schedule;

import com.udacity.jdnd.course3.critter.domain.pet.Pet;
import com.udacity.jdnd.course3.critter.domain.user.Employee;
import com.udacity.jdnd.course3.critter.domain.user.EmployeeSkill;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "SCHEDULE")
public class Schedule {

    @Id
    @GeneratedValue
    @Column(name = "SCHEDULE_ID")
    private Long id;
    @Column
    private LocalDate date;
    @ElementCollection(targetClass = EmployeeSkill.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "SCHEDULE_ACTIVITIES")
    @Column(name = "SCHEDULE_ACTIVITY_TYPE")
    private Set<EmployeeSkill> activities = new HashSet<>();
    /*@ManyToMany(mappedBy = "petsScheduleSet",cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)*/
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "PET_SCHEDULE",
            joinColumns = @JoinColumn(name = "SCHEDULE_ID"),
            inverseJoinColumns = @JoinColumn(name = "PET_ID"))
    private Set<Pet> pets = new HashSet<>();
    /*@ManyToMany(mappedBy = "employeesScheduleSet",cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)*/
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "EMPLOYEE_SCHEDULE",
            joinColumns = @JoinColumn(name = "SCHEDULE_ID"),
            inverseJoinColumns = @JoinColumn(name = "USER_ID"))
    private Set<Employee> employees;


    public Schedule() {
    }

    public Set<Pet> getPets() {
        return pets;
    }

    public void setPets(Set<Pet> pets) {
        this.pets = pets;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<EmployeeSkill> getActivities() {
        return activities;
    }

    public void setActivities(Set<EmployeeSkill> activities) {
        this.activities = activities;
    }
}
