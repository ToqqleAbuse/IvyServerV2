package system.tools.skills;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeManager{

    public static final long SECOND = 1000;

    public static final long MINUTE = SECOND * 60;

    public static final long HOUR = MINUTE * 60;

    public static final long DAY = HOUR * 24;

    public static final long WEEK = DAY * 7;

    public static final long MONTH = DAY * 30;

    public static final long YEAR = DAY * 365;

    private long timestamp;

    public TimeManager(final long timestamp){
        this.timestamp = timestamp;
    }

    public String getDateFormat(final String dateFormat){
        Date date = new Date(this.timestamp);
        DateFormat format = new SimpleDateFormat(dateFormat);
        return format.format(date);
    }

    public void setTimestamp(final long newTimestamp){
        this.timestamp = newTimestamp;
    }

    public long getTimestamp(){
        return this.timestamp;
    }

    public Calendar getCalendar(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(this.timestamp);
        return calendar;
    }

    public boolean isAM(){
        return (this.getCalendar().get(Calendar.AM_PM) == Calendar.AM);
    }

    public boolean isPM(){
        return (this.getCalendar().get(Calendar.AM_PM) == Calendar.PM);
    }

    public int getYear(){
        return this.getCalendar().get(Calendar.YEAR);
    }

    public int getMonth(){
        return this.getCalendar().get(Calendar.MONTH) + 1;
    }

    public int getDayOfWeekInMonth(){
        return this.getCalendar().get(Calendar.DAY_OF_WEEK_IN_MONTH);
    }

    public int getDayOfWeek(){
        return this.getCalendar().get(Calendar.DAY_OF_MONTH);
    }

    public int getDay(){
        return this.getCalendar().get(Calendar.DAY_OF_MONTH);
    }

    public int getHour(){
        return (this.getCalendar().get(Calendar.HOUR) + (!isAM() ? 12 : 0));
    }

    public int getMinute(){
        return this.getCalendar().get(Calendar.MINUTE);
    }

    public int getSecond(){
        return this.getCalendar().get(Calendar.SECOND);
    }

    public int getMillisecond(){
        return this.getCalendar().get(Calendar.MILLISECOND);
    }

    public TimeManager getLastDayOfMonth(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 0);
        calendar.set(Calendar.MONTH, getMonth());
        calendar.set(Calendar.YEAR, getYear());
        return new TimeManager(calendar.getTime().getTime());
    }

    public TimeManager getFirstDayOfMonth(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.MONTH, getMonth() - 1);
        calendar.set(Calendar.YEAR, getYear());
        return new TimeManager(calendar.getTime().getTime());
    }

    public static TimeManager from_date(final String format, final String date_input){
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            Date date = dateFormat.parse(date_input);
            return new TimeManager(date.getTime());
        }catch(Exception exception){
            exception.printStackTrace();
        }
        return null;
    }


}
