package au.com.myob.payroll.payslip.chain;

import au.com.myob.payroll.employee.details.EmployeeDetails;

import java.util.List;

import static au.com.myob.payroll.common.utils.DateUtils.isWholeCalendarMonth;

/**
 * CalendarMonthHandler sets wholeMonth flag
 */
public class CalendarMonthHandler extends EmployeeDetailsRecordHandler {

    public CalendarMonthHandler(EmployeeDetailsRecordHandler handler) {
        super(handler);
    }

    @Override
    public List<EmployeeDetails> handle(List<EmployeeDetails> empRecords) {

        List<EmployeeDetails> treatedRecords = super.handle(empRecords);

        treatedRecords.forEach(this::handle);

        return treatedRecords;
    }

    private EmployeeDetails handle(EmployeeDetails singleRecord) {

        singleRecord.setWholeMonth(isWholeCalendarMonth(singleRecord
                .getPaymentStartDate(), singleRecord.getPaymentEndDate()));

        return singleRecord;
    }

}
