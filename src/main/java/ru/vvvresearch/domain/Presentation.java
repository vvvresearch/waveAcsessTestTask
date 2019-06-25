package ru.vvvresearch.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Presentation.
 */
@Entity
@Table(name = "presentation")
public class Presentation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "topic", nullable = false)
    private String topic;

    @Column(name = "description")
    private String description;

    @ManyToMany
    @JoinTable(name = "presentation_users",
               joinColumns = @JoinColumn(name = "presentation_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"))
    private Set<User> users = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public Presentation topic(String topic) {
        this.topic = topic;
        return this;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDescription() {
        return description;
    }

    public Presentation description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<User> getUsers() {
        return users;
    }

    public Presentation users(Set<User> users) {
        this.users = users;
        return this;
    }

    public Presentation addUsers(User user) {
        this.users.add(user);
        return this;
    }

    public Presentation removeUsers(User user) {
        this.users.remove(user);
        return this;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Presentation)) {
            return false;
        }
        return id != null && id.equals(((Presentation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Presentation{" +
            "id=" + getId() +
            ", topic='" + getTopic() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
