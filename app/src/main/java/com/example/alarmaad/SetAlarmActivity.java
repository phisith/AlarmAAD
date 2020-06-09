package com.example.alarmaad;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

public class SetAlarmActivity extends AppCompatActivity {
    TimePicker timePicker;
    TextView showtime;

    int Hour, Min;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);

        timePicker = (TimePicker)findViewById(R.id.timepicker);
        showtime = (TextView)findViewById(R.id.showtime);

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

                Hour= hourOfDay;
                Min= minute;
                showtime.setText(showtime.getText().toString()+ " " +Hour + ":" +Min);
            }
        });
    }

    public void setAlarm (View view){

        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        Date date = new Date();

        Calendar C_A = Calendar.getInstance(); // this for the alarm
        Calendar C_T = Calendar.getInstance(); // this for the current time

        C_A.setTime(date);
        C_T.setTime(date);

        C_A.set(Calendar.HOUR_OF_DAY,Hour);
        C_A.set(Calendar.MINUTE,Min);
        C_A.set(Calendar.SECOND,0);

        if(C_A.before(C_T)){
            C_A.add(Calendar.DATE,1);
        }

        Intent intent = new Intent(SetAlarmActivity.this,NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(SetAlarmActivity.this,24444,intent,0);
        alarmManager.set(AlarmManager.RTC_WAKEUP,C_A.getTimeInMillis(),pendingIntent);





    }

}
