package com.example.alarmaad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertlist);

        insert_task = findViewById(R.id.insert_priority);
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
}

