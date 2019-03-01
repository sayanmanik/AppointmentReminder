package com.example.sayan.appointmentreminder.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sayan.appointmentreminder.Adapter.AppointmentAdapter;
import com.example.sayan.appointmentreminder.Helper.DatabaseHelper;
import com.example.sayan.appointmentreminder.R;

import static android.view.View.GONE;
import static com.example.sayan.appointmentreminder.Appointment.COLUMN_DAY;
import static com.example.sayan.appointmentreminder.Appointment.COLUMN_HOUR;
import static com.example.sayan.appointmentreminder.Appointment.COLUMN_MINUTE;
import static com.example.sayan.appointmentreminder.Appointment.COLUMN_MONTH;
import static com.example.sayan.appointmentreminder.Appointment.COLUMN_NAME;
import static com.example.sayan.appointmentreminder.Appointment.COLUMN_PHONE;
import static com.example.sayan.appointmentreminder.Appointment.COLUMN_TYPE;
import static com.example.sayan.appointmentreminder.Appointment.COLUMN_YEAR;
import static com.example.sayan.appointmentreminder.Appointment.TABLE_NAME;
import static com.example.sayan.appointmentreminder.Appointment.COLUMN_ID;

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    FloatingActionButton fab;
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
        setContentView(R.layout.activity_main);

        checkPermission();

         toolbar = (Toolbar) findViewById(R.id.toolbar);
         setSupportActionBar(toolbar);
         fab = findViewById(R.id.fab);
         drawerLayout=findViewById(R.id.drawer);
         navigationView=findViewById(R.id.nav_view);
         navigationView.setNavigationItemSelectedListener(this);
         drawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.Open_Drawer,R.string.Close_Drawer);
         drawerToggle.syncState();

         listView = findViewById(R.id.list);



         View emptyView = findViewById(R.id.empty_view);
         listView.setEmptyView(emptyView);

         fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddAppointmentActivity.class);
                startActivity(intent);
             }
        });

         listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String getPhone=getPosition(position+1);
                Intent intent = new Intent(Intent.ACTION_DIAL,Uri.fromParts("tel","+91"+getPhone,null));
                startActivity(intent);
             }
        });
         try {
            DatabaseHelper fdb = new DatabaseHelper(this);
            db = fdb.getReadableDatabase();
            cursor = db.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_TYPE, COLUMN_DAY, COLUMN_MONTH, COLUMN_YEAR, COLUMN_HOUR, COLUMN_MINUTE,COLUMN_PHONE}, null, null, null, null, null);

            adapter = new AppointmentAdapter(this, cursor);
            listView.setAdapter(adapter);

            adapter.swapCursor(cursor);

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

    private void checkPermission()
    {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
        {
            if(!(ContextCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO)==PackageManager.PERMISSION_GRANTED))
            {
                Intent intent=new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:"+getPackageName()));
                startActivity(intent);
                finish();
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
        if(cursor!=null)
        cursor.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            DatabaseHelper fdb = new DatabaseHelper(this);
            SQLiteDatabase db = fdb.getReadableDatabase();
            Cursor newCursor = db.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_TYPE, COLUMN_DAY,COLUMN_MONTH,COLUMN_YEAR,COLUMN_HOUR,COLUMN_MINUTE,COLUMN_PHONE}, null, null, null, null, null);
            //RecyclerView recyclerView=findViewById(R.id.recycler);
            AppointmentAdapter adapter = (AppointmentAdapter)listView.getAdapter();

            adapter.changeCursor(newCursor);
            cursor = newCursor;
            adapter.swapCursor(cursor);
        }
        catch (SQLiteException e)
        {

            Log.e("ERROR TAG",e.getMessage());
            Toast.makeText(this, "Database unavailable", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        Intent intent=null;
        switch(id)
        {
            case R.id.today:
                 intent = new Intent(MainActivity.this, TodayActivity.class);
                startActivity(intent);
                break;
            case R.id.tomorrow:
                 intent = new Intent(MainActivity.this, TomorrowActivity.class);
                startActivity(intent);
                break;
            case R.id.ten_days:
                 intent=new Intent(MainActivity.this,TenDaysActivity.class);
                startActivity(intent);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return  true;
    }
}