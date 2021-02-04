package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.domain.user.Customer;
import com.udacity.jdnd.course3.critter.domain.user.Employee;
import com.udacity.jdnd.course3.critter.persistence.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

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
    public Employee saveEmployee(Employee employee) {
        return userRepository.saveEmployee(employee);
    }

    public Employee getEmployeeById(long id) {

        return userRepository.getEmployeeById(id);

    }
    public Customer getCustomerById(long id) {

        return userRepository.getCustomerById(id);

    }

    public void upDateEmployeeAvailability(long employeeId, Set<DayOfWeek> daysAvailable) {
        userRepository.upDateEmployeeAvailability(employeeId,daysAvailable);
    }
}