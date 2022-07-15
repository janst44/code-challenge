package com.mindex.challenge.controller;

import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
public class ReportingController {
    private static final Logger LOG = LoggerFactory.getLogger(ReportingController.class);

    @Autowired
    private ReportingService reportingService;

    @GetMapping("/report/{id}")
    public ResponseEntity<ReportingStructure> read(@PathVariable String id) {
        LOG.debug("Getting ReportStructure for employee id [{}]", id);
        try {
            return new ResponseEntity<>(reportingService.read(id), HttpStatus.OK);
        } catch(HttpClientErrorException e) {
           LOG.info("Failed to retrieve report for employee id[{}] with http status [{}]", id, e.getStatusCode());
           return new ResponseEntity<>(e.getStatusCode());
        } catch(Exception e) {
            LOG.error("Failed to retrieve report for employee id[{}]", id, e);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
