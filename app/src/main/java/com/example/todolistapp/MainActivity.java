package com.example.todolistapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todolistapp.adapter.ListTaskAdapter;
import com.example.todolistapp.model.Task;
import com.example.todolistapp.utils.DatabaseHandler;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView titlepage, subtitlepage, endpage;
    Button btnAddNew;

    private ArrayList<Task> list = new ArrayList<>();
    DatabaseHandler databaseHandler;
    private RecyclerView rvTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titlepage = findViewById(R.id.titlepage);
        subtitlepage = findViewById(R.id.subtitlepage);
        endpage = findViewById(R.id.endpage);

        btnAddNew = findViewById(R.id.btnAddNew);
        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(MainActivity.this, NewTaskAct.class);
                onDestroy();
                startActivity(a);
            }
        });

        rvTask = findViewById(R.id.rc_task);
        rvTask.setHasFixedSize(true);
        databaseHandler = new DatabaseHandler(getApplicationContext());
        databaseHandler.openDatabase();
        list.addAll(databaseHandler.getAllTasks());
        showRecyclerList();

        if (getSupportActionBar()!=null) {
            getSupportActionBar().setTitle("My To Do");
        }

        //        import font
//        Typeface MLight = Typeface.createFromAsset(getAssets(), "fonts/ML.ttf");
//        Typeface MMedium = Typeface.createFromAsset(getAssets(), "fonts/MM.ttf");

//          customize font
//        titlepage.setTypeface(MMedium);
//        subtitlepage.setTypeface(MLight);
//        endpage.setTypeface(MLight);
        
        // get data from firebase
//        reference = FirebaseDatabase.getInstance("https://todolist-e82bd-default-rtdb.asia-southeast1.firebasedatabase.app").getReference().child("DoesApp");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                // set code to retrive data and replace layout
//                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
//                {
//                    MyDoes p = dataSnapshot1.getValue(MyDoes.class);
//                    list.add(p);
//                }
//                doesAdapter = new DoesAdapter(MainActivity.this, list);
//                ourdoes.setAdapter(doesAdapter);
//                doesAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                // set code to show an error
//                Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
//            }
//        });

    }

    private void showRecyclerList() {
        rvTask.setLayoutManager(new LinearLayoutManager(this));
        ListTaskAdapter listTaskAdapter = new ListTaskAdapter(list, databaseHandler);
        rvTask.setAdapter(listTaskAdapter);
    }
}