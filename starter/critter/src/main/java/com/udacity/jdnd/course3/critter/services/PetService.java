package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.domain.pet.PetDTO;
import com.udacity.jdnd.course3.critter.persistence.PetRepository;
import org.springframework.stereotype.Service;

@Service
public class PetService {

    private PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Long save(PetDTO p) {
        //Mapping or Projection
        //petRepository.save(p);
        //return p.getId();
        return  null;
    }
}
