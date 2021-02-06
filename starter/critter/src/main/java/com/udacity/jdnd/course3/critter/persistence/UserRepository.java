package com.udacity.jdnd.course3.critter.persistence;

import com.udacity.jdnd.course3.critter.domain.pet.Pet;
import com.udacity.jdnd.course3.critter.domain.user.*;
import org.hibernate.jpa.QueryHints;
import org.hibernate.query.NativeQuery;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@Transactional
public class UserRepository {
   // private final String ALL_CUSTOMERS = "SELECT DISTINCT customer from Customer customer LEFT JOIN  customer.petSet ps ORDER BY ps.id";

    private final String ALL_CUSTOMERS = "SELECT DISTINCT customer from Customer customer";
    private final String PET_OWNER = "SELECT  pet.customer from Pet pet where pet.id = ?1";

    @PersistenceContext
    EntityManager entityManager;
    public void saveUser(User user) {
        entityManager.persist(user);
    }
    public Customer getCustomerByPetId(Long id) {
        var query = entityManager.createQuery(PET_OWNER, User.class);
        User user = query.setParameter(1, id).getSingleResult();
        Customer customer = entityManager.find(Customer.class, user.getId());
        return customer;
    }
    public Customer saveCustomer(Customer customer) {
        entityManager.persist(customer);
        entityManager.flush();
        return customer;
    }
    public List<Customer> getAllCustomers() {
        var query = entityManager.createQuery(ALL_CUSTOMERS,Customer.class);
        List<Customer> customers= query.getResultList();
        return customers;
    }

    public Employee saveEmployee(Employee employee) {
        entityManager.persist(employee);
        entityManager.flush();
        return employee;
    }
    public Employee getEmployeeById(Long id) {
        return entityManager.find(Employee.class, id);

    }
    public Customer getCustomerById(Long id) {
        return entityManager.find(Customer.class, id);
    }
    public void upDateEmployeeAvailability(@Param("employeeId") Long employeeId, @Param("availableDays") Set<DayOfWeek> daysAvailable) {
        Employee employee = entityManager.getReference(Employee.class, employeeId);
        employee.setDaysAvailable(daysAvailable);
        entityManager.merge(employee);
        entityManager.flush();
    }
    public List<Employee> getAvailableEmployees(EmployeeRequestDTO employeeRequestDTO) {
        String availablitySearchQl =
                "select * from EMPLOYEE employee, USER user , EMPLOYEE_SKILLS skills\n" +
                        "where employee.user_id = user.user_id \n" +
                        "and employee.user_id =skills.employee_user_id \n" +
                        "and skills.employee_skill_type in :skills \n" +
                        "and employee.user_id NOT IN  " +
                        "(SELECT schedule.user_id FROM SCHEDULE schedule where schedule.user_id  = employee.user_id and schedule.date = :dateRequested )";

        Query query = entityManager.createNativeQuery(availablitySearchQl, Employee.class).unwrap(NativeQuery.class);
        var skillsSet = employeeRequestDTO.getSkills().stream().map(employeeSkill -> {
            String str = employeeSkill.name().replaceAll("\\[", "").replaceAll("\\]", "");
            return str;
        }).collect(Collectors.toList());
        query.setParameter("skills", Arrays.asList(skillsSet.toArray()));
        query.setParameter("dateRequested", employeeRequestDTO.getDate());
        var employeeList = query.getResultList();
        return employeeList;
    }

    public List<Customer> getAllCustomersALT(){

        String customerQuery = "select  * from customer customer0_  inner join user customer0_1_  on customer0_.user_id=customer0_1_.user_id  " +
                "left outer join pet petset1_  on customer0_.user_id=petset1_.user_id  order by petset1_.pet_id";
        Query query = entityManager.createNativeQuery(customerQuery, Customer.class).unwrap(NativeQuery.class);

        var customerList = query.getResultList();
        return customerList;


    }
}
