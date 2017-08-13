package au.com.myob.payroll.employee.payslip;

public class EmployeePayslip {

    private static final String COMMA_SEPARATOR = ",";
    private static final String NEW_LINE = "\n";
    private String name;
    private String payPeriod;
    private long grossIncome;
    private long incomeTax;
    private long netIncome;
    private long _super;

    EmployeePayslip(String name, String payPeriod, long grossIncome, long incomeTax, long netIncome, long
            _super) {
        this.name = name;
        this.payPeriod = payPeriod;
        this.grossIncome = grossIncome;
        this.incomeTax = incomeTax;
        this.netIncome = netIncome;
        this._super = _super;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPayPeriod() {
        return payPeriod;
    }

    public void setPayPeriod(String payPeriod) {
        this.payPeriod = payPeriod;
    }

    public long getGrossIncome() {
        return grossIncome;
    }

    public void setGrossIncome(long grossIncome) {
        this.grossIncome = grossIncome;
    }

    public long getIncomeTax() {
        return incomeTax;
    }

    public void setIncomeTax(long incomeTax) {
        this.incomeTax = incomeTax;
    }

    public long getNetIncome() {
        return netIncome;
    }

    public void setNetIncome(long netIncome) {
        this.netIncome = netIncome;
    }

    public long getSuper() {
        return _super;
    }

    public void setSuper(long _super) {
        this._super = _super;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmployeePayslip)) return false;

        EmployeePayslip that = (EmployeePayslip) o;

        if (getGrossIncome() != that.getGrossIncome()) return false;
        if (getIncomeTax() != that.getIncomeTax()) return false;
        if (getNetIncome() != that.getNetIncome()) return false;
        if (getSuper() != that.getSuper()) return false;
        if (!getName().equals(that.getName())) return false;
        return getPayPeriod().equals(that.getPayPeriod());
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getPayPeriod().hashCode();
        result = 31 * result + (int) (getGrossIncome() ^ (getGrossIncome() >>> 32));
        result = 31 * result + (int) (getIncomeTax() ^ (getIncomeTax() >>> 32));
        result = 31 * result + (int) (getNetIncome() ^ (getNetIncome() >>> 32));
        result = 31 * result + (int) (getSuper() ^ (getSuper() >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "EmployeePayslip{" +
                "name:'" + name + '\'' +
                ", payPeriod:'" + payPeriod + '\'' +
                ", grossIncome:" + grossIncome +
                ", incomeTax:" + incomeTax +
                ", netIncome:" + netIncome +
                ", super:" + _super +
                '}';
    }

    public String toCSVString() {
        return name + COMMA_SEPARATOR +
                payPeriod + COMMA_SEPARATOR +
                grossIncome + COMMA_SEPARATOR +
                incomeTax + COMMA_SEPARATOR +
                netIncome + COMMA_SEPARATOR +
                _super + NEW_LINE;
    }
}
