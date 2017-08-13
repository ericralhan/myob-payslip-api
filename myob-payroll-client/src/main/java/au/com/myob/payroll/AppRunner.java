package au.com.myob.payroll;

import au.com.myob.payroll.common.exception.MYOBApplicationException;
import au.com.myob.payroll.payslip.client.PayslipClient;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;


public class AppRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppRunner.class);

    public static void main(String[] args) {

        String inputCSVFile = "employee-details.csv";
        String outputCSVFile = "employee-payslips.csv";

        PayslipClient client = new PayslipClient();

        try {
            client.execute(inputCSVFile, outputCSVFile);

            LOGGER.debug("success");
        } catch (MYOBApplicationException e) {

            LOGGER.error(e.getMessage());
        }
    }
}
