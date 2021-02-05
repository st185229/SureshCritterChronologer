package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.domain.pet.Pet;
import com.udacity.jdnd.course3.critter.persistence.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    private PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Pet save(Pet p) {
        var result = petRepository.save(p);

        return  result;
    }

    public Pet getOne(long petId) {
        return petRepository.getOne(petId);
    }

    public List<Pet> findAll() {
        return petRepository.findAll();
    }

    public List<Pet> getPetsByOwnerId(Long ownerId) {
       return petRepository.getPetsByOwnerId(ownerId);
    }
}
