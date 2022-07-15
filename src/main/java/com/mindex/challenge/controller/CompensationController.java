package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
public class CompensationController {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

    @Autowired
    private CompensationService compensationService;

    @PostMapping("/compensation")
    public ResponseEntity<Compensation> create(@RequestBody Compensation compensation) {
        LOG.debug("Received compensation create request for [{}]", compensation);
        try {
            return new ResponseEntity<>(compensationService.create(compensation), HttpStatus.OK);
        } catch(HttpClientErrorException e) {
            LOG.info("Failed to create compensation for employee id [{}] with http status [{}]", compensation.getEmployeeId(), e.getStatusCode());
            return new ResponseEntity<>(e.getStatusCode());
        } catch(Exception e) {
            LOG.error("Failed to create compensation for employee id [{}]", compensation.getEmployeeId(), e);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/compensation/{id}")
    public ResponseEntity<Compensation> read(@PathVariable String id) {
        LOG.debug("Received compensation create request for id [{}]", id);
        try {
            return new ResponseEntity<>(compensationService.read(id), HttpStatus.OK);
        } catch(HttpClientErrorException e) {
            LOG.info("Failed to read compensation for employee id[{}] with http status [{}]", id, e.getStatusCode());
            return new ResponseEntity<>(e.getStatusCode());
        } catch (Exception e) {
            LOG.error("Failed to create compensation for employee id [{}]", id, e);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
