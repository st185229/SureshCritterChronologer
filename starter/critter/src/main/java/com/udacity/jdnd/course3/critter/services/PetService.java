package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.domain.pet.Pet;
import com.udacity.jdnd.course3.critter.exception.EntityNotFoundException;
import com.udacity.jdnd.course3.critter.persistence.PetRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class PetService {
    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Transactional
    public Pet save(Pet p) {
        AtomicReference<Pet> result = new AtomicReference<>(petRepository.saveAndFlush(p));
        return result.get();
    }

    public Pet getOne(long petId) {
        Pet pet = petRepository.getOne(petId);
        if (pet == null || pet.getId() == 0) {
            throw new EntityNotFoundException(Pet.class, "id", String.valueOf(petId));
        }
        return pet;
    }

    public List<Pet> findAll() {
        var pets = petRepository.findAll();
        if (pets.size() == 0) {
            throw new EntityNotFoundException(Pet.class, "id", "0");
        }
        return pets;
    }

    public List<Pet> getPetsByOwnerId(Long ownerId) {
        var pets = petRepository.getPetsByOwnerId(ownerId);
        if (pets == null || pets.size() == 0) {
            throw new EntityNotFoundException(Pet.class, "id" + ownerId);
        }
        return pets;
    }
}
