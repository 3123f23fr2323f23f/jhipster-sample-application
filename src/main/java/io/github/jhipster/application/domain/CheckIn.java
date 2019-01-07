package io.github.jhipster.application.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A CheckIn.
 */
@Entity
@Table(name = "check_in")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CheckIn implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Integer userID;

    @Column(name = "time_check_in")
    private String timeCheckIn;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserID() {
        return userID;
    }

    public CheckIn userID(Integer userID) {
        this.userID = userID;
        return this;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getTimeCheckIn() {
        return timeCheckIn;
    }

    public CheckIn timeCheckIn(String timeCheckIn) {
        this.timeCheckIn = timeCheckIn;
        return this;
    }

    public void setTimeCheckIn(String timeCheckIn) {
        this.timeCheckIn = timeCheckIn;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CheckIn checkIn = (CheckIn) o;
        if (checkIn.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), checkIn.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CheckIn{" +
            "id=" + getId() +
            ", userID=" + getUserID() +
            ", timeCheckIn='" + getTimeCheckIn() + "'" +
            "}";
    }
}
