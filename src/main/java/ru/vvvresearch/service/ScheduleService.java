package ru.vvvresearch.service;

import ru.vvvresearch.domain.Schedule;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.vvvresearch.service.error.BadTimeForScheduleEntity;

import java.util.Optional;

/**
 * Service Interface for managing {@link Schedule}.
 */
public interface ScheduleService {

    /**
     * Save a schedule.
     *
     * @param schedule the entity to save.
     * @return the persisted entity.
     */
    Schedule save(Schedule schedule) throws BadTimeForScheduleEntity;

    /**
     * Get all the schedules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Schedule> findAll(Pageable pageable);


    /**
     * Get the "id" schedule.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Schedule> findOne(Long id);

    /**
     * Delete the "id" schedule.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
