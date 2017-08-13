package au.com.myob.payroll.payslip.service;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

import static au.com.myob.payroll.common.MYOBConstants.*;
import static au.com.myob.payroll.common.utils.DateUtils.getDayOfMonth;
import static au.com.myob.payroll.common.utils.DateUtils.getLastDayOfMonth;

@Service
public class PayslipServiceImpl implements PayslipService {

    @Override
    public long calculateSuper(long grossIncome, double superRate) {

        double doubleSuper = grossIncome * superRate / LONG_100;
        return Math.round(doubleSuper);
    }

    @Override
    public long calculateNetIncome(long grossIncome, long incomeTax) {
        return grossIncome - incomeTax;
    }

    /**
     * Factor to calculate prorata income, when paymentStartDate is in between a month.
     * Expectation: paymentStartDate and paymentEndDate fall in same month
     *
     * @param paymentStartDate
     * @param paymentEndDate
     * @return
     */
    @Override
    public double calculateProrataFactor(Date paymentStartDate, Date paymentEndDate) {

        double PAY_START_DAY = getDayOfMonth(paymentStartDate);
        double PAY_END_DAY = getDayOfMonth(paymentEndDate);
        double LAST_DAY_OF_MONTH = getLastDayOfMonth(paymentStartDate);

        double NUMBER_OF_PAID_DAYS = PAY_END_DAY - PAY_START_DAY + LONG_1;

        // prorata factor
        return NUMBER_OF_PAID_DAYS / LAST_DAY_OF_MONTH;
    }

    @Override
    public double calculateGrossIncome(long annualSalary) {
        return annualSalary / DOUBLE_12;
    }

    /**
     * Expectation: paymentStartDate and paymentEndDate fall in same month
     *
     * @param paymentStartDate
     * @param paymentEndDate
     * @return
     */
    @Override
    public String calculatePayPeriod(Date paymentStartDate, Date paymentEndDate) {

        SimpleDateFormat sf = new SimpleDateFormat(DATE_FORMAT);

        String startDateString = sf.format(paymentStartDate);
        String endDateString = sf.format(paymentEndDate);

        String payPeriod = startDateString + DATES_SEPARATOR + endDateString;

        return payPeriod;
    }

    @Override
    public String calculateName(String firstName, String lastName) {
        return firstName + BLANK + lastName;
    }

}
