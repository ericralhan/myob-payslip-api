package au.com.myob.payroll.common.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.time.Instant;
import java.time.Month;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;

import static au.com.myob.payroll.common.MYOBConstants.INT_1;

public class DateUtils {

    /**
     * Assumption: startDate and endDate are of same month and year
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static boolean isWholeCalendarMonth(Date startDate, Date endDate) {

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(startDate);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(endDate);

        return (cal1.get(Calendar.DAY_OF_MONTH) == INT_1) && (cal2.get(Calendar.DAY_OF_MONTH) == cal2.getActualMaximum
                (Calendar.DAY_OF_MONTH));
    }

    public static boolean isDatesLieInSameMonth(Date date1, Date date2) {

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        return cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
/*
        Instant instant1 = date1.toInstant();
        Instant instant2 = date2.toInstant();

        return Month.from(instant1) == Month.from(instant2) && Year.from(instant1).equals(Year.from(instant2));
*/
    }

    public static Date getLastDateOfThisMonth(Date date) {
        // first day of next month
        Date date1 = org.apache.commons.lang3.time.DateUtils.ceiling(date, Calendar.MONTH);
        // subtract a day
        date1 = org.apache.commons.lang3.time.DateUtils.addDays(date1, -1);
        return date1;
    }

    public static Date getFirstDateOfNextMonth(Date date) {
        // first day of next month
        return org.apache.commons.lang3.time.DateUtils.ceiling(date, Calendar.MONTH);
    }

    public static long getDayOfMonth(Date date) {

        Calendar dateCal = Calendar.getInstance();
        dateCal.setTime(date);

        return (long) dateCal.get(Calendar.DAY_OF_MONTH);
    }

    public static long getLastDayOfMonth(Date date) {

        Calendar dateCal = Calendar.getInstance();
        dateCal.setTime(date);

        return (long) dateCal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
}
