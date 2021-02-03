package com.udacity.jdnd.course3.critter.domain.schedule;

import com.udacity.jdnd.course3.critter.domain.pet.Pet;
import com.udacity.jdnd.course3.critter.domain.user.Employee;
import com.udacity.jdnd.course3.critter.domain.user.EmployeeSkill;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name="SCHEDULE")
public class Schedule {

    public Schedule() {

    }

    public Long getId() {
        return id;
    }

    public Schedule(Long id, List<Employee> employees, List<Pet> pets, LocalDate date, Set<EmployeeSkill> activities) {
        this.id = id;
        this.date = date;
        this.activities = activities;
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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="SCHEDULE_ID")
    private Long id;
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
    @Column
    private LocalDate date;

    @ElementCollection(targetClass = EmployeeSkill.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name="SCHEDULE_ACTIVITIES")
    @Column(name="SCHEDULE_ACTIVITY_TYPE")
    private Set<EmployeeSkill> activities = new HashSet<EmployeeSkill>();


}
