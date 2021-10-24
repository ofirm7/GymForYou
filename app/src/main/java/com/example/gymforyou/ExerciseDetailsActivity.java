package com.example.gymforyou;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ExerciseDetailsActivity extends AppCompatActivity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_details);

        tv = findViewById(R.id.tv);

        tv.setText(getIntent().getStringExtra("WE"));
    }
}