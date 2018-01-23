package br.liveo.hotelbook.hotelbook.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by phx on 2016/1/28.
 */
public class ToolUtils {

    public String dateToString(Date date, String format){
        DateFormat df = new SimpleDateFormat(format);
        String reportDate = df.format(date);
        return reportDate;
    }

    public Date ConvertToDate(String dateString, String format){
        SimpleDateFormat curFormater = new SimpleDateFormat(format);
        Date dateObj = null;
        try {
            dateObj = curFormater.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateObj);
        return calendar.getTime();

    }
}
