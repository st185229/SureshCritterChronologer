package com.udacity.jdnd.course3.critter.persistence;


import com.udacity.jdnd.course3.critter.domain.pet.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface PetRepository extends JpaRepository<Pet, Long> {


    @Query("SELECT pet FROM Pet pet WHERE pet.customer.id = ?1")
    List<Pet> getPetsByOwnerId(Long ownerId);

}
