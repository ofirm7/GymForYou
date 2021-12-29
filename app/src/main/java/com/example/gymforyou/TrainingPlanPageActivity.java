package com.example.gymforyou;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class TrainingPlanPageActivity extends AppCompatActivity {

    SharedPref sharedPref;

    TextView trainingPlanNameTV;

    //for the list
    ListView trainingPlansList;
    MyListAdapter adapter3;
    ArrayList<String> aTitle = new ArrayList<>(), aDescription = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_plan_page);

        trainingPlanNameTV = findViewById(R.id.trainingPlanNameTV);
        if (sharedPref.isAdmin())
        {
            trainingPlanNameTV.setText(DataModel.admins.get(getIntent().getIntExtra("USERPOSITION", 0)
            ).getTrainingPlansList().get(getIntent().getIntExtra("TONEXT", 0)).getNameOfTrainingPlan());
        }
        else {
            trainingPlanNameTV.setText(DataModel.users.get(getIntent().getIntExtra("USERPOSITION", 0)
            ).getTrainingPlansList().get(getIntent().getIntExtra("TONEXT", 0)).getNameOfTrainingPlan());
        }



    }
}