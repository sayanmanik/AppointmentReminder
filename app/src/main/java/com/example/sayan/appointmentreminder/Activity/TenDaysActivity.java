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

public class TenDaysActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    SQLiteDatabase db;
    Cursor cursor;
    Toolbar toolbar;
    ListView listView;
    AppointmentAdapter adapter;
    ActionBarDrawerToggle drawerToggle;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ten_days);

        toolbar = (Toolbar) findViewById(R.id.toolbarTenDays);
        setSupportActionBar(toolbar);

        listView = findViewById(R.id.listTenDays);

        drawerLayout=findViewById(R.id.drawerTenDays);
        navigationView=findViewById(R.id.nav_viewTenDays);
        navigationView.setNavigationItemSelectedListener(this);
        drawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.Open_Drawer,R.string.Close_Drawer);
        drawerToggle.syncState();

        View emptyView = findViewById(R.id.empty_view);
        listView.setEmptyView(emptyView);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String getPhone=getPosition(position+1);
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel","+91"+getPhone,null));
                startActivity(intent);
            }
        });



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
                intent=new Intent(TenDaysActivity.this,MainActivity.class);
                startActivity(intent);
                break;

            case R.id.today:
                intent=new Intent(TenDaysActivity.this,TodayActivity.class);
                startActivity(intent);
                break;

            case R.id.tomorrow:
                intent=new Intent(TenDaysActivity.this,TomorrowActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }
}