package com.example.alarmaad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button normal_b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        normal_b = (Button)findViewById(R.id.normal_b);

        normal_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSetAlarmActivity();
            }
        });
    }

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
}
