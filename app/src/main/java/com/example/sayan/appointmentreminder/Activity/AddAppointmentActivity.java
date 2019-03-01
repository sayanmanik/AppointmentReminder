package com.example.sayan.appointmentreminder.Activity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.sayan.appointmentreminder.Adapter.CustomAdapter;
import com.example.sayan.appointmentreminder.Appointment;
import com.example.sayan.appointmentreminder.BroadCastReceiverExample.MyAlarm;
import com.example.sayan.appointmentreminder.Helper.DatabaseHelper;
import com.example.sayan.appointmentreminder.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddAppointmentActivity extends AppCompatActivity {

    TextView txtDate;
    TextView txtTime;
    ImageButton imgButton;
    private int year;
    private int month;
    private int day;

    Calendar c;
    private int hour;
    private int minute;

    static final int DATE_DIALOG_ID = 999;
    static final int TIME_DIALOG_ID = 998;
    Spinner spinnerAppointmentType;
    String typeText;
    Long number;
    String  nameText;
    EditText editAppointmentName,editPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appoint);
        spinnerAppointmentType = findViewById(R.id.spnTaskType);


        imgButton=findViewById(R.id.micro_phone);
        editAppointmentName = (EditText) findViewById(R.id.editTaskName);
        editPhoneNumber=findViewById(R.id.phone_number);

        String[] Names={"Personal","Work","School","Health","Other"};

        int images[] = {R.drawable.icons8_resume_96,
                R.drawable.icons8_business_80,
                R.drawable.icons8_school_80,
                R.drawable.icons8_stethoscope_96,
                R.drawable.icons8_exclamation_mark_48};



        final SpeechRecognizer speechRecognizer=SpeechRecognizer.createSpeechRecognizer(this);

        final Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Say Something!!");

        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int error) {

            }

            @Override
            public void onResults(Bundle bundle) {
                ArrayList<String>list=bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if(list!=null)
                editAppointmentName.setText(list.get(0));
            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });

        imgButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction())
                {
                    case MotionEvent.ACTION_UP:
                        speechRecognizer.stopListening();
                        editAppointmentName.setHint("You will see input here!!");

                        break;
                    case MotionEvent.ACTION_DOWN:
                        speechRecognizer.startListening(intent);
                        editAppointmentName.setHint("");
                        editAppointmentName.setHint("Listening...");
                        break;
                }
                return false;
            }
        });


        final CustomAdapter customAdapter=new CustomAdapter(getApplicationContext(),images,Names);
        spinnerAppointmentType.setAdapter(customAdapter);


        spinnerAppointmentType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeText= (String) customAdapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        setCurrentDateAndTimeOnView();
    }

    // display current date and time
    public void setCurrentDateAndTimeOnView() {
        txtDate = (TextView)findViewById(R.id.txttvDate);
        txtTime = (TextView)findViewById(R.id.txtvTime);

        c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        // set current time into textview
        txtTime.setText(new StringBuilder().append(pad(hour))
                .append(":").append(pad(minute)));

        // set current date into textview
        txtDate.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(month + 1).append("-").append(day).append("-")
                .append(year).append(" "));
    }

    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            // set selected date into textview
            txtDate.setText(new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(" "));
        }
    };

    private TimePickerDialog.OnTimeSetListener timePickerListener
            = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
            hour = selectedHour;
            minute = selectedMinute;

            // set current time into textview
            txtTime.setText(new StringBuilder().append(pad(hour))
                    .append(":").append(pad(minute)));
        }
    };

    //Displays a new dialog for date picker or time picker
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,
                        datePickerListener, year, month,day);
            case TIME_DIALOG_ID:
                // set time picker as current time
                return new TimePickerDialog(this,
                        timePickerListener, hour, minute,false);
        }
        return null;
    }

    /**When Date Text View is touched, opens a Date Picker Dialog **/
    public void edittxtDate(View view){
        showDialog(DATE_DIALOG_ID);
    }

    /**When Time Text View is touched, opens a Time Picker Dialog**/
    public void edittxtTime(View view){
        showDialog(TIME_DIALOG_ID);
    }


    public void btnAddAppointment() {

        if(!(editAppointmentName.getText().toString()).isEmpty()){

           nameText=editAppointmentName.getText().toString();
           number=Long.parseLong(editPhoneNumber.getText().toString());

           insertData(nameText,typeText,day,DisplayTheMonthInCharacters(month),year,hour,minute,number);
           setAlarm(c.getTimeInMillis()-60000);
           finish();
        }
        else{
            Toast toast = Toast.makeText(AddAppointmentActivity.this, "Please enter an Appointment Name", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    public void insertData(String nameText, String typeText,
                           int day, String month, int year, int hour, int minute,Long number)
    {
        DatabaseHelper helper=new DatabaseHelper(this);
        SQLiteDatabase database= helper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Appointment.COLUMN_NAME,nameText);
        values.put(Appointment.COLUMN_TYPE,typeText);
        values.put(Appointment.COLUMN_DAY,String.valueOf(day));
        values.put(Appointment.COLUMN_MONTH,month);
        values.put(Appointment.COLUMN_YEAR,String.valueOf(year));
        values.put(Appointment.COLUMN_HOUR,String.valueOf(hour));
        values.put(Appointment.COLUMN_MINUTE,String.valueOf(minute));
        values.put(Appointment.COLUMN_PHONE,String.valueOf(number));

        database.insert(Appointment.TABLE_NAME,null,values);

    }


    /* Helper Methods */

    //Returns the Month Abbreviation for the corresponding number.
    private String DisplayTheMonthInCharacters(int passedMonth){
        switch(passedMonth){
            case 0:
                return "Jan";
            case 1:
                return"Feb";
            case 2:
                return"Mar";
            case 3:
                return"Apr";
            case 4:
                return"May";
            case 5:
                return"Jun";
            case 6:
                return"Jul";
            case 7:
                return"Aug";
            case 8:
                return"Sept";
            case 9:
                return"Oct";
            case 10:
                return"Nov";
            case 11:
                return"Dec";

        }
        return "";
    }



    private String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }


    public void setAlarm(long timeMilis)
    {
        AlarmManager alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent=new Intent(this, MyAlarm.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(this,0,intent,0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,timeMilis,AlarmManager.INTERVAL_DAY,pendingIntent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(id==R.id.action_cancel)
        {
            finish();
            return true;
        }

        if(id==R.id.add_item)
        {
            btnAddAppointment();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode==100)
        {
            if(resultCode==RESULT_OK&&data!=null)
            {
                ArrayList<String>list=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                nameText= list.get(0);
            }
        }
    }
}