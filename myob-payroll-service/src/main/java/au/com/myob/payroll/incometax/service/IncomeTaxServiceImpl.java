package au.com.myob.payroll.incometax.service;

import org.springframework.stereotype.Service;

import static au.com.myob.payroll.common.MYOBConstants.*;

@Service
public class IncomeTaxServiceImpl implements IncomeTaxService {

    @Override
    public double calculateIncomeTax(long annualSalary) {

        double taxPerAnnum;

        if (annualSalary > LONG_180000) {

            taxPerAnnum = LONG_54547 + DOUBLE_0_45 * (annualSalary - LONG_180000);
            return taxPerAnnum / DOUBLE_12;
        }

        if (annualSalary > LONG_80000) {

            taxPerAnnum = LONG_17547 + DOUBLE_0_37 * (annualSalary - LONG_80000);
            return taxPerAnnum / DOUBLE_12;
        }

        if (annualSalary > LONG_37000) {

            taxPerAnnum = LONG_3572 + DOUBLE_0_325 * (annualSalary - LONG_37000);
            return taxPerAnnum / DOUBLE_12;
        }

        if (annualSalary > LONG_18200) {

            taxPerAnnum = DOUBLE_0_19 * (annualSalary - LONG_18200);
            return taxPerAnnum / DOUBLE_12;
        }
        return DOUBLE_ZERO;
    }
}
