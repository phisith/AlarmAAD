package com.example.alarmaad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button normal_b, test;
    //sensor---------------------------------------------------------------------------------------
    TextView text1;

    TextView tempV;

    SensorManager sensorManager;
    Sensor light, temp;
    SensorEventListener lightListener, tempListener;
    View root;
    float maxValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        root = findViewById(R.id.root2);
        text1 = findViewById(R.id.text1);
        tempV = findViewById(R.id.tempV);

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
                float tempA = event.values[0];
                String tempS = "Temp: ";
                tempS += String.valueOf(tempA);
                tempV.setText(tempS);


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
                    text1.setTextColor(Color.rgb(0,0,0));
                }
                else{
                    root.setBackgroundColor(Color.rgb(0,0,0));
                    text1.setTextColor(Color.rgb(255,125,0));
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {


            }
        };
        //sensor------------------------------------------------------------------------------------


        normal_b = (Button)findViewById(R.id.normal_b);
        test = (Button)findViewById(R.id.test);

        normal_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSetAlarmActivity();
            }
        });

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
     //           openLightSensor();
            }
        });


    }
  //  private void openLightSensor(){
  //      Intent intent = new Intent(this, LightSensor.class);
  //      startActivity(intent);

  //  }

    private void openSetAlarmActivity() {
        Intent intent = new Intent(this, SetAlarmActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.List:
                startActivity(new Intent(this, Alarm_listActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    //sensor----------------------------------------------------------------------------------------
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(lightListener, light, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(tempListener, temp, sensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(lightListener);
        sensorManager.unregisterListener(tempListener);
    }
    //sensor----------------------------------------------------------------------------------------
}
