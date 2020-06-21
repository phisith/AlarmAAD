package com.example.alarmaad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alarmaad.Model.Todo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class insertlistActivity extends AppCompatActivity {

    private EditText insert_task, insert_priority, insert_Date_or_Time;
    private Button btn_add;

    SensorManager sensorManager;
    Sensor light, temp;
    SensorEventListener lightListener, tempListener;
    View root;
    float maxValue;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertlist);

        insert_task = findViewById(R.id.insert_task);
        insert_priority = findViewById(R.id.insert_priority);
        insert_Date_or_Time = findViewById(R.id.insert_dateortime);

        btn_add = findViewById(R.id.btn_add);

        firebaseDatabase =FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("ToDoList");

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToFirebase();
            }
        });

        root = findViewById(R.id.root4);
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
                    insert_task.setTextColor(Color.rgb(0,0,0));
                    insert_priority.setTextColor(Color.rgb(0,0,0));
                    insert_Date_or_Time.setTextColor(Color.rgb(0,0,0));
                }
                else{
                    root.setBackgroundColor(Color.rgb(0,0,0));
                    insert_task.setTextColor(Color.rgb(255,125,0));
                    insert_priority.setTextColor(Color.rgb(255,125,0));
                    insert_Date_or_Time.setTextColor(Color.rgb(255,125,0));
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {


            }
        };
    }

    private void saveToFirebase() {
        String task = insert_task.getText().toString();
        String priority = insert_priority.getText().toString();
        String Date_or_Time = insert_Date_or_Time.getText().toString();

        if(!TextUtils.isEmpty(task) && !TextUtils.isEmpty(priority)){

            Todo todo = new Todo(task, priority, Date_or_Time);

            reference.push().setValue(todo).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    Toast.makeText(insertlistActivity.this, "Task is added", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(insertlistActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(insertlistActivity.this, "Task is added", Toast.LENGTH_SHORT).show();
        }

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

