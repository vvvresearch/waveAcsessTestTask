package ru.vvvresearch.service.impl;

import ru.vvvresearch.service.ScheduleService;
import ru.vvvresearch.domain.Schedule;
import ru.vvvresearch.repository.ScheduleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vvvresearch.service.error.BadTimeForScheduleEntity;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Schedule}.
 */
@Service
@Transactional
public class ScheduleServiceImpl implements ScheduleService {

    private final Logger log = LoggerFactory.getLogger(ScheduleServiceImpl.class);

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    /**
     * Save a schedule.
     *
     * @param schedule the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Schedule save(Schedule schedule) throws BadTimeForScheduleEntity {
        log.debug("Request to save Schedule : {}", schedule);
//        List<Schedule> schedules = scheduleRepository.findSchedulesByStartTimeIsBetweenAndRoom(schedule.getStartTime(),schedule.getEndTime(),schedule.getRoom());
//        schedules.forEach(schedule1 -> {log.debug(schedule1.toString());});
        if (!scheduleRepository.existsScheduleByStartTimeAfterAndEndTimeBeforeAndRoom(schedule.getStartTime(),schedule.getEndTime(),schedule.getRoom())) {
            return scheduleRepository.save(schedule);
        }
        else throw new BadTimeForScheduleEntity();
    }

    /**
     * Get all the schedules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Schedule> findAll(Pageable pageable) {
        log.debug("Request to get all Schedules");
        return scheduleRepository.findAll(pageable);
    }


    /**
     * Get one schedule by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Schedule> findOne(Long id) {
        log.debug("Request to get Schedule : {}", id);
        return scheduleRepository.findById(id);
    }

    /**
     * Delete the schedule by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Schedule : {}", id);
        scheduleRepository.deleteById(id);
    }
}
