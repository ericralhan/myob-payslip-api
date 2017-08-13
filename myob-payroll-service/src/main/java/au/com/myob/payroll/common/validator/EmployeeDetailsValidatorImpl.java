package au.com.myob.payroll.common.validator;

import au.com.myob.payroll.common.exception.MYOBEmployeeDetailsValidationException;
import au.com.myob.payroll.employee.details.EmployeeDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static au.com.myob.payroll.common.MYOBConstants.*;

/**
 * Assumptions:
 * 1. Tax rates did not change since July 2012
 * 2. Tax rates before July 2012 are not available
 * 3. Super = 0 is acceptable
 */
@Service
public class EmployeeDetailsValidatorImpl implements EmployeeDetailsValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeDetailsValidatorImpl.class);

    @Override
    public List<EmployeeDetails> validate(List<EmployeeDetails> allRecords) throws
            MYOBEmployeeDetailsValidationException {

        Date JULY_01_2012;
        try {
            JULY_01_2012 = new SimpleDateFormat(DATE_PATTERN_DD_MM_YYYY).parse(DATE_STRING_01_07_2012);
        } catch (ParseException e) {
            LOGGER.error("Date parse exception");
            throw new MYOBEmployeeDetailsValidationException("Date parse exception");
        }

        List<EmployeeDetails> validRecords = new ArrayList<>();

        // TODO catch exceptions if any
        for (EmployeeDetails singleRec : allRecords) {
            // dates before july 2012 are invalid - reason, we don't have tax rates for that period.
            // with the default date_format=dd MMMM, this validation is not required.

            try {

                LOGGER.info(Validator.of(singleRec).validate(EmployeeDetails::getPaymentStartDate, date -> date
                        .after(JULY_01_2012), "Date before July 2012")
                        .validate(EmployeeDetails::getPaymentStartDate, date -> date.before(new Date()), "Start " +
                                "Date is in future")
                        .validate(EmployeeDetails::getPaymentEndDate, date -> date.before(new Date()), "End Date " +
                                "is in future")
                        .validate(EmployeeDetails::getAnnualSalary, salary -> salary > LONG_0, "Salary cannot be " +
                                "zero or negative")
                        .validate(EmployeeDetails::getSuperRate, superRate -> superRate >= 0 && superRate <= LONG_50,
                                "Salary cannot be zero or negative")
                        .get().toString());
            } catch (Exception e) {
                LOGGER.warn("record skipped due to validation exception");
                continue;
            }

            // start date must not be more than end date
            if (singleRec.getPaymentStartDate().after(singleRec.getPaymentEndDate())) {
                continue;
            }

            validRecords.add(singleRec);
        }
        if (validRecords.size() == LONG_0) {
            LOGGER.error("no valid records found");
            throw new MYOBEmployeeDetailsValidationException("No valid records found");
        }
        LOGGER.debug("validations were successful");
        return validRecords;
    }
}
