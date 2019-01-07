package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.CheckIn;
import io.github.jhipster.application.service.CheckInService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CheckIn.
 */
@RestController
@RequestMapping("/api")
public class CheckInResource {

    private final Logger log = LoggerFactory.getLogger(CheckInResource.class);

    private static final String ENTITY_NAME = "checkIn";

    private final CheckInService checkInService;

    public CheckInResource(CheckInService checkInService) {
        this.checkInService = checkInService;
    }

    /**
     * POST  /check-ins : Create a new checkIn.
     *
     * @param checkIn the checkIn to create
     * @return the ResponseEntity with status 201 (Created) and with body the new checkIn, or with status 400 (Bad Request) if the checkIn has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/check-ins")
    @Timed
    public ResponseEntity<CheckIn> createCheckIn(@RequestBody CheckIn checkIn) throws URISyntaxException {
        log.debug("REST request to save CheckIn : {}", checkIn);
        if (checkIn.getId() != null) {
            throw new BadRequestAlertException("A new checkIn cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CheckIn result = checkInService.save(checkIn);
        return ResponseEntity.created(new URI("/api/check-ins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /check-ins : Updates an existing checkIn.
     *
     * @param checkIn the checkIn to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated checkIn,
     * or with status 400 (Bad Request) if the checkIn is not valid,
     * or with status 500 (Internal Server Error) if the checkIn couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/check-ins")
    @Timed
    public ResponseEntity<CheckIn> updateCheckIn(@RequestBody CheckIn checkIn) throws URISyntaxException {
        log.debug("REST request to update CheckIn : {}", checkIn);
        if (checkIn.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CheckIn result = checkInService.save(checkIn);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, checkIn.getId().toString()))
            .body(result);
    }

    /**
     * GET  /check-ins : get all the checkIns.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of checkIns in body
     */
    @GetMapping("/check-ins")
    @Timed
    public ResponseEntity<List<CheckIn>> getAllCheckIns(Pageable pageable) {
        log.debug("REST request to get a page of CheckIns");
        Page<CheckIn> page = checkInService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/check-ins");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /check-ins/:id : get the "id" checkIn.
     *
     * @param id the id of the checkIn to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the checkIn, or with status 404 (Not Found)
     */
    @GetMapping("/check-ins/{id}")
    @Timed
    public ResponseEntity<CheckIn> getCheckIn(@PathVariable Long id) {
        log.debug("REST request to get CheckIn : {}", id);
        Optional<CheckIn> checkIn = checkInService.findOne(id);
        return ResponseUtil.wrapOrNotFound(checkIn);
    }

    /**
     * DELETE  /check-ins/:id : delete the "id" checkIn.
     *
     * @param id the id of the checkIn to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/check-ins/{id}")
    @Timed
    public ResponseEntity<Void> deleteCheckIn(@PathVariable Long id) {
        log.debug("REST request to delete CheckIn : {}", id);
        checkInService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
