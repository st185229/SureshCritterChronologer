package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.domain.pet.Pet;
import com.udacity.jdnd.course3.critter.domain.user.*;
import com.udacity.jdnd.course3.critter.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

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
        return mapCustomerToDTO(savedCustomer);
    }
    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers() {
        var customers = userService.getAllCustomers();
        List<CustomerDTO> customerDTOS = customers.stream().map(customer -> {
            return getCustomerDTO(customer);
        }).collect(toList());
        return customerDTOS;
    }
    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId) {
        var  savedCustomer = userService.findOwnerByPet(petId);
        return getCustomerDTO(savedCustomer);
    }
    private CustomerDTO getCustomerDTO(Customer savedCustomer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(savedCustomer.getId());
        customerDTO.setName(savedCustomer.getName());
        customerDTO.setPhoneNumber(savedCustomer.getPhoneNumber());
        customerDTO.setNotes(savedCustomer.getNotes());
        var petSet = savedCustomer.getPetSet();
        var petIds = petSet.stream().map(Pet::getId).collect(Collectors.toList());
        customerDTO.setPetIds(petIds);
        return customerDTO;
    }
    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {

        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setDaysAvailable(employeeDTO.getDaysAvailable());
        employee.setSkills(employeeDTO.getSkills());
        var savedEmployee = userService.saveEmployee(employee);
        BeanUtils.copyProperties(savedEmployee, employeeDTO);
        employeeDTO.setId(employee.getId());
        return employeeDTO;
    }


    @GetMapping("/customer/{id}")
    public CustomerDTO getCustomerById(@PathVariable long id) {
        var customerDTO = new CustomerDTO();
        var customer = userService.getCustomerById(id);
        BeanUtils.copyProperties(customer, customerDTO);
        customerDTO.setId(customer.getId());
        return customerDTO;

    }
    /*
    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        throw new UnsupportedOperationException();
    }
     */
    // The above is replaced with GetMapping getEmployeeById
    @GetMapping("/employee/{id}")
    public EmployeeDTO getEmployeeById(@PathVariable long id) {

        var employeeDTO = new EmployeeDTO();
        var employee =  userService.getEmployeeById(id);
        BeanUtils.copyProperties(employee, employeeDTO);
        employeeDTO.setId(employee.getId());
        return employeeDTO;
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {

        userService.upDateEmployeeAvailability(employeeId,daysAvailable);

    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeRequestDTO) {


        List<Employee> availableEmployees =
        userService.getAvailableEmployees(employeeRequestDTO);

        return availableEmployees.stream().map(employee -> {
            var employeeDTO = new EmployeeDTO();
            BeanUtils.copyProperties(employee, employeeDTO);
            employeeDTO.setId(employee.getId());
            return employeeDTO;
        }).collect(Collectors.toList());


    }

    private CustomerDTO mapCustomerToDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        customerDTO.setId(customer.getId());
        return customerDTO;
    }



}
