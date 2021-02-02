package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.User;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "PET")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PET_ID", updatable = false, nullable = false)
    private Long id;

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

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
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

    @Enumerated(EnumType.STRING)
    @Column(length = 10, name = "PET_TYPE")
    private PetType type;

    @Column
    private String name;

    //Mapped to customer => A customer can have many pets
    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private User customer;

    @Column(name = "DATE_OF_BIRTH")
    private LocalDate birthDate;

    public Pet(Long id, PetType type, String name, User customer, LocalDate birthDate, String notes) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.customer = customer;
        this.birthDate = birthDate;
        this.notes = notes;
    }

    @Column(name = "REMARKS")
    private String notes;


}
