package ru.vvvresearch.repository;

import ru.vvvresearch.domain.Room;
import ru.vvvresearch.domain.Schedule;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;


/**
 * Spring Data  repository for the Schedule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long>{
    boolean existsScheduleByStartTimeAfterAndEndTimeBeforeAndRoom(ZonedDateTime startTime, ZonedDateTime endTime, Room room);
}
