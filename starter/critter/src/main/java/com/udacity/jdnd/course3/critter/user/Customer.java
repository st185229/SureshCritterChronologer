package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import org.checkerframework.checker.units.qual.C;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="CUSTOMER")
public class Customer extends User {

    @Column(name="CONTACT_PHONE")
    private String phoneNumber;

    @Column(name = "NOTES")
    private String notes;

    @OneToMany(mappedBy = "customer", orphanRemoval = true)
    private Set<Pet> petSet=  new HashSet<Pet>();

}
