package au.com.myob.payroll.incometax.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IncomeTaxServiceImplTest {

    private IncomeTaxServiceImpl incomeTaxService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void calculateIncomeTax_caseZeroSalary() throws Exception {

        //Arrange
        incomeTaxService = new IncomeTaxServiceImpl();
        long salary = 0;
        //Act
        double tax = incomeTaxService.calculateIncomeTax(salary);
        //Assert
        assertEquals(0.0, tax, 0);
    }

    @Test
    public void calculateIncomeTax_caseDreamSalary() throws Exception {

        //Arrange
        incomeTaxService = new IncomeTaxServiceImpl();
        long salary = 8999999999999999999L;
        //Act
        double tax = incomeTaxService.calculateIncomeTax(salary);
        //Assert
        assertEquals(3.3749999999999782E17, tax, 0);
    }

    @Test
    public void calculateIncomeTax_caseSalary180001() throws Exception {

        //Arrange
        incomeTaxService = new IncomeTaxServiceImpl();
        long salary = 180001;
        //Act
        double tax = incomeTaxService.calculateIncomeTax(salary);
        //Assert
        assertEquals(4545.620833333333, tax, 0);
    }

    @Test
    public void calculateIncomeTax_caseSalary80001() throws Exception {

        //Arrange
        incomeTaxService = new IncomeTaxServiceImpl();
        long salary = 80001;
        //Act
        double tax = incomeTaxService.calculateIncomeTax(salary);
        //Assert
        assertEquals(1462.2808333333332, tax, 0);
    }

    @Test
    public void calculateIncomeTax_caseSalary37001() throws Exception {

        //Arrange
        incomeTaxService = new IncomeTaxServiceImpl();
        long salary = 37001;
        //Act
        double tax = incomeTaxService.calculateIncomeTax(salary);
        //Assert
        assertEquals(297.69374999999997, tax, 0);
    }

    @Test
    public void calculateIncomeTax_caseSalary18201() throws Exception {

        //Arrange
        incomeTaxService = new IncomeTaxServiceImpl();
        long salary = 18201;
        //Act
        double tax = incomeTaxService.calculateIncomeTax(salary);
        //Assert
        assertEquals(0.015833333333333335, tax, 0);
    }

    @Test
    public void calculateIncomeTax_caseSalary10000() throws Exception {

        //Arrange
        incomeTaxService = new IncomeTaxServiceImpl();
        long salary = 10000;
        //Act
        double tax = incomeTaxService.calculateIncomeTax(salary);
        //Assert
        assertEquals(0, tax, 0);
    }

}