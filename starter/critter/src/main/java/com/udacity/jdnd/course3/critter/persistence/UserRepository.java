package com.udacity.jdnd.course3.critter.persistence;

import com.udacity.jdnd.course3.critter.domain.pet.Pet;
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
import java.util.*;

@Repository
@Transactional
public class UserRepository {
    // private final String ALL_CUSTOMERS = "SELECT DISTINCT customer from Customer customer LEFT JOIN  customer.petSet ps ORDER BY ps.id";

    private PetRepository petRepository;

    public UserRepository(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    private final String PET_OWNER;
    {
        PET_OWNER = "SELECT  pet.customer from Pet pet where pet.id = ?1";
    }
    @PersistenceContext
    EntityManager entityManager;
    public Customer getCustomerByPetId(Long id) {
        var query = entityManager.createNativeQuery(
                "select * from customer cust , pet pt, user  usr " +
                        "where cust.user_id = pt.user_id " +
                        "and cust.user_id = usr.user_id " +
                        "and pt.pet_id= ?1", Customer.class)
                .setParameter(1,id);

        Customer customer = (Customer) query.getSingleResult();

        if(customer.getPetSet() == null){

            query = entityManager.createQuery("SELECT pt from Pet pt where pt.id = ?1", Pet.class).setParameter(1,id);

            var pets = query.getResultList();

            HashSet targetSet = new HashSet<>(pets);

            customer.setPetSet(targetSet);

        }

        return customer;
    }
    public Customer saveCustomer(Customer customer) {
        entityManager.persist(customer);
        entityManager.flush();
        return customer;
    }
    public List<Customer> getAllCustomers() {
        String ALL_CUSTOMERS = "SELECT DISTINCT customer from Customer customer";
        var query = entityManager.createQuery(ALL_CUSTOMERS, Customer.class);
        var customers =  query.getResultList();
        for( Customer customer:customers) customer.setPetSet(new HashSet<>(petRepository.getPetsByOwnerId(customer.getId())));
        return customers;

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
        String availabilitySearchQl ="select * from EMPLOYEE employee, USER user , EMPLOYEE_SKILLS skills, DAYS_OF_WEEK dow " +
                "where employee.user_id = user.user_id and employee.user_id =skills.employee_user_id and employee.user_id = dow.employee_user_id " +
                "and skills.employee_skill_type in :skills " +
                "and UPPER(DAYNAME(:dateRequested)) in (dow.available_days) " +
                "and employee.user_id NOT IN " +
                "(SELECT empsch.user_id FROM employee_schedule empsch, schedule sch  " +
                "where empsch.user_id  = employee.user_id and empsch.schedule_id = sch.schedule_id and sch.date = :dateRequested)";

        Query query = entityManager.createNativeQuery(availabilitySearchQl, Employee.class).unwrap(NativeQuery.class);
        query.setParameter("skills", Arrays.asList(employeeRequestDTO.getSkills().stream().map
                (employeeSkill -> employeeSkill.name().replaceAll("\\[", "").replaceAll("\\]", "")).toArray()));
        query.setParameter("dateRequested", employeeRequestDTO.getDate());
        List<Employee> employees= query.getResultList();

        // Check for available days




        return employees;
    }

}
