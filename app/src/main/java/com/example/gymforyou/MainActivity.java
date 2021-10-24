package com.example.gymforyou;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView l1;
    MyListAdapter adapter1;
    ArrayList<String> aTitle = new ArrayList<>(), aDescription = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aTitle.add("Excersie 1");
        aTitle.add("Excersie 2");
        aTitle.add("Excersie 3");
        aTitle.add("Excersie 4");

        aDescription.add("blablabla");
        aDescription.add("blablabla");
        aDescription.add("blablabla");
        aDescription.add("blablabla");

        adapter1 = new MyListAdapter(this, aTitle, aDescription);

        l1 = findViewById(R.id.LTest);
        l1.setAdapter(adapter1);

        l1.setOnItemClickListener(this);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent1 = new Intent(this, ExerciseDetailsActivity.class);
        switch (position)
        {
            case 0:
                intent1.putExtra("WE", "Exercise 1");
                break;
            case 1:
                intent1.putExtra("WE", "Exercise 2");
                break;
            case 2:
                intent1.putExtra("WE", "Exercise 3");
                break;
            case 3:
                intent1.putExtra("WE", "Exercise 4");
                break;
        }

        startActivity(intent1);
    }
}