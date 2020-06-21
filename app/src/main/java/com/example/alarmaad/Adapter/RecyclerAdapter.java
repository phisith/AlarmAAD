package com.example.alarmaad.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alarmaad.Model.Todo;
import com.example.alarmaad.R;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{




    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView text_priority, text_task, text_Date_or_Time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            text_priority = itemView.findViewById(R.id.text_priority);
            text_task = itemView.findViewById(R.id.text_task);
            text_Date_or_Time = itemView.findViewById(R.id.text_Date_or_Time);
        }
    }

    private Context context;
    private ArrayList<Todo> todoArrayList;

    public  RecyclerAdapter(Context context, ArrayList<Todo> todoArrayList){
        this.context = context;
        this.todoArrayList = todoArrayList;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()). inflate(R.layout.list, parent, false);



        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.text_priority.setText(todoArrayList.get(position).getPriority());
        holder.text_task.setText(todoArrayList.get(position).getTask());
        holder.text_Date_or_Time.setText(todoArrayList.get(position).getDateortime());

    }







    @Override
    public int getItemCount() {
        return todoArrayList.size();
    }
}
