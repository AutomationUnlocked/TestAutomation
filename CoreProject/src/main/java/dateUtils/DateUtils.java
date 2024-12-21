package dateUtils;


import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class DateUtils {


    public static String dtFormatwithoutms = "dd-MM-yyyy HH:mm:ss";
    public static String sqlDateFormat = "yyyy-MM-dd HH:mm:ss";
    public static String normalDate = "yyyy/M/dd";
    public static String formatSingleDate = "yyyy/M/d";

    public static String getTimestamp() {

        String dateTime;
        try {
            DateFormat dateFormat = new SimpleDateFormat(dtFormatwithoutms);
            Date date = new Date();
            return dateTime = dateFormat.format(date);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    public static String getDate() {

        String dateTime;
        try {
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            Date date = new Date();
            return dateTime = dateFormat.format(date);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static String getDate(int Days) {
        try {
            Calendar c = new GregorianCalendar();
            c.add(Calendar.DATE, Days);
            Date date = c.getTime();
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            return dateFormat.format(date);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static String getFormattedDate(String pattern) {

        String dateTime = "";
        try {
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            dateTime = dateFormat.format(date);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateTime;
    }


    public static String getTimeStamp() {

        String dateTime;
        try {
            DateFormat dateFormat = new SimpleDateFormat(dtFormatwithoutms);
            Date date = new Date();
            dateTime = dateFormat.format(date);
            dateTime = dateTime.replaceAll(":", "_").replaceAll(" ", "_");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return dateTime;
    }

    public static long getEpochTime() {
        long dateTime;
        try {
            DateFormat dateFormat = new SimpleDateFormat(dtFormatwithoutms);
            Date date = new Date();
            dateTime = date.getTime();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return dateTime;
    }

    public static String getTime() {

        String dateTime;
        try {
            DateFormat dateFormat = new SimpleDateFormat(dtFormatwithoutms);
            Date date = new Date();
            dateTime = dateFormat.format(date);
            dateTime = dateTime.replaceAll("\\W", "");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return dateTime;
    }

    public static String getTimeWithMilli() {

        String dateTime;
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date date = new Date();
            dateTime = dateFormat.format(date);
            dateTime = dateTime.replaceAll("\\W", "");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return dateTime;
    }

    public static String getRandomDateInYear(int year) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.set(gc.YEAR, year);
        int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));
        gc.set(gc.DAY_OF_YEAR, dayOfYear);
        Date date = gc.getTime();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return dateFormat.format(date);

    }

    private static int randBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }

    public static String getSimpleDateFromMilliSeconds(long milliSeconds) {

        DateFormat dateFormat = new SimpleDateFormat(sqlDateFormat);
        Date date = new Date(milliSeconds);
        return dateFormat.format(date);

    }


    public static String getMonthName(int number) {
        return new DateFormatSymbols().getMonths()[number - 1];
    }

    public static LocalDate getLocalDate(String dateTime, String format) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(format);
        return LocalDate.parse(dateTime, dateFormatter);
    }

}
