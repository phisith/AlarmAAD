package com.example.alarmaad;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Splash_Screen extends AppCompatActivity {
    private Handler mHandler;
    private Runnable mRunnable;

    SensorManager sensorManager;
    Sensor light, temp;
    SensorEventListener lightListener, tempListener;
    View root;
    float maxValue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        mRunnable = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), MainActivity.class)); //set the next the page after the splash screen gone
            }
        };
        mHandler = new Handler();
        mHandler.postDelayed(mRunnable, 800); // set duration of the splash screen will stay

        root = findViewById(R.id.root5);
        ;




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

                }
                else{
                    root.setBackgroundColor(Color.rgb(0,0,0));

                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {


            }
        };

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mHandler != null && mRunnable != null)
            mHandler.removeCallbacks(mRunnable);
    }
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
}
