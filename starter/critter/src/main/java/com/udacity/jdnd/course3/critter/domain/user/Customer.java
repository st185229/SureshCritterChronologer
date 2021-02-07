package com.udacity.jdnd.course3.critter.domain.user;

import com.udacity.jdnd.course3.critter.domain.pet.Pet;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.sql.Date;
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
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SELECT)
    private final Set<Pet> petSet = new HashSet<>();

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
