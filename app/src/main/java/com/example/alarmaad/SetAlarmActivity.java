package com.example.alarmaad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class SetAlarmActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    TextView showtime;

    Button Stop_alarm, Set_alram;

    Intent intent;
    SensorManager sensorManager;
    Sensor light, temp;
    SensorEventListener lightListener, tempListener;
    View root;
    float maxValue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);

        root = findViewById(R.id.root3);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        temp = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        if (light == null) {
            Toast.makeText(this, "this device no light sensor", Toast.LENGTH_LONG).show();
            finish();
        }

        tempListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {



            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        //get the maxrage of light sensor
        maxValue = light.getMaximumRange();

        lightListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float value = event.values[0];
                //getSupportActionBar().setTitle("light : " + value + " xl");


                //between 0 to 255
                int newValue = (int) (255 * value / maxValue);
                if (newValue >= 127){
                    root.setBackgroundColor(Color.rgb(255,255,255));
                    showtime.setTextColor(Color.rgb(0,0,0));
                }
                else{
                    root.setBackgroundColor(Color.rgb(0,0,0));
                    showtime.setTextColor(Color.rgb(255,125,0));
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {


            }
        };
        //sensor------------------------------------------------------------------------------------

        showtime = (TextView)findViewById(R.id.showtime);

        Set_alram = (Button)findViewById(R.id.set_alarm);

        Stop_alarm = (Button)findViewById(R.id.Stop_alarm);

        Set_alram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment TimepickerDialog = new com.example.alarmaad.TimePicker(); // call the timepickerdialog form the set_alarm buttton
                TimepickerDialog.show(getSupportFragmentManager(),"Time Picker");
            }
        });

        intent = new Intent(getApplicationContext(), com.example.alarmaad.Ringtone.class); //call the alarm song class

        Stop_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // click the stop_alarm button to stop the alarm
                stopService(new Intent(getApplicationContext(), com.example.alarmaad.Ringtone.class)); //stop the alarm song
                cancelAlarm();
            }

        });







    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) { // set the time in the timepick to get the alarm start

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,0);

        ShowTimeSet(calendar);
        startAlarm(calendar);
    }

    public void ShowTimeSet(Calendar calendar){ // set the time that you set on the textview
        String Timeset =  "Alarm set at: ";
        Timeset += DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());

        showtime.setText(Timeset);

    }


    public void startAlarm(Calendar calendar) { // to get the alarm ringing and show the notification
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        if(calendar.before(Calendar.getInstance())){
            calendar.add(Calendar.DATE, 1);



        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),pendingIntent); // actually the alarm start from here
        startService(new Intent(getApplicationContext(), com.example.alarmaad.Ringtone.class)); // get the alarm song start




    }

    public void cancelAlarm() { //cancel the alarm
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        alarmManager.cancel(pendingIntent);



       showtime.setText("Alarm canceled"); // set the message that alarm already got canceled
    }

    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left); //animation slide in and out
    }

    //sensor----------------------------------------------------------------------------------------
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(lightListener, light, SensorManager.SENSOR_DELAY_FASTEST);

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(lightListener);

    }
    //sensor----------------------------------------------------------------------------------------




}




