package com.example.sayan.appointmentreminder.Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.sayan.appointmentreminder.Appointment;

import static com.example.sayan.appointmentreminder.Appointment.COLUMN_PHONE;


/**
 * Created by 1605476 and 21-May-18
 **/
public class DatabaseHelper extends SQLiteOpenHelper{

    private static int DATABSE_VERSION=3;
    private static String DATABASE_NAME="appointment_db";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABSE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Appointment.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.e("Old Version",String.valueOf(oldVersion));
        Log.e("New Version",String.valueOf(newVersion));
        //if(newVersion>oldVersion)
        //db.execSQL("ALTER TABLE " + Appointment.TABLE_NAME+" ADD COLUMN "+COLUMN_PHONE+" TEXT;");
    }
}
