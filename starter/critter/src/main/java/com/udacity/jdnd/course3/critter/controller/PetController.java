package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.domain.pet.Pet;
import com.udacity.jdnd.course3.critter.domain.pet.PetDTO;
import com.udacity.jdnd.course3.critter.persistence.PetRepository;
import com.udacity.jdnd.course3.critter.persistence.UserRepository;
import com.udacity.jdnd.course3.critter.services.PetService;
import com.udacity.jdnd.course3.critter.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    private final PetService petService;
    private final UserService userService;

    public PetController(PetService petService, UserService userService) {
        this.petService = petService;
        this.userService = userService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {



        Pet pet = new Pet();
        pet.setName(petDTO.getName());
        pet.setBirthDate(petDTO.getBirthDate());
        Long customerID = petDTO.getOwnerId();
        if(customerID != null){
            var customer = userService.getCustomerById(customerID);
            pet.setCustomer(customer);
            pet.setName(petDTO.getNotes());
            pet.setNotes(petDTO.getNotes());
            pet.setType(petDTO.getType());
            Pet savedPet = petService.save(pet);
            PetDTO responseDTO = new PetDTO();
            BeanUtils.copyProperties(savedPet,responseDTO);
            responseDTO.setOwnerId(savedPet.getCustomer().getId());
            return responseDTO;
        }
        throw new RuntimeException("The Pet should have an owner");

    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {

        Pet savedPet = petService.getOne(petId);
        var petDTO = new PetDTO();
       BeanUtils.copyProperties(savedPet,petDTO);
        petDTO.setOwnerId(savedPet.getCustomer().getId());

        return petDTO;

    }

    @GetMapping
    public List<PetDTO> getPets(){
        var pets = petService.findAll();
        return pets.stream().map(pet->{
            PetDTO petDTO = new PetDTO();
            BeanUtils.copyProperties(pet,petDTO);
            return petDTO;
        }).collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {

        List<Pet> pets = petService.getPetsByOwnerId(ownerId);
        return pets.stream().map(pet->{
            PetDTO petDTO = new PetDTO();
            BeanUtils.copyProperties(pet,petDTO);
            petDTO.setOwnerId(pet.getCustomer().getId());
            return petDTO;
        }).collect(Collectors.toList());
    }
}
