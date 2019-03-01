package com.example.sayan.appointmentreminder.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sayan.appointmentreminder.Adapter.AppointmentAdapter;
import com.example.sayan.appointmentreminder.Helper.DatabaseHelper;
import com.example.sayan.appointmentreminder.R;

import java.util.Calendar;

import static com.example.sayan.appointmentreminder.Appointment.COLUMN_DAY;
import static com.example.sayan.appointmentreminder.Appointment.COLUMN_HOUR;
import static com.example.sayan.appointmentreminder.Appointment.COLUMN_ID;
import static com.example.sayan.appointmentreminder.Appointment.COLUMN_MINUTE;
import static com.example.sayan.appointmentreminder.Appointment.COLUMN_MONTH;
import static com.example.sayan.appointmentreminder.Appointment.COLUMN_NAME;
import static com.example.sayan.appointmentreminder.Appointment.COLUMN_PHONE;
import static com.example.sayan.appointmentreminder.Appointment.COLUMN_TYPE;
import static com.example.sayan.appointmentreminder.Appointment.COLUMN_YEAR;
import static com.example.sayan.appointmentreminder.Appointment.TABLE_NAME;

public class TodayActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private String day,year,month;
    SQLiteDatabase db;
    Cursor cursor;
    Toolbar toolbar;
    ListView listView;
    AppointmentAdapter adapter;
    ActionBarDrawerToggle drawerToggle;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Calendar c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);

        toolbar = (Toolbar) findViewById(R.id.toolbarToDay);
        setSupportActionBar(toolbar);

        listView = findViewById(R.id.listToDay);

        c = Calendar.getInstance();
        year = String.valueOf(c.get(Calendar.YEAR));
        month = String.valueOf(c.get(Calendar.MONTH));
        day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));

        Toast.makeText(this,day+","+month+","+year,Toast.LENGTH_LONG).show();

        drawerLayout=findViewById(R.id.drawerToDay);
        navigationView=findViewById(R.id.nav_viewToDay);
        navigationView.setNavigationItemSelectedListener(this);
        drawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.Open_Drawer,R.string.Close_Drawer);
        drawerToggle.syncState();

        View emptyView = findViewById(R.id.empty_view);
        listView.setEmptyView(emptyView);


      /*  listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String getPhone=getPosition(position+1);
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel","+91"+getPhone,null));
                startActivity(intent);
            }
        });*/


        try {
            DatabaseHelper fdb = new DatabaseHelper(this);
            db = fdb.getReadableDatabase();


            String selection = COLUMN_DAY+ " = ? AND "+COLUMN_MONTH+ " = ? AND "+COLUMN_YEAR+" = ?";

            String[] selectionArgs = {day,"Jul",year };

            Cursor cursor = db.query(
                    TABLE_NAME,   // The table to query
                    new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_TYPE, COLUMN_DAY,COLUMN_MONTH,COLUMN_YEAR,COLUMN_HOUR,COLUMN_MINUTE,COLUMN_PHONE},           // The array of columns to return (pass null to get all)
                    selection,            // The columns for the WHERE clause
                    selectionArgs,        // The values for the WHERE clause
                    null,                 // don't group the rows
                    null,                 // don't filter by row groups
                    null         // The sort order
            );

            adapter = new AppointmentAdapter(this, cursor);
            listView.setAdapter(adapter);

            Toast.makeText(this,adapter.date,Toast.LENGTH_LONG).show();
            //adapter.swapCursor(cursor);

        } catch (SQLiteException e) {
            Log.e("ERROR TAG",e.getMessage());
            Toast.makeText(this, "database unavailable", Toast.LENGTH_LONG).show();
        }
    }


    private String getPosition(int id) {

        Cursor newCursor = db.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_TYPE, COLUMN_DAY, COLUMN_MONTH, COLUMN_YEAR, COLUMN_HOUR, COLUMN_MINUTE, COLUMN_PHONE}, COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        if (newCursor != null) {
            if (newCursor.moveToFirst()) {
                String str = newCursor.getString(newCursor.getColumnIndex(COLUMN_PHONE));
                newCursor.close();

                return str;
            }
        }
        return  null;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent=null;
        int id=item.getItemId();
        switch(id)
        {
            case R.id.overView:
                intent=new Intent(TodayActivity.this,MainActivity.class);
                startActivity(intent);
                break;

            case R.id.ten_days:
                intent=new Intent(TodayActivity.this,TenDaysActivity.class);
                startActivity(intent);

                break;

            case R.id.tomorrow:
                intent=new Intent(TodayActivity.this,TomorrowActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            DatabaseHelper fdb = new DatabaseHelper(this);
            SQLiteDatabase db = fdb.getReadableDatabase();

            String selection = COLUMN_DAY+ " = ? AND "+COLUMN_MONTH+ " = ? AND "+COLUMN_YEAR+" = ?";

            String[] selectionArgs = { day, month, year };

            Cursor newCursor = db.query(
                    "appointment",   // The table to query
                    new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_TYPE, COLUMN_DAY, COLUMN_MONTH, COLUMN_YEAR, COLUMN_HOUR, COLUMN_MINUTE, COLUMN_PHONE},           // The array of columns to return (pass null to get all)
                    null,            // The columns for the WHERE clause
                    null,        // The values for the WHERE clause
                    null,                 // don't group the rows
                    null,                 // don't filter by row groups
                    null           // The sort order
            );

            //RecyclerView recyclerView=findViewById(R.id.recycler);
            AppointmentAdapter adapter = (AppointmentAdapter)listView.getAdapter();

            adapter.changeCursor(newCursor);
            cursor = newCursor;
           // adapter.swapCursor(cursor);
        }
        catch (SQLiteException e)
        {

            Log.e("ERROR TAG",e.getMessage());
            Toast.makeText(this, "Database unavailable", Toast.LENGTH_LONG).show();
        }
    }
    }