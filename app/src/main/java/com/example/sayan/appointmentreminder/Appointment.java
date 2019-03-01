package com.example.sayan.appointmentreminder;

/**
 * Created by 1605476 and 21-May-18
 **/
public class Appointment {

    public String name;
    public String type;
    public String monthDate;
    public int dayDate;
    public int yearDate;
    public int hourTime;
    public int minuteTime;
    public int phone;



    public Appointment(String passedAppointmentName, String passedAppointType,
                       String passedAppointmentDateMonth, int passedAppointmentDateDay, int passedAppointmentDateYear,
                       int passedAppointmentTimeHour, int passedAppointmentTimeMinute,
                       int phoneNumber){
        name = passedAppointmentName;
        type = passedAppointType;
        monthDate = passedAppointmentDateMonth;
        dayDate = passedAppointmentDateDay;
        yearDate = passedAppointmentDateYear;
        hourTime = passedAppointmentTimeHour;
        minuteTime = passedAppointmentTimeMinute;
        phone=phoneNumber;
        }


    public static final String COLUMN_ID ="_id" ;
    public static final String TABLE_NAME = "appointment";
    public static final String COLUMN_NAME="name";
    public static final String COLUMN_TYPE="type";
    public static final String COLUMN_DAY="day";
    public static final String COLUMN_MONTH="month";
    public static final String COLUMN_YEAR="year";
    public static final String COLUMN_HOUR="hour";
    public static final String COLUMN_MINUTE="minute";
    public static final String COLUMN_PHONE="phone";


    public static final String CREATE_TABLE="CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_TYPE + " TEXT,"
            + COLUMN_DAY + " TEXT,"
            + COLUMN_MONTH + " TEXT,"
            + COLUMN_YEAR + " TEXT,"
            + COLUMN_HOUR + " TEXT,"
            + COLUMN_MINUTE + " TEXT,"
            +COLUMN_PHONE+" TEXT"
            + ")";

    public String  getMonthDate()
    {
        return monthDate;
    }

    public String getType()
    {
        return type;
    }

    public int getDayDate()
    {
        return dayDate;
    }

    public int getYearDate()
    {
        return yearDate;
    }
    public int getHourTime()
    {
        return hourTime;
    }

    public int getMinuteTime()
    {
        return minuteTime;
    }

    public int getPhone()
    {
        return phone;
    }


    public void setName(String name)
    {
        this.name=name;

    }

    public void setType(String type)
    {
        this.type=type;
    }

    public void setDayDate(int dayDate)
    {
        this.dayDate=dayDate;
    }

    public void setYearDate(int year)
    {
        this.yearDate=year;
    }

    public void setHourTime(int hour)
    {
        this.hourTime=hour;
    }

    public void setMinuteTime(int minuteTime)
    {
        this.minuteTime=minuteTime;
    }


    public void setMonthDate(String monthDate)
    {
        this.monthDate=monthDate;
    }


    public void setPhoneNumber(int phone)
    {
        this.phone=phone;
    }


}