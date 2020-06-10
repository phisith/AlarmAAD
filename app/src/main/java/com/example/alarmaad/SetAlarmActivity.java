package com.example.alarmaad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.sql.Time;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class SetAlarmActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    TextView showtime;

    int hour, min;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);

        showtime = (TextView)findViewById(R.id.showtime);

        Button timepickerdialog =findViewById(R.id.set_alarm);
        timepickerdialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timepick = new com.example.alarmaad.TimePicker();
                timepick.show(getSupportFragmentManager(),"time picker");

            }
        });



    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,0);

        updatetimetext(calendar);
        startAlarm(calendar);
    }

    public void updatetimetext(Calendar calendar){
        String timeText =  "Alarm set at: ";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());

        showtime.setText(timeText);

    }

    public void startAlarm(Calendar calendar) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        if(calendar.before(Calendar.getInstance())){
            calendar.add(Calendar.DATE, 1);
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),pendingIntent);
    }

}

    //public void setAlarm (View view){

      //  AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        //Date date = new Date();

        //Calendar C_A = Calendar.getInstance(); // this for the alarm
        //Calendar C_T = Calendar.getInstance(); // this for the current time

        //C_A.setTime(date);
        //C_T.setTime(date);


        //if(C_A.before(C_T)){
        //    C_A.add(Calendar.DATE,1);
        //}

        //Intent intent = new Intent(SetAlarmActivity.this,NotificationReceiver.class);
        //PendingIntent pendingIntent = PendingIntent.getBroadcast(SetAlarmActivity.this,24444,intent,0);
        //alarmManager.set(AlarmManager.RTC_WAKEUP,C_A.getTimeInMillis(),pendingIntent);





   // }


