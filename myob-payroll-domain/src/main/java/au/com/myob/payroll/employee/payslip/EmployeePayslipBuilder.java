package au.com.myob.payroll.employee.payslip;

public class EmployeePayslipBuilder {
    private String name;
    private String payPeriod;
    private long grossIncome;
    private long incomeTax;
    private long netIncome;
    private long _super;

    public EmployeePayslipBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public EmployeePayslipBuilder setPayPeriod(String payPeriod) {
        this.payPeriod = payPeriod;
        return this;
    }

    public EmployeePayslipBuilder setGrossIncome(long grossIncome) {
        this.grossIncome = grossIncome;
        return this;
    }

    public EmployeePayslipBuilder setIncomeTax(long incomeTax) {
        this.incomeTax = incomeTax;
        return this;
    }

    public EmployeePayslipBuilder setNetIncome(long netIncome) {
        this.netIncome = netIncome;
        return this;
    }

    public EmployeePayslipBuilder setSuper(long _super) {
        this._super = _super;
        return this;
    }

    public EmployeePayslip createEmployeePayslip() {
        return new EmployeePayslip(name, payPeriod, grossIncome, incomeTax, netIncome, _super);
    }
}