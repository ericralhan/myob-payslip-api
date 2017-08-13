package au.com.myob.payroll.employee.details;

import java.util.Date;

public class EmployeeDetails {

    private String firstName;
    private String lastName;
    private long annualSalary;
    private double superRate;
    private Date paymentStartDate;
    private Date paymentEndDate;
    private boolean isWholeMonth;

    public EmployeeDetails(String firstName, String lastName, long annualSalary, double superRate, Date
            paymentStartDate, Date paymentEndDate, boolean isWholeMonth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.annualSalary = annualSalary;
        this.superRate = superRate;
        this.paymentStartDate = paymentStartDate;
        this.paymentEndDate = paymentEndDate;
        this.isWholeMonth = isWholeMonth;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getAnnualSalary() {
        return annualSalary;
    }

    public void setAnnualSalary(long annualSalary) {
        this.annualSalary = annualSalary;
    }

    public double getSuperRate() {
        return superRate;
    }

    public void setSuperRate(double superRate) {
        this.superRate = superRate;
    }

    public Date getPaymentStartDate() {
        return paymentStartDate;
    }

    public void setPaymentStartDate(Date paymentStartDate) {
        this.paymentStartDate = paymentStartDate;
    }

    public Date getPaymentEndDate() {
        return paymentEndDate;
    }

    public void setPaymentEndDate(Date paymentEndDate) {
        this.paymentEndDate = paymentEndDate;
    }

    public boolean isWholeMonth() {
        return isWholeMonth;
    }

    public void setWholeMonth(boolean wholeMonth) {
        isWholeMonth = wholeMonth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmployeeDetails)) return false;

        EmployeeDetails that = (EmployeeDetails) o;

        if (getAnnualSalary() != that.getAnnualSalary()) return false;
        if (Double.compare(that.getSuperRate(), getSuperRate()) != 0) return false;
        if (isWholeMonth() != that.isWholeMonth()) return false;
        if (!getFirstName().equals(that.getFirstName())) return false;
        if (!getLastName().equals(that.getLastName())) return false;
        if (!getPaymentStartDate().equals(that.getPaymentStartDate())) return false;
        return getPaymentEndDate().equals(that.getPaymentEndDate());
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getFirstName().hashCode();
        result = 31 * result + getLastName().hashCode();
        result = 31 * result + (int) (getAnnualSalary() ^ (getAnnualSalary() >>> 32));
        temp = Double.doubleToLongBits(getSuperRate());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + getPaymentStartDate().hashCode();
        result = 31 * result + getPaymentEndDate().hashCode();
        result = 31 * result + (isWholeMonth() ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EmployeeDetails{" +
                "firstName:'" + firstName + '\'' +
                ", lastName:'" + lastName + '\'' +
                ", annualSalary:" + annualSalary +
                ", superRate:" + superRate +
                ", paymentStartDate:" + paymentStartDate +
                ", paymentEndDate:" + paymentEndDate +
                ", isWholeMonth:" + isWholeMonth +
                '}';
    }
}
