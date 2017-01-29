package au.com.unisharing.eztutor.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Class Name   :
 * Author       :
 * Created Date :
 * Description  :
 */

public class DateUtils {
    public static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy.MM.dd", Locale.CHINA);
    public static final Calendar CALENDAR = Calendar.getInstance(Locale.CHINA);
    private static Date date = new Date();

    private DateUtils() {

    }

    public static Date getDate() {
        return date;
    }

    public static Date refresh(){
        return new Date();
    }

    public static String getDateString(int field,int value){
        CALENDAR.add(field,value);
        String dateString = FORMAT.format(CALENDAR.getTime());
        CALENDAR.add(field,-value);
        return  dateString;
    }

}
