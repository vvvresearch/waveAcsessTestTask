package ru.vvvresearch.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Room.
 */
@Entity
@Table(name = "room")
public class Room implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "room_name", nullable = false)
    private String roomName;

    @Column(name = "level")
    private Integer level;

    @Column(name = "capacity")
    private Integer capacity;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "room")
    private Set<Schedule> schedules = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public Room roomName(String roomName) {
        this.roomName = roomName;
        return this;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Integer getLevel() {
        return level;
    }

    public Room level(Integer level) {
        this.level = level;
        return this;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Room capacity(Integer capacity) {
        this.capacity = capacity;
        return this;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Set<Schedule> getSchedules() {
        return schedules;
    }

    public Room schedules(Set<Schedule> schedules) {
        this.schedules = schedules;
        return this;
    }

    public Room addSchedules(Schedule schedule) {
        this.schedules.add(schedule);
        schedule.setRoom(this);
        return this;
    }

    public Room removeSchedules(Schedule schedule) {
        this.schedules.remove(schedule);
        schedule.setRoom(null);
        return this;
    }

    public void setSchedules(Set<Schedule> schedules) {
        this.schedules = schedules;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Room)) {
            return false;
        }
        return id != null && id.equals(((Room) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Room{" +
            "id=" + getId() +
            ", roomName='" + getRoomName() + "'" +
            ", level=" + getLevel() +
            ", capacity=" + getCapacity() +
            "}";
    }
}
