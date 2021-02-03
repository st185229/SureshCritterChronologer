package com.udacity.jdnd.course3.critter.persistence;

import com.udacity.jdnd.course3.critter.domain.user.Customer;
import com.udacity.jdnd.course3.critter.domain.user.Employee;
import com.udacity.jdnd.course3.critter.domain.user.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
@Transactional
public class UserRepository {

    @PersistenceContext
    EntityManager entityManager;

    public void saveUser(User user){

        entityManager.persist(user);

    }

    public Customer saveCustomer(Customer customer){

        entityManager.persist(customer);
        entityManager.flush();
        return customer;

    }
    public void saveEmployee(Employee employee){
        entityManager.persist(employee);

    }


}
