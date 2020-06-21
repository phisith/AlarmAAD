package com.example.alarmaad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Person;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alarmaad.Adapter.RecyclerAdapter;
import com.example.alarmaad.Model.Todo;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Todo_listActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    SensorManager sensorManager;
    Sensor light, temp;
    SensorEventListener lightListener, tempListener;
    View root;
    float maxValue;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    ArrayList<Todo> todoArrayList; // the list to store the values of todo
    RecyclerAdapter recyclerAdapter; //the adapter for the recyclerview





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        recyclerView = findViewById(R.id.recyclerview);

        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("ToDoList"); // select the location or go to the location

        recyclerView.setHasFixedSize(true); // set the list
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); //set and show the list


        todoArrayList = new ArrayList<>();


        clearlist(); // clear the list, so the list will not stack for make the list mass up

        GetDataFromdatabase(); // call the get data from the database

        root = findViewById(R.id.root6);





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

    private void GetDataFromdatabase() {
        Query query = reference; // go to the location in the database that we set before

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) { // read the data from the database
                clearlist();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Todo todo = new Todo();

                    todo.setTask(snapshot.child("task").getValue().toString()); //read the task
                    todo.setPriority(snapshot.child("priority").getValue().toString()); //read the priority
                    todo.setDateortime(snapshot.child("dateortime").getValue().toString()); //read the date or time

                    todoArrayList.add(todo); // add the data that read from the database into the array list
                }

                recyclerAdapter = new RecyclerAdapter(getApplicationContext(), todoArrayList);
                recyclerView.setAdapter(recyclerAdapter);
                recyclerAdapter.notifyDataSetChanged(); // set the data need to be dispaly
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void resetlist(){
        Query query = reference; //go to the location that we set before

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) { //read the data in the database
                    appleSnapshot.getRef().removeValue(); // remove the list from the database
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void clearlist(){
        if (todoArrayList !=null){
            todoArrayList.clear();

            if (recyclerAdapter != null){
                recyclerAdapter.notifyDataSetChanged();
            }
        }
        todoArrayList = new ArrayList<>();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // to call the button that create to be on the toolbar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){ //option for click the menu of the toolbar
            case R.id.Add:
                startActivity(new Intent(this, insertlistActivity.class)); // go to insertlistActivity

            case R.id.Delete: // call the restlist function to delete all the task
                resetlist();
        }
        return super.onOptionsItemSelected(item);
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
