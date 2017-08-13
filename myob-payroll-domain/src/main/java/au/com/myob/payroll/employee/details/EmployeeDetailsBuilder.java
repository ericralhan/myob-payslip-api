package au.com.myob.payroll.employee.details;

import java.util.Date;

public class EmployeeDetailsBuilder {
    private String firstName;
    private String lastName;
    private long annualSalary;
    private double superRate;
    private Date paymentStartDate;
    private Date paymentEndDate;
    private boolean isWholeMonth;

    public EmployeeDetailsBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public EmployeeDetailsBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public EmployeeDetailsBuilder setAnnualSalary(long annualSalary) {
        this.annualSalary = annualSalary;
        return this;
    }

    public EmployeeDetailsBuilder setSuperRate(double superRate) {
        this.superRate = superRate;
        return this;
    }

    public EmployeeDetailsBuilder setPaymentStartDate(Date paymentStartDate) {
        this.paymentStartDate = paymentStartDate;
        return this;
    }

    public EmployeeDetailsBuilder setPaymentEndDate(Date paymentEndDate) {
        this.paymentEndDate = paymentEndDate;
        return this;
    }

    public EmployeeDetailsBuilder setIsWholeMonth(boolean isWholeMonth) {
        this.isWholeMonth = isWholeMonth;
        return this;
    }

    public EmployeeDetails createEmployeeDetailsRecord() {
        return new EmployeeDetails(firstName, lastName, annualSalary, superRate, paymentStartDate,
                paymentEndDate, isWholeMonth);
    }
}