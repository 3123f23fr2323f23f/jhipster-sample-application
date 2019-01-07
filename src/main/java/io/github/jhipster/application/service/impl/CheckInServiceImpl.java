package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.CheckInService;
import io.github.jhipster.application.domain.CheckIn;
import io.github.jhipster.application.repository.CheckInRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing CheckIn.
 */
@Service
@Transactional
public class CheckInServiceImpl implements CheckInService {

    private final Logger log = LoggerFactory.getLogger(CheckInServiceImpl.class);

    private final CheckInRepository checkInRepository;

    public CheckInServiceImpl(CheckInRepository checkInRepository) {
        this.checkInRepository = checkInRepository;
    }

    /**
     * Save a checkIn.
     *
     * @param checkIn the entity to save
     * @return the persisted entity
     */
    @Override
    public CheckIn save(CheckIn checkIn) {
        log.debug("Request to save CheckIn : {}", checkIn);
        return checkInRepository.save(checkIn);
    }

    /**
     * Get all the checkIns.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CheckIn> findAll(Pageable pageable) {
        log.debug("Request to get all CheckIns");
        return checkInRepository.findAll(pageable);
    }


    /**
     * Get one checkIn by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CheckIn> findOne(Long id) {
        log.debug("Request to get CheckIn : {}", id);
        return checkInRepository.findById(id);
    }

    /**
     * Delete the checkIn by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CheckIn : {}", id);
        checkInRepository.deleteById(id);
    }
}
