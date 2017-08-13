package au.com.myob.payroll.payslip.chain;

import au.com.myob.payroll.employee.details.EmployeeDetails;
import au.com.myob.payroll.employee.details.EmployeeDetailsBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static au.com.myob.payroll.common.utils.DateUtils.*;

/**
 * MultiMonthHandler divides multi-month records to multiple single month records
 */
public class MultiMonthHandler extends EmployeeDetailsRecordHandler {

    public MultiMonthHandler(EmployeeDetailsRecordHandler handler) {
        super(handler);
    }

    @Override
    public List<EmployeeDetails> handle(List<EmployeeDetails> empRecords) {

        List<EmployeeDetails> treatedRecords = new ArrayList<>();
        EmployeeDetailsBuilder builder = new EmployeeDetailsBuilder();

        for (EmployeeDetails singleRecord : empRecords) {

            Date paymentStartDate = singleRecord.getPaymentStartDate();
            Date paymentEndDate = singleRecord.getPaymentEndDate();

            if (!isDatesLieInSameMonth(paymentStartDate, paymentEndDate)) {

                while (paymentEndDate.compareTo(paymentStartDate) >= 0) {

                    Date lastDateOfMonth = getLastDateOfThisMonth(paymentStartDate);

                    // case of last month with paymentEndDate less than month-last-day
                    if (lastDateOfMonth.after(paymentEndDate)) {
                        lastDateOfMonth = paymentEndDate;
                    }

                    EmployeeDetails newRecord = builder.setAnnualSalary(singleRecord.getAnnualSalary())
                            .setFirstName(singleRecord.getFirstName())
                            .setLastName(singleRecord.getLastName())
                            .setSuperRate(singleRecord.getSuperRate())
                            .setPaymentStartDate(paymentStartDate)
                            .setPaymentEndDate(lastDateOfMonth)
                            .createEmployeeDetailsRecord();

                    treatedRecords.add(newRecord);
                    paymentStartDate = getFirstDateOfNextMonth(paymentStartDate);
                }
            } else {
                treatedRecords.add(singleRecord);
            }
        }
        empRecords = treatedRecords;
        return empRecords;
    }
}
