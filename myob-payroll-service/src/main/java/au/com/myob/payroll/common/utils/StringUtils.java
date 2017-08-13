package au.com.myob.payroll.common.utils;

import au.com.myob.payroll.common.exception.MYOBPartialDataException;

public class StringUtils {

    public static String checkEmptyStringAndTrim(String str) throws MYOBPartialDataException {

        if (org.apache.commons.lang3.StringUtils.isBlank(str))
            throw new MYOBPartialDataException("commaSeparated String is empty");

        return str.trim();
    }
}
