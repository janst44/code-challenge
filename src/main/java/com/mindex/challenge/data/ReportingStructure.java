package com.mindex.challenge.data;

public class ReportingStructure {
    private String employeeId;
    private int numReports;

    public ReportingStructure(String employeeId, int numReports) {
        this.employeeId = employeeId;
        this.numReports = numReports;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public int getNumReports() {
        return numReports;
    }

    public void setNumReports(int numReports) {
        this.numReports = numReports;
    }
}
