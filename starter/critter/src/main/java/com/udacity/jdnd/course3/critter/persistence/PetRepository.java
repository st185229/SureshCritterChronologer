package com.udacity.jdnd.course3.critter.persistence;


import com.udacity.jdnd.course3.critter.domain.pet.Pet;
import com.udacity.jdnd.course3.critter.domain.schedule.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface PetRepository extends JpaRepository<Pet, Long> {


    @Query("SELECT pet FROM Pet pet WHERE pet.customer.id = ?1")
    List<Pet> getPetsByOwnerId(Long ownerId);

    @Query("SELECT pet.petsScheduleSet from Pet pet where pet.id = :#{#petId}")
    List<Schedule> getScheduleForPet(@Param ("petId") Long petId);
}
