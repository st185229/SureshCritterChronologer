package com.udacity.jdnd.course3.critter.persistence;

import com.udacity.jdnd.course3.critter.domain.schedule.Schedule;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class ScheduleRepository {
    private final EntityManager entityManager;

    public ScheduleRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<Schedule> save(Schedule schedule) {
        try {
            entityManager.persist(schedule);
            entityManager.flush();
            return Optional.of(schedule);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<Schedule> findById(Integer id) {
        Schedule schedule = entityManager.find(Schedule.class, id);
        return schedule != null ? Optional.of(schedule) : Optional.empty();
    }

    public List<Schedule> findAll() {
        return entityManager.createQuery("select sch from Schedule sch", Schedule.class).getResultList();
    }

    public Optional<List<Schedule>> getScheduleForPet(Long petId) {

        var searchScheduleQuery = entityManager.createQuery("select schedule from Schedule schedule join fetch schedule.pets pc where pc.id =?1", Schedule.class);
        var parameterisedSearchQuery = searchScheduleQuery.setParameter(1, petId);
        List<Schedule> schedules = parameterisedSearchQuery.getResultList();
        return schedules != null  ? Optional.of(schedules) : Optional.empty();

    }

    public Optional<List<Schedule>> getScheduleForEmployee(Long employeeId) {

        var searchScheduleQuery = entityManager.createQuery("select schedule from Schedule schedule join fetch schedule.employees es where es.id =?1", Schedule.class);
        var parameterisedSearchQuery = searchScheduleQuery.setParameter(1, employeeId);
        List<Schedule> schedules = parameterisedSearchQuery.getResultList();
        return schedules != null  ? Optional.of(schedules) : Optional.empty();

    }

    public Optional<List<Schedule>> getScheduleForCustomer(Long customerID) {
        var searchScheduleQuery = entityManager.createNativeQuery(
                "select sh.* from schedule sh, pet_schedule ps, pet pt, customer cust " +
                        "where sh.schedule_id = ps.schedule_id and pt.pet_id = ps.pet_id and pt.user_id = cust.user_id and cust.user_id = ?1", Schedule.class);
        var parameterisedSearchQuery = searchScheduleQuery.setParameter(1, customerID);
        List<Schedule> schedules = parameterisedSearchQuery.getResultList();
        return schedules != null  ? Optional.of(schedules) : Optional.empty();

    }

}