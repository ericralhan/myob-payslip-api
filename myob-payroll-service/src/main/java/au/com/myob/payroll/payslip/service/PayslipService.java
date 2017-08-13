package au.com.myob.payroll.payslip.service;

import java.util.Date;

public interface PayslipService {

    String calculatePayPeriod(Date paymentStartDate, Date paymentEndDate);

    String calculateName(String firstName, String lastName);

    double calculateProrataFactor(Date paymentStartDate, Date paymentEndDate);

    double calculateGrossIncome(long annualSalary);

    long calculateSuper(long grossIncome, double superRate);

    long calculateNetIncome(long grossIncome, long incomeTax);

}
