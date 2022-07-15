package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
public class ReportingServiceImpl implements ReportingService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportingServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public ReportingStructure read(String id) {
        LOG.debug("Getting reporting structure for employee id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invalid employeeId: " + id);
        }

        return computeReportingStructure(employee);
    }

    private ReportingStructure computeReportingStructure(Employee employee) {
        String employeeId = employee.getEmployeeId();
        int reportCount = computeReportingStructureRecursive(employee, 0);
        return new ReportingStructure(employeeId, reportCount);
    }

    private int computeReportingStructureRecursive(Employee employee, int reportCount) {
        List<Employee> directReports = employee.getDirectReports();
        if(directReports == null) {
            return reportCount;
        }
        for(Employee emp: directReports) {
            Employee realEmp = employeeRepository.findByEmployeeId(emp.getEmployeeId());
            reportCount = computeReportingStructureRecursive(realEmp, reportCount) + 1;
        }
        return reportCount;
    }
}
