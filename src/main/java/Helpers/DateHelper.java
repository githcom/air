package Helpers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import org.apache.commons.lang3.time.DateUtils;

public class DateHelper {

    public static final String DD_MM_YYYY_SLASH = "dd/MM/yyyy";
    public static final String MM_DD_YYYY_SLASH = "MM/dd/yyyy";
    public static final String DD_MM_YYYY_DOTS = "DD.MM.YYYY";
    public static final String DD_MM_YYYY_DOTS2 = "dd.MM.yyyy";

    public static String addToDate(int addDays) {
        return addToDate(addDays, DD_MM_YYYY_SLASH);
    }

    public static String addToDate(int addDays, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, addDays);
        return dateFormat.format(cal.getTime());
    }

    public static String today() {
        return addToDate(0);
    }

    public static String parseDate(String dateVal, String format) {
        String res = "";
        String[] dateArr = dateVal.split("/");
        if (format.equals(DateHelper.MM_DD_YYYY_SLASH)) {
            res = dateArr[1] + "/" + dateArr[0] + "/" + dateArr[2];
        }
        if (format.equals(DD_MM_YYYY_DOTS)) {
            res = dateArr[0] + "." + dateArr[1] + "." + dateArr[2];
        }
        return res;
    }

    //    public static String getDateTime() {
//        Date date = Calendar.getInstance().getTime();
//        // Display a date in day, month, year format
//        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy_HH_mm_ss");
//        String today = formatter.format(date);
//        return today;
//    }
    public static int getDifferenceInDays(String d1, String d2, String format) {
        Date date1 = parseString2Date(d1, format);
        Date date2 = parseString2Date(d2, format);
        return (int) (date2.getTime() - date1.getTime()) / (1000 * 3600 * 24);
    }

    public static int getDifferenceInDaysFromToday(String date, String format) {
        Date date2 = parseString2Date(date, format);
        Date truncatedDate = DateUtils.truncate(new Date(), Calendar.DATE);
        return (int) (date2.getTime() - truncatedDate.getTime()) / (1000 * 3600 * 24);
    }

    //    format
//    Pattern	Example
//    dd-MM-yy	31-01-12
//    dd-MM-yyyy	31-01-2012
//    MM-dd-yyyy	01-31-2012
//    yyyy-MM-dd	2012-01-31
//    yyyy-MM-dd HH:mm:ss	2012-01-31 23:59:59
//    yyyy-MM-dd HH:mm:ss.SSS	2012-01-31 23:59:59.999
//    yyyy-MM-dd HH:mm:ss.SSSZ	2012-01-31 23:59:59.999+0100
//    EEEEE MMMMM yyyy HH:mm:ss.SSSZ	Saturday November 2012 10:45:42.720+0100
    public static Date parseString2Date(String dateString, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date date = new Date();
        try {
            date = formatter.parse(dateString);
        } catch (ParseException e) {
            Report.verifyValue("parseString2Date: Wrong date format ", true, false);
        }
        return date;
    }

    /**
     * @param timeAgo - should be string, for example "21 min 57 sec" or "4
     * weeks 6 days"
     * @return - result will be a time in seconds
     */
    public static int parseTimeAgo(String timeAgo) {
        int week, day, hour, min, sec;
        week = day = hour = min = sec = 0;
        String[] splitedTimeAgo = timeAgo.split("\\s+");
        for (int index = 1; index < splitedTimeAgo.length; index += 2) {
            int tmp = Integer.parseInt(splitedTimeAgo[index - 1]);
            switch (splitedTimeAgo[index]) {
                case "week":
                case "weeks":
                    week = tmp;
                    break;
                case "day":
                case "days":
                    day = tmp;
                    break;
                case "hour":
                case "hours":
                    hour = tmp;
                    break;
                case "min":
                    min = tmp;
                    break;
                case "sec":
                    sec = tmp;
                    break;
            }
        }
        return sec + min * 60 + hour * 3600 + day * 24 * 3600 + week * 7 * 24 * 3600;
    }

    public static LocalDateTime getLocalDateTimeNowMinusSeconds(long sec) {
        return LocalDateTime.now().minusSeconds(sec);
    }
}
