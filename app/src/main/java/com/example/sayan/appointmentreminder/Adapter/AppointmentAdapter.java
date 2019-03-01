package com.example.sayan.appointmentreminder.Adapter;

import android.content.Context;
import android.database.Cursor;

import android.support.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.CursorAdapter;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sayan.appointmentreminder.Activity.MainActivity;
import com.example.sayan.appointmentreminder.Appointment;
import com.example.sayan.appointmentreminder.R;

import java.util.ArrayList;

import static com.example.sayan.appointmentreminder.Appointment.COLUMN_DAY;
import static com.example.sayan.appointmentreminder.Appointment.COLUMN_HOUR;
import static com.example.sayan.appointmentreminder.Appointment.COLUMN_MINUTE;
import static com.example.sayan.appointmentreminder.Appointment.COLUMN_MONTH;
import static com.example.sayan.appointmentreminder.Appointment.COLUMN_NAME;
import static com.example.sayan.appointmentreminder.Appointment.COLUMN_PHONE;
import static com.example.sayan.appointmentreminder.Appointment.COLUMN_TYPE;
import static com.example.sayan.appointmentreminder.Appointment.COLUMN_YEAR;

/**
 * Created by 1605476 and 21-May-18
 **/
public class AppointmentAdapter extends CursorAdapter  {

    Cursor cursor;
    public String date, month, year, hour, minute, type, name, phone;

    public AppointmentAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        this.cursor=cursor;

    }

    @NonNull

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        int drawable = 0;
        ImageView imgView = view.findViewById(R.id.type_image);
        name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
        type = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE));
        date = cursor.getString(cursor.getColumnIndex(COLUMN_DAY));
        month = cursor.getString(cursor.getColumnIndex(COLUMN_MONTH));
        year = cursor.getString(cursor.getColumnIndex(COLUMN_YEAR));
        hour = cursor.getString(cursor.getColumnIndex(COLUMN_HOUR));
        minute = cursor.getString(cursor.getColumnIndex(COLUMN_MINUTE));
        phone = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE));

        if (type.equals("Health"))
            drawable = R.drawable.icons8_stethoscope_96;
        else if (type.equals("Personal"))
            drawable = R.drawable.icons8_resume_96;
        else if (type.equals("Work"))
            drawable = R.drawable.icons8_business_80;
        else if (type.equals("School"))
            drawable = R.drawable.icons8_school_80;
        else if (type.equals("Other"))
            drawable = R.drawable.icons8_exclamation_mark_48;

        imgView.setImageResource(drawable);

        TextView textView1 = view.findViewById(R.id.clock_text);
        textView1.setText(hour + ":" + minute);
        TextView textView2 = view.findViewById(R.id.date_text);
        textView2.setText(date + "-" + month + "-" + year);
        TextView textView = view.findViewById(R.id.nameText);
        textView.setText(name);
        TextView textView3 = view.findViewById(R.id.phoneText);
        textView3.setText(phone);
    }
}