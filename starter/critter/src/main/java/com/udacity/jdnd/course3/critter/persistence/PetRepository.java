package com.udacity.jdnd.course3.critter.persistence;


import com.udacity.jdnd.course3.critter.domain.pet.Pet;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
@Transactional
public class PetRepository {

    @PersistenceContext
    EntityManager entityManager;

    public Long save(Pet pet){
        entityManager.persist(pet);
        entityManager.flush();
        return pet.getId();
    }

}
