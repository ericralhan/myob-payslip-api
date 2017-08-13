package au.com.myob.payroll.common.utils;

import au.com.myob.payroll.employee.payslip.EmployeePayslip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class EmployeeDetailsCSVWriter {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeDetailsCSVWriter.class);

    public static void writeCSV(List<EmployeePayslip> payslipList, String outputCSVFilePath) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputCSVFilePath, false))) {

            for (EmployeePayslip payslip : payslipList) {

                bw.write(payslip.toCSVString());
            }
            LOGGER.debug("file written successfully");

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

}
