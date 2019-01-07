package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.CheckIn;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CheckIn entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CheckInRepository extends JpaRepository<CheckIn, Long> {

}
