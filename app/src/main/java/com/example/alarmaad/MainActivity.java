package com.example.alarmaad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.LayoutTransition;
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
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button normal_b, Button_main2;
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
        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT); // get the light sensor
        temp = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE); // get the temperature sensor

        if (light == null) { //to do check is that a light sensor in the device
            Toast.makeText(this, "this device no light sensor", Toast.LENGTH_LONG).show();
            finish();
        }

        tempListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float tempA = event.values[0]; // get the values of the light from the sensor
                String tempS = "Temp: "; // set the string for display the values
                tempS += String.valueOf(tempA); // combine the light into the string
                tempV.setText(tempS); // set the light values to to display on the textview


            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        //get the rage of the light sensor
        maxValue = light.getMaximumRange();

        lightListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float value = event.values[0]; // get the light values


                //between 0 to 255
                int newValue = (int) (255 * value / maxValue); //detect the light values
                if (newValue >= 127){ // if the light values higher than 127 the color of the background, text will be change to the RGB color that i set
                    root.setBackgroundColor(Color.rgb(255,255,255));
                    text1.setTextColor(Color.rgb(0,0,0));
                    tempV.setTextColor(Color.rgb(0,0,0));
                }
                else{ //similar thing here if the light values lower than 127 the color will change
                    root.setBackgroundColor(Color.rgb(0,0,0));
                    text1.setTextColor(Color.rgb(255,125,0));
                    tempV.setTextColor(Color.rgb(255,125,0));
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {


            }
        };
        //sensor------------------------------------------------------------------------------------


        normal_b = (Button)findViewById(R.id.normal_b);
        Button_main2 = (Button)findViewById(R.id.Button_main2);


        normal_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSetAlarmActivity();
            }
        });

        Button_main2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTodo_list();
            }
        });




    }
  //  private void openLightSensor(){
  //      Intent intent = new Intent(this, LightSensor.class);
  //      startActivity(intent);

  //  }

    private void openSetAlarmActivity() { //open the setAlarmActivity
        Intent intent = new Intent(this, SetAlarmActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left); //this is the slide in, out animation
    }

    private void openTodo_list() { //open todolist
        Intent intent = new Intent(this, Todo_listActivity.class);
        startActivity(intent);
    }



    //sensor----------------------------------------------------------------------------------------
    @Override
    protected void onResume() { //get the sensor start
        super.onResume();
        sensorManager.registerListener(lightListener, light, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(tempListener, temp, sensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() { //stop the sensor
        super.onPause();
        sensorManager.unregisterListener(lightListener);
        sensorManager.unregisterListener(tempListener);
    }
    //sensor----------------------------------------------------------------------------------------
}
