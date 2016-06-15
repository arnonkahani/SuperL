package com.Workers.Objects;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.Calendar;


public class Date {
    private String _day;
    private String _mounth;
    private String _year;
    public static final SimpleDateFormat dateWithTimef = new SimpleDateFormat("dd-MM-yyyy hh:mm");
    public static final SimpleDateFormat datef = new SimpleDateFormat("dd-MM-yyyy");
    public static final SimpleDateFormat dayf = new SimpleDateFormat("dd");
    public static final SimpleDateFormat monthf = new SimpleDateFormat("MM");
    public static final SimpleDateFormat yearf = new SimpleDateFormat("yyyy");
    public static final SimpleDateFormat hourf = new SimpleDateFormat("hh");
    public static final SimpleDateFormat minf = new SimpleDateFormat("mm");
    private final String[] WeekDays = new String[]{"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday", "Saturday"};
    java.util.Date _date;

    String _dayInWeek = "";

    public Date(java.util.Date date) {
        _date = date;
        _day = dayf.format(date);
        _mounth = monthf.format(date);
        _year = yearf.format(date);
        _dayInWeek = WeekDays[numOfDayInWeek(_date)];
    }

    public Date(int day, int mounth, int year) throws DateTimeException, ParseException {
        if (day <= 0 || day > 31 || year < 0 || mounth <= 0 || mounth > 12)
            throw new DateTimeException("Error: invalid Date insert");
        _day = day < 10 ? "0" + day : "" + day;
        _mounth = mounth < 10 ? "0" + mounth : "" + mounth;
        _year = "" + year;

        _date = datef.parse(this.toString());
        _dayInWeek = WeekDays[numOfDayInWeek(_date)];
    }

    public Date(String date) throws DateTimeException, ParseException {

        String times[] = date.split("/");
        if (times.length != 3) {
            times = date.split("-");
            if (times.length != 3)
                throw new DateTimeException("Error: invalid Date insert");
        }
        int year = Integer.parseInt(times[2]);
        int month = Integer.parseInt(times[1]);
        int day = Integer.parseInt(times[0]);

        if (day <= 0 || day > 31 || year < 0 || month <= 0 || month > 12)
            throw new DateTimeException("Error: invalid Date insert");
        _day = day < 10 ? "0" + day : "" + day;
        _mounth = month < 10 ? "0" + month : "" + month;
        _year = "" + year;

        _date = datef.parse(this.toString());
        _dayInWeek = WeekDays[numOfDayInWeek(_date)];
    }


    public boolean equals(Date other) {
        return other.get_year() == _year &&
                other.get_mounth() == _mounth &&
                other.get_day() == _day;

    }


    public boolean equals(java.util.Date other) {
        Date date = new Date(other);
        return this.equals(date);

    }

    @Override
    public String toString() {
        return _day + "-" + _mounth + "-" + _year;
    }

    public String get_dayInWeek(){
        return _dayInWeek;
    }
    public String get_day() {
        return _day;
    }

    public String get_mounth() {
        return _mounth;
    }

    public String get_year() {
        return _year;
    }

    private int numOfDayInWeek(java.util.Date time){
        Calendar c = Calendar.getInstance();
        c.setTime(_date);
        return c.get(Calendar.DAY_OF_WEEK) - 1;

    }

}

