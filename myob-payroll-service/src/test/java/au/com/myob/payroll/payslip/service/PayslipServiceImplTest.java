package au.com.myob.payroll.payslip.service;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static au.com.myob.payroll.common.MYOBConstants.DATE_PATTERN_DD_MM_YYYY;
import static au.com.myob.payroll.common.MYOBConstants.DATE_STRING_01_07_2012;
import static org.junit.Assert.assertEquals;

public class PayslipServiceImplTest {

    private PayslipServiceImpl payslipService = new PayslipServiceImpl();

    @Test
    public void calculateSuper_LargeValues() throws Exception {
        //Act
        long superRate = payslipService.calculateSuper(8999999999999999999L,
                9999999999999999999999999999999999999999999999999999999999999.0);
        //Assert
        assertEquals(9.223372036854776E18, superRate, 0);
    }

    @Test
    public void calculateSuper_Zero() throws Exception {
        //Act
        long superRate = payslipService.calculateSuper(0, 0);
        //Assert
        assertEquals(0.0, superRate, 0);
    }

    @Test
    public void calculateNetIncome_Zero() throws Exception {
        //Act
        long netIncome = payslipService.calculateNetIncome(8999999999999999999L, 8999999999999999999L);
        //Assert
        assertEquals(0, netIncome, 0);
    }

    @Test
    public void calculateNetIncome_Zero2() throws Exception {
        //Act
        long netIncome = payslipService.calculateNetIncome(0, 0);
        //Assert
        assertEquals(0, netIncome, 0);
    }

    @Test
    public void calculateNetIncome_LargePositive() throws Exception {
        //Act
        long netIncome = payslipService.calculateNetIncome(8999999999999999999L, 0);
        //Assert
        assertEquals(8999999999999999999L, netIncome, 0);
    }

    @Test
    public void calculateNetIncome_LargeNegative() throws Exception {
        //Act
        long netIncome = payslipService.calculateNetIncome(0, 8999999999999999999L);
        //Assert
        assertEquals(-8999999999999999999L, netIncome, 0);
    }

    @Test
    public void calculateProrataFactor_ForOneDay() throws Exception {
        //Act
        // 1470960000000L = August 12, 2016 12:00:00 AM
        double prorataFactor = payslipService.calculateProrataFactor(new Date(1470960000000L), new Date
                (1470960000000L));
        //Assert
        assertEquals(0.03225806451612903, prorataFactor, 0);
    }

    @Test
    public void calculateProrataFactor_ForDifferentMonths() throws Exception {
        //Arrange
        Date date1 = new SimpleDateFormat(DATE_PATTERN_DD_MM_YYYY).parse(DATE_STRING_01_07_2012);
        // 1470960000000L = August 12, 2016 12:00:00 AM
        Date date2 = new Date(1470960000000L);
        //Act
        double prorataFactor = payslipService.calculateProrataFactor(date1, date2);
        //Assert
        assertEquals(0.3870967741935484, prorataFactor, 0);
    }

    @Test
    public void calculateGrossIncome_LargeValue() throws Exception {
        //Act
        double grossIncome = payslipService.calculateGrossIncome(8999999999999999999L);
        //Assert
        assertEquals(7.5E17, grossIncome, 0);
    }

    @Test
    public void calculateGrossIncome_Zero() throws Exception {
        //Act
        double grossIncome = payslipService.calculateGrossIncome(0);
        //Assert
        assertEquals(0, grossIncome, 0);
    }

    @Test
    public void calculatePayPeriod() throws Exception {
        //Arrange
        Date date1 = new SimpleDateFormat(DATE_PATTERN_DD_MM_YYYY).parse(DATE_STRING_01_07_2012);
        // 1470960000000L = August 12, 2016 12:00:00 AM
        Date date2 = new Date(1470960000000L);
        //Act
        String payPeriod = payslipService.calculatePayPeriod(date1, date2);
        //Assert
        assertEquals("01 July - 12 August", payPeriod);
    }

    @Test
    public void calculateName() throws Exception {
        //Act
        String name = payslipService.calculateName("", "");
        //Assert
        assertEquals(" ", name);
    }

}