package au.com.myob.payroll.common.utils;

import au.com.myob.payroll.common.exception.MYOBParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class EmployeeDetailsCSVReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeDetailsCSVReader.class);

    private static final String CSV_SPLIT_BY_COMMA = ",";

    static List<String[]> readCSV(String csvFile) throws MYOBParserException {

        List<String[]> employeeDetailsRecordList = new ArrayList<>();
        String line = "";

        try (BufferedReader br = new BufferedReader(new FileReader(new ClassPathResource(csvFile).getFile()))) {

            for (int i = 0; (line = br.readLine()) != null; i++) {
                // use comma as separator
                String[] csvArray = line.split(CSV_SPLIT_BY_COMMA);

                if (csvArray.length != 5) {
                    LOGGER.warn("Skipping CSV record with partial data: " + i);
                    continue;
                }
                employeeDetailsRecordList.add(csvArray);
            }
            LOGGER.debug("CSV File read successful");

        } catch (IOException e) {

            LOGGER.error(e.getMessage());
            throw new MYOBParserException("Error while reading CSV File");
        }
        return employeeDetailsRecordList;
    }
}
