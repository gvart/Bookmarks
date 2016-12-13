package dev.gva.bookmarks.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by pika on 12/13/16.
 */
public class DateParser {

    private final static Logger logger = LoggerFactory.getLogger(DateParser.class);

    public static Date parse(String date){
        logger.debug("Input date: " + date);
        Calendar calendar = new GregorianCalendar();
        int year = Integer.parseInt(date.substring(0,4));
        int month = Integer.parseInt(date.substring(5,7));
        int day = Integer.parseInt(date.substring(8,10));
        int hours = Integer.parseInt(date.substring(11,13));
        int minutes = Integer.parseInt(date.substring(14,15));

        calendar.set(year,month,day,hours,minutes);

        return calendar.getTime();
    }
}
