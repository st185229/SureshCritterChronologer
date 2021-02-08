package com.udacity.jdnd.course3.critter.domain.user;

import com.udacity.jdnd.course3.critter.domain.pet.Pet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "CUSTOMER")
public class Customer extends User {
    @Column(name = "CONTACT_PHONE")
    private String phoneNumber;
    @Column(name = "NOTES")
    private String notes;
    @OneToMany(mappedBy = "customer")
    private Set<Pet> petSet;

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

    public void setPetSet(Set<Pet> petSet) {
        this.petSet = petSet;
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
