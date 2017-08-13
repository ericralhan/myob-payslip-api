package au.com.myob.payroll.payslip.chain;

import au.com.myob.payroll.employee.details.EmployeeDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * EmployeeDetailsRecordHandler
 */
public abstract class EmployeeDetailsRecordHandler {

    private EmployeeDetailsRecordHandler next;

    public EmployeeDetailsRecordHandler(EmployeeDetailsRecordHandler next) {
        this.next = next;
    }

    /**
     * Handle records - default handler
     */
    public List<EmployeeDetails> handle(List<EmployeeDetails> empRecords) {

        if (next != null) {
            return next.handle(empRecords);
        }
        return new ArrayList<>();
    }
}
