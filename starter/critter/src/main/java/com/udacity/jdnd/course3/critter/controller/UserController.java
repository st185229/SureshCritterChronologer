package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.domain.pet.Pet;
import com.udacity.jdnd.course3.critter.domain.user.*;
import com.udacity.jdnd.course3.critter.exception.EntityNotFoundException;
import com.udacity.jdnd.course3.critter.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * Handles web requests related to Users.
 * <p>
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setNotes(customerDTO.getNotes());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        var savedCustomer = userService.saveCustomer(customer);
        CustomerDTO responseCustomerDTO = new CustomerDTO();

        var petSet = savedCustomer.getPetSet();
        if (petSet != null && petSet.size() > 0) {
            var petIds = petSet.stream().map(Pet::getId).collect(Collectors.toList());
            responseCustomerDTO.setPetIds(petIds);
        }
        responseCustomerDTO.setPhoneNumber(savedCustomer.getPhoneNumber());
        responseCustomerDTO.setNotes(savedCustomer.getNotes());
        responseCustomerDTO.setName(savedCustomer.getName());
        responseCustomerDTO.setId(savedCustomer.getId());
        return responseCustomerDTO;
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers() {
        try {
            var customers = userService.getAllCustomers();
            return customers.stream().map(this::getCustomerDTO).collect(toList());
        } catch (EntityNotFoundException exec) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exec.getMessage(), exec);

        }
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId) {
        try {
            var savedCustomer = userService.findOwnerByPet(petId);

            return getCustomerDTO(savedCustomer);
        } catch (EntityNotFoundException exec) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exec.getMessage(), exec);
        } catch (javax.persistence.NoResultException exec) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No pet found, or orphaned Pet - # " + petId, exec);
        }
    }

    private CustomerDTO getCustomerDTO(Customer savedCustomer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(savedCustomer.getId());
        customerDTO.setName(savedCustomer.getName());
        customerDTO.setPhoneNumber(savedCustomer.getPhoneNumber());
        customerDTO.setNotes(savedCustomer.getNotes());
        var petSet = savedCustomer.getPetSet();
        if (petSet != null && petSet.size() > 0) {
            var petIds = petSet.stream().map(Pet::getId).collect(Collectors.toList());
            customerDTO.setPetIds(petIds);
        }
        return customerDTO;
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setDaysAvailable(employeeDTO.getDaysAvailable());
        employee.setSkills(employeeDTO.getSkills());
        var savedEmployee = userService.saveEmployee(employee);
        EmployeeDTO responseDTO = new EmployeeDTO();
        BeanUtils.copyProperties(savedEmployee, responseDTO);
        responseDTO.setDaysAvailable(savedEmployee.getDaysAvailable());
        return responseDTO;
    }

    @GetMapping("/customer/{id}")
    public CustomerDTO getCustomerById(@PathVariable long id) {
        try {
            var customerDTO = new CustomerDTO();
            var customer = userService.getCustomerById(id);
            BeanUtils.copyProperties(customer, customerDTO);
            customerDTO.setId(customer.getId());
            var petSet = customer.getPetSet();
            var petIds = petSet.stream().map(Pet::getId).collect(Collectors.toList());
            customerDTO.setPetIds(petIds);

            return customerDTO;
        } catch (EntityNotFoundException exec) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exec.getMessage(), exec);
        }
    }

    @GetMapping("/employee/{id}")
    public EmployeeDTO getEmployeeById(@PathVariable long id) throws EntityNotFoundException {
        try {
            var employeeDTO = new EmployeeDTO();
            var employee = userService.getEmployeeById(id);
            BeanUtils.copyProperties(employee, employeeDTO);
            employeeDTO.setId(employee.getId());
            return employeeDTO;
        } catch (EntityNotFoundException exec) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exec.getMessage(), exec);
        }
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        userService.upDateEmployeeAvailability(employeeId, daysAvailable);

    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeRequestDTO) {


        try {
            List<Employee> availableEmployees =
                    userService.getAvailableEmployees(employeeRequestDTO);

            return availableEmployees.stream().map(employee -> {
                var employeeDTO = new EmployeeDTO();
                BeanUtils.copyProperties(employee, employeeDTO);
                employeeDTO.setId(employee.getId());
                return employeeDTO;
            }).collect(Collectors.toList());
        } catch (EntityNotFoundException exec) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exec.getMessage(), exec);
        }
    }
}
