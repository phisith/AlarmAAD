package com.example.alarmaad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;

public class SetAlarmActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    TextView showtime;

    Button Stop_alarm, Set_alram;

    Intent intent;





    public static boolean data = true;

    public static boolean getData(){
        return data;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);


        showtime = (TextView)findViewById(R.id.showtime);

        Set_alram = (Button)findViewById(R.id.set_alarm);

        Stop_alarm = (Button)findViewById(R.id.Stop_alarm);

        Set_alram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment TimepickerDialog = new com.example.alarmaad.TimePicker();
                TimepickerDialog.show(getSupportFragmentManager(),"Time Picker");
            }
        });

        intent = new Intent(getApplicationContext(), com.example.alarmaad.Ringtone.class);

        Stop_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(getApplicationContext(), com.example.alarmaad.Ringtone.class));
                cancelAlarm();
            }

        });







    }





    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,0);

        ShowTimeSet(calendar);
        startAlarm(calendar);
    }

    public void ShowTimeSet(Calendar calendar){
        String Timeset =  "Alarm set at: ";
        Timeset += DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());

        showtime.setText(Timeset);

    }
    Uri RingRing = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);

    Ringtone ringtone = RingtoneManager.getRingtone(this,RingRing);

    public void startAlarm(Calendar calendar) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        if(calendar.before(Calendar.getInstance())){
            calendar.add(Calendar.DATE, 1);



        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),pendingIntent);
        startService(new Intent(getApplicationContext(), com.example.alarmaad.Ringtone.class));




    }

    public void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        alarmManager.cancel(pendingIntent);



       showtime.setText("Alarm canceled");
    }




}




