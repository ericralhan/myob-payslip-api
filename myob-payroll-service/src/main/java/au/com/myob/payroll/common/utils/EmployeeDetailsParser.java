package au.com.myob.payroll.common.utils;

import au.com.myob.payroll.common.exception.MYOBParserException;
import au.com.myob.payroll.common.exception.MYOBPartialDataException;
import au.com.myob.payroll.employee.details.EmployeeDetails;
import au.com.myob.payroll.employee.details.EmployeeDetailsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static au.com.myob.payroll.common.MYOBConstants.*;
import static au.com.myob.payroll.common.utils.StringUtils.checkEmptyStringAndTrim;

/**
 * Assumptions:
 * 1. Default date format is dd MMMM yyyy
 * 2. Default year is 2016
 */
public class EmployeeDetailsParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeDetailsParser.class);

    public static List<EmployeeDetails> parseCommaSeparated(String csvFilePath) throws MYOBParserException {

        List<String[]> listOfRawEmployeeRecords = EmployeeDetailsCSVReader.readCSV(csvFilePath);
        boolean defaultDateFormat = false;

        SimpleDateFormat dtFormat;
        if (DATE_FORMAT.equals(DEFAULT_DATE_FORMAT_DD_MMMM)) {
            dtFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT_DD_MMMM + BLANK + YYYY);
            defaultDateFormat = true;
        } else {
            dtFormat = new SimpleDateFormat(DATE_FORMAT);
        }

        List<EmployeeDetails> empDetailsList = new ArrayList<>();

        int i = 0;
        for (String[] singleRawRecord : listOfRawEmployeeRecords) {

            EmployeeDetails empDetailsSingleRecord = new EmployeeDetailsBuilder()
                    .createEmployeeDetailsRecord();
            try {
                empDetailsSingleRecord.setFirstName(checkEmptyStringAndTrim(singleRawRecord[INT_0]));
                empDetailsSingleRecord.setLastName(checkEmptyStringAndTrim(singleRawRecord[INT_1]));
                empDetailsSingleRecord.setAnnualSalary(Long.parseLong(checkEmptyStringAndTrim(singleRawRecord[INT_2])));
                // trim the % character
                empDetailsSingleRecord.setSuperRate(Double.parseDouble(checkEmptyStringAndTrim(singleRawRecord[INT_3])
                        .substring(INT_0, singleRawRecord[INT_3].length() - INT_1)));

                String fifthElementDateRange = singleRawRecord[INT_4];
                if (!checkEmptyStringAndTrim(fifthElementDateRange).contains(STRING_DASH))
                    throw new MYOBPartialDataException("date Range not in expected format");

                String[] startAndEndDate = fifthElementDateRange.split(STRING_DASH);

                if (startAndEndDate.length != INT_2)
                    throw new MYOBParserException("date Range not in expected format");

                Date startDate;
                Date endDate;

                if (defaultDateFormat) {
                    startDate = dtFormat.parse(checkEmptyStringAndTrim(startAndEndDate[INT_0]) + BLANK + DEFAULT_YEAR);
                    endDate = dtFormat.parse(checkEmptyStringAndTrim(startAndEndDate[INT_1]) + BLANK + DEFAULT_YEAR);
                } else {
                    startDate = dtFormat.parse(checkEmptyStringAndTrim(startAndEndDate[INT_0]));
                    endDate = dtFormat.parse(checkEmptyStringAndTrim(startAndEndDate[INT_1]));
                }
                empDetailsSingleRecord.setPaymentStartDate(startDate);
                empDetailsSingleRecord.setPaymentEndDate(endDate);

            } catch (Exception e) {

                LOGGER.error("CSV record parsing failed :: " + i);
                throw new MYOBParserException("CSV record parsing failed :: " + i);
            }
            LOGGER.debug("Parsed - " + empDetailsSingleRecord.toString());
            empDetailsList.add(empDetailsSingleRecord);
            i++;
        }
        LOGGER.debug("file parsed successfully");
        return empDetailsList;
    }
}
