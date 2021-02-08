package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.domain.user.Customer;
import com.udacity.jdnd.course3.critter.domain.user.Employee;
import com.udacity.jdnd.course3.critter.domain.user.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.exception.EntityNotFoundException;
import com.udacity.jdnd.course3.critter.persistence.UserRepository;
import org.springframework.stereotype.Service;

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

    public List<Customer> getAllCustomers() throws EntityNotFoundException {
        var customers = userRepository.getAllCustomers();
        if (customers.size() == 0) {
            throw new EntityNotFoundException(Customer.class, "id", "0");
        }
        return customers;
    }

    //EntityNotFoundException(Pet.class, "id", String.valueOf(petId));
    public Customer findOwnerByPet(Long id) throws EntityNotFoundException {
        var customer = userRepository.getCustomerByPetId(id);
        if (customer == null || customer.getId() == 0) {
            throw new EntityNotFoundException(Customer.class, "id", String.valueOf(id));
        }
        return customer;
    }

    public Employee saveEmployee(Employee employee) {
        return userRepository.saveEmployee(employee);
    }

    public Employee getEmployeeById(long id) throws EntityNotFoundException {
        var employee = userRepository.getEmployeeById(id);
        if (employee == null || employee.getId() == 0) {
            throw new EntityNotFoundException(Employee.class, "id", String.valueOf(id));
        }
        return employee;
    }

    public Customer getCustomerById(long id) throws EntityNotFoundException {
        var customer = userRepository.getCustomerById(id);
        if (customer == null || customer.getId() == 0) {
            throw new EntityNotFoundException(Employee.class, "id", String.valueOf(id));
        }
        return customer;
    }

    public void upDateEmployeeAvailability(long employeeId, Set<DayOfWeek> daysAvailable) {
        userRepository.upDateEmployeeAvailability(employeeId, daysAvailable);
    }

    public List<Employee> getAvailableEmployees(EmployeeRequestDTO employeeRequestDTO) throws EntityNotFoundException {
        var employees = userRepository.getAvailableEmployees(employeeRequestDTO);
        if (employees.size() == 0) {
            throw new EntityNotFoundException(EmployeeRequestDTO.class, "id", "0");
        }
        return employees;
    }
}