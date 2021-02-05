package com.udacity.jdnd.course3.critter.persistence;

import com.udacity.jdnd.course3.critter.domain.user.Customer;
import com.udacity.jdnd.course3.critter.domain.user.Employee;
import com.udacity.jdnd.course3.critter.domain.user.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.domain.user.User;
import org.hibernate.query.NativeQuery;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@Transactional
public class UserRepository {
    private final String ALL_CUSTOMERS = "SELECT customer from Customer customer";
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
        return entityManager.createQuery(ALL_CUSTOMERS).getResultList();
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

        String qlString =
                "select * from EMPLOYEE employee, USER user , EMPLOYEE_SKILLS skills\n" +
                        "        where employee.user_id = user.user_id \n" +
                        "        and employee.user_id =skills.employee_user_id\n" +
                        "        and skills.employee_skill_type in :skills";
        Query query = entityManager.createNativeQuery(qlString, Employee.class).unwrap(NativeQuery.class);
        var skillsSet = employeeRequestDTO.getSkills().stream().map(employeeSkill -> {
            String str = employeeSkill.name().replaceAll("\\[", "").replaceAll("\\]", "");
            return str;
        }).collect(Collectors.toList());
        query.setParameter("skills", Arrays.asList(skillsSet.toArray()));
        var employeeList = query.getResultList();
        return employeeList;


    }
}
