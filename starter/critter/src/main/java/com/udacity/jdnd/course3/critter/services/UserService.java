package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.domain.user.Customer;
import com.udacity.jdnd.course3.critter.persistence.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Customer saveCustomer(Customer customer) {
        return userRepository.saveCustomer(customer);
    }

    public List<Customer> getAllCustomers() {
        return userRepository.getAllCustomers();
    }

    public Customer findOwnerByPet(Long id) {
        return userRepository.getCustomerByPetId(id);
    }
}