package au.com.myob.payroll.payslip.client;

import au.com.myob.payroll.common.exception.MYOBApplicationException;
import au.com.myob.payroll.common.utils.EmployeeDetailsCSVWriter;
import au.com.myob.payroll.common.utils.EmployeeDetailsParser;
import au.com.myob.payroll.common.validator.EmployeeDetailsValidator;
import au.com.myob.payroll.config.AppConfig;
import au.com.myob.payroll.employee.details.EmployeeDetails;
import au.com.myob.payroll.employee.payslip.EmployeePayslip;
import au.com.myob.payroll.employee.payslip.EmployeePayslipBuilder;
import au.com.myob.payroll.incometax.service.IncomeTaxService;
import au.com.myob.payroll.payslip.chain.EmployeeDetailsRecordHandlerChain;
import au.com.myob.payroll.payslip.service.PayslipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static au.com.myob.payroll.common.MYOBConstants.ENABLE_PRORATA_ADJUSTMENTS;

public class PayslipClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(PayslipClient.class);

    @Autowired
    private EmployeeDetailsValidator validator;

    @Autowired
    private EmployeeDetailsRecordHandlerChain handler;

    @Autowired
    private IncomeTaxService incomeTaxService;

    @Autowired
    private PayslipService payslipService;

    public void execute(String inputCSVFilePath, String outputCSVFilePath) throws MYOBApplicationException {

        List<EmployeeDetails> parsedRecords = EmployeeDetailsParser.parseCommaSeparated(inputCSVFilePath);

        // create spring context
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(AppConfig.class);
        ctx.refresh();

        // initialize spring services and components
        validator = ctx.getBean(EmployeeDetailsValidator.class);
        handler = ctx.getBean(EmployeeDetailsRecordHandlerChain.class);
        incomeTaxService = ctx.getBean(IncomeTaxService.class);
        payslipService = ctx.getBean(PayslipService.class);

        List<EmployeeDetails> validRecords = validator.validate(parsedRecords);

        List<EmployeeDetails> serviceableRecordsRecords = handler.makeServiceableRecords(validRecords);

        List<EmployeePayslip> payslipRecords = generatePayslips(serviceableRecordsRecords);

        EmployeeDetailsCSVWriter.writeCSV(payslipRecords, outputCSVFilePath);

        LOGGER.debug("Payslips generated successfully");
    }

    private List<EmployeePayslip> generatePayslips(List<EmployeeDetails> empRecords) {

        List<EmployeePayslip> payslipRecords = new ArrayList<>();

        for (EmployeeDetails record : empRecords) {

            EmployeePayslip payslip = handleSingleRecord(record);
            payslipRecords.add(payslip);

            LOGGER.debug("Payslip record generated - " + payslip.toString());
        }
        return payslipRecords;
    }

    private EmployeePayslip handleSingleRecord(EmployeeDetails empRecord) {

        EmployeePayslipBuilder payslipBuilder = new EmployeePayslipBuilder();

        double doubleGrossIncome = payslipService.calculateGrossIncome(empRecord.getAnnualSalary());
        double doubleIncomeTax = incomeTaxService.calculateIncomeTax(empRecord.getAnnualSalary());

        if (ENABLE_PRORATA_ADJUSTMENTS && !empRecord.isWholeMonth()) {
            double prorataFactor = payslipService.calculateProrataFactor(empRecord.getPaymentStartDate(), empRecord
                    .getPaymentEndDate());
            doubleIncomeTax *= prorataFactor;
            doubleGrossIncome *= prorataFactor;
        }

        // make payslipRecord
        long longGrossIncome = Math.round(doubleGrossIncome);
        long longIncomeTax = Math.round(doubleIncomeTax);

        return payslipBuilder
                .setName(payslipService.calculateName(empRecord.getFirstName(), empRecord.getLastName()))
                .setPayPeriod(payslipService.calculatePayPeriod(empRecord.getPaymentStartDate(), empRecord
                        .getPaymentEndDate()))
                .setGrossIncome(longGrossIncome)
                .setIncomeTax(longIncomeTax)
                .setNetIncome(payslipService.calculateNetIncome(longGrossIncome, longIncomeTax))
                .setSuper(payslipService.calculateSuper(longGrossIncome, empRecord.getSuperRate()))
                .createEmployeePayslip();
    }
}
