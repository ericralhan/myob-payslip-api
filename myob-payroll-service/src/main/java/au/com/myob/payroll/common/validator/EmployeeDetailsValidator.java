package au.com.myob.payroll.common.validator;

import au.com.myob.payroll.common.exception.MYOBEmployeeDetailsValidationException;
import au.com.myob.payroll.employee.details.EmployeeDetails;

import java.util.List;

public interface EmployeeDetailsValidator {

    List<EmployeeDetails> validate(List<EmployeeDetails> allRecords) throws
            MYOBEmployeeDetailsValidationException;
}
