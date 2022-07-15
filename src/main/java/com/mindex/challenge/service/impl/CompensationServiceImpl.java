package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import com.mongodb.BasicDBObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class CompensationServiceImpl implements CompensationService {

    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    @Autowired
    private CompensationRepository compensationRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Compensation create(Compensation compensation) {
        String id = compensation.getEmployeeId();
        LOG.debug("Creating employee [{}]", compensation);

        Employee employee = employeeRepository.findByEmployeeId(id);
        if (employee == null) { // fail to create compensation if employee doesn't exist
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invalid employeeId: " + id);
        }
        Compensation existingCompensation = compensationRepository.findByEmployeeId(id);
        if(existingCompensation != null) {
            LOG.info("Compensation already set for employee id [{}]", id);
            return compensation; // This case is not explained but seems to be the best option since only 2 endpoints are needed supposedly (GET and POST).
        }
        String effectiveDate = new Timestamp(System.currentTimeMillis()).toString();
        compensation.setEffectiveDate(effectiveDate);
        compensationRepository.insert(compensation);

        return compensation;
    }

    @Override
    public Compensation read(String id) {
        LOG.debug("Creating compensation with employee id [{}]", id);

        Compensation compensation = compensationRepository.findByEmployeeId(id);

        if (compensation == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invalid employeeId: " + id);
        }
        //TODO: Date is stored in server timezone, so we need to convert it to vendor timezone
        return compensation;
    }
}
