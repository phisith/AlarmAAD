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

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;



    ArrayList<Todo> todoArrayList;
    RecyclerAdapter recyclerAdapter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        recyclerView = findViewById(R.id.recyclerview);

        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("ToDoList");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        todoArrayList = new ArrayList<>();


        clearlist();

        GetDataFromdatabase();
    }

    private void GetDataFromdatabase() {
        Query query = reference;

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                clearlist();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Todo todo = new Todo();

                    todo.setTask(snapshot.child("task").getValue().toString());
                    todo.setPriority(snapshot.child("priority").getValue().toString());
                    todo.setDateortime(snapshot.child("dateortime").getValue().toString());

                    todoArrayList.add(todo);
                }

                recyclerAdapter = new RecyclerAdapter(getApplicationContext(), todoArrayList);
                recyclerView.setAdapter(recyclerAdapter);
                recyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void resetlist(){
        Query query = reference;

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.Add:
                startActivity(new Intent(this, insertlistActivity.class));

            case R.id.Delete:
                resetlist();
        }
        return super.onOptionsItemSelected(item);
    }
}
