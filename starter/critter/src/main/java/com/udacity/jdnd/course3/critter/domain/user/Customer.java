package com.udacity.jdnd.course3.critter.domain.user;

import com.udacity.jdnd.course3.critter.domain.pet.Pet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "CUSTOMER")
public class Customer extends User {
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Set<Pet> getPetSet() {
        return petSet;
    }

    @Column(name = "CONTACT_PHONE")
    private String phoneNumber;

    @Column(name = "NOTES")
    private String notes;

    @OneToMany(mappedBy = "customer", orphanRemoval = true)
    private final Set<Pet> petSet = new HashSet<Pet>();

}
