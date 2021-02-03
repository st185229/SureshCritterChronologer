package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.domain.pet.Pet;
import com.udacity.jdnd.course3.critter.domain.user.Customer;
import com.udacity.jdnd.course3.critter.domain.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.persistence.UserRepository;
import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {

        Customer cust = new Customer();
        cust.setName(customerDTO.getName());
        cust.setPhoneNumber(customerDTO.getPhoneNumber());
        cust.setNotes(customerDTO.getPhoneNumber());
        Customer savedCustomer = userRepository.saveCustomer(cust);
        customerDTO.setId(savedCustomer.getId());
        return customerDTO;

    }
}
