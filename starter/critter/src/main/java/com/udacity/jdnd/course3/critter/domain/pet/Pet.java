package com.udacity.jdnd.course3.critter.domain.pet;

import com.udacity.jdnd.course3.critter.domain.schedule.Schedule;
import com.udacity.jdnd.course3.critter.domain.user.Customer;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "PET")
public class Pet {
    @Id
    @GeneratedValue
    @Column(name = "PET_ID", updatable = false, nullable = false)
    private Long id;
    @ManyToMany(mappedBy = "pets", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<Schedule> petsScheduleSet;
    @Enumerated(EnumType.STRING)
    @Column(length = 10, name = "PET_TYPE")
    private PetType type;
    @Column(name = "PET_NAME")
    private String name;
    //Mapped to customer => A customer can have many pets
    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private Customer customer;
    @Column(name = "DATE_OF_BIRTH")
    private LocalDate birthDate;
    @Column(name = "NOTES")
    private String notes;

    public Pet() {
    }

    public Pet(Long id) {
        this.id = id;
    }

    public Set<Schedule> getScheduleSet() {
        return petsScheduleSet;
    }

    public void setScheduleSet(Set<Schedule> scheduleSet) {
        this.petsScheduleSet = scheduleSet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
