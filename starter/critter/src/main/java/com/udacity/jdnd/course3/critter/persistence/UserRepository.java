package com.udacity.jdnd.course3.critter.persistence;

import com.udacity.jdnd.course3.critter.domain.schedule.Schedule;
import com.udacity.jdnd.course3.critter.domain.user.*;
import org.hibernate.query.NativeQuery;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Repository
@Transactional
public class UserRepository {
    // private final String ALL_CUSTOMERS = "SELECT DISTINCT customer from Customer customer LEFT JOIN  customer.petSet ps ORDER BY ps.id";

    private final String PET_OWNER;

    {
        PET_OWNER = "SELECT  pet.customer from Pet pet where pet.id = ?1";
    }

    @PersistenceContext
    EntityManager entityManager;

    public Customer getCustomerByPetId(Long id) {
        var query = entityManager.createQuery(PET_OWNER, User.class);
        User user = query.setParameter(1, id).getSingleResult();
        return entityManager.find(Customer.class, user.getId());
    }

    public Customer saveCustomer(Customer customer) {
        entityManager.persist(customer);
        entityManager.flush();
        return customer;
    }

    public List<Customer> getAllCustomers() {
        String ALL_CUSTOMERS = "SELECT DISTINCT customer from Customer customer";
        var query = entityManager.createQuery(ALL_CUSTOMERS, Customer.class);
        return query.getResultList();
    }

    public List<Schedule> getAllPetScheduleByCustomer(Long customerId) {
        String ALL_PETS_BY_OWNER = "SELECT customer.petSet from Customer customer where customer.id = ?1";
        var query = entityManager.createQuery(ALL_PETS_BY_OWNER, Customer.class);
        List<Customer> customers = query.setParameter(1, customerId).getResultList();
        return null;

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
        String availabilitySearchQl =
                "select * from EMPLOYEE employee, USER user , EMPLOYEE_SKILLS skills\n" +
                        "where employee.user_id = user.user_id \n" +
                        "and employee.user_id =skills.employee_user_id \n" +
                        "and skills.employee_skill_type in :skills \n" +
                        "and employee.user_id NOT IN  " +
                        "(SELECT schedule.user_id FROM SCHEDULE schedule where schedule.user_id  = employee.user_id and schedule.date = :dateRequested )";

        Query query = entityManager.createNativeQuery(availabilitySearchQl, Employee.class).unwrap(NativeQuery.class);
        var skillsSet = new ArrayList<>();
        for (EmployeeSkill employeeSkill : employeeRequestDTO.getSkills()) {
            String s = employeeSkill.name().replaceAll("\\[", "").replaceAll("\\]", "");
            skillsSet.add(s);
        }
        query.setParameter("skills", Arrays.asList(skillsSet.toArray()));
        query.setParameter("dateRequested", employeeRequestDTO.getDate());
        var employeeList = query.getResultList();
        return employeeList;
    }

    public List getAllCustomersALT() {

        String customerQuery = "select  * from customer customer0_  inner join user customer0_1_  on customer0_.user_id=customer0_1_.user_id  " +
                "left outer join pet petset1_  on customer0_.user_id=petset1_.user_id  order by petset1_.pet_id";
        Query query = entityManager.createNativeQuery(customerQuery, Customer.class).unwrap(NativeQuery.class);

        var customerList = query.getResultList();
        return customerList;

    }
}
