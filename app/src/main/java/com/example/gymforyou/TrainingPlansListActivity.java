package com.example.gymforyou;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class TrainingPlansListActivity extends AppCompatActivity {

    SharedPref sharedPref;
    AlertDialog.Builder builder;
    Dialog addDialog;
    Button openAddDialog, addTrainingPlan;
    TextView dontHaveTrainingPlansTV;

    //for the list
    ListView trainingPlansList;
    MyListAdapter adapter3;
    ArrayList<String> aTitle = new ArrayList<>(), aDescription = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_plans_list);

        dontHaveTrainingPlansTV = findViewById(R.id.dontHaveTrainingPlansTV);
        dontHaveTrainingPlansTV.setVisibility(View.GONE);

        makeListFunc();

        openAddDialog = findViewById(R.id.openAddDialog);

        builder = new AlertDialog.Builder(this);

    }

    public void makeListFunc() {
        Boolean isAdminVar = sharedPref.isAdmin();
        //checking the position of the user
        int i;
        boolean stopLoop = false;
        //checking if user
        if (isAdminVar) {
            for (i = 0; i < DataModel.admins.size(); i++) {
                if (sharedPref.GetUsername().equals(DataModel.admins.get(i).getUsername())) {
                    stopLoop = true;
                    break;
                }
            }
            if (DataModel.admins.get(i).getTrainingPlansList() != null) {
                for (int j = 0; j < DataModel.admins.get(i).getTrainingPlansList().size(); j++) {
                    aTitle.add(DataModel.admins.get(i).getTrainingPlansList().get(j).getNameOfTrainingPlan());
                    aDescription.add(DataModel.admins.get(i).getTrainingPlansList().get(j).getTypeOfTrainingPlan());
                }
                adapter3 = new MyListAdapter(this, aTitle, aDescription);

                trainingPlansList = findViewById(R.id.LTest);
                trainingPlansList.setAdapter(adapter3);
            } else {
                dontHaveTrainingPlansTV.setVisibility(View.VISIBLE);
            }
        } else {
            for (i = 0; i < DataModel.users.size() && !stopLoop; i++) {
                if (DataModel.users.get(i).getUsername() == sharedPref.GetUsername()) {
                    stopLoop = true;
                }
            }
            if (DataModel.users.get(i).getTrainingPlansList() != null) {
                for (int j = 0; j < DataModel.users.get(i).getTrainingPlansList().size(); j++) {
                    aTitle.add(DataModel.users.get(i).getTrainingPlansList().get(j).getNameOfTrainingPlan());
                    aDescription.add(DataModel.users.get(i).getTrainingPlansList().get(j).getTypeOfTrainingPlan());
                }
                adapter3 = new MyListAdapter(this, aTitle, aDescription);

                trainingPlansList = findViewById(R.id.LTest);
                trainingPlansList.setAdapter(adapter3);
            } else {
                dontHaveTrainingPlansTV.setVisibility(View.VISIBLE);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);

        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            item.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
        MenuItem item;

        if (sharedPref.GetUsername().equals("YouRGuest")) {
            item = menu.getItem(0);
            item.setEnabled(false);
            item.setVisible(false);

            item = menu.getItem(3);
            item.setEnabled(false);
            item.setVisible(false);


        } else {

            item = menu.getItem(1);
            item.setEnabled(false);
            item.setVisible(false);

            item = menu.getItem(2);
            item.setEnabled(false);
            item.setVisible(false);

            item = menu.getItem(0);
            item.setTitle(sharedPref.GetUsername());
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_login) {
            Intent intent = new Intent(this, LoginPage.class);
            startActivityForResult(intent, 0);
            //Toast.makeText(this,"you selected login",Toast.LENGTH_LONG).show();
            return true;
        } else if (id == R.id.action_register) {
            Intent intent = new Intent(this, SignUp.class);
            startActivityForResult(intent, 0);
            return true;
        } else if (id == R.id.action_exit) {
            builder.setMessage("Do you want to logout?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                            sharedPref.SetUsername("YouRGuest");
                            Toast.makeText(getApplicationContext(), "You logged out",
                                    Toast.LENGTH_SHORT).show();
                            restartAppToHomePage();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //  Action for 'NO' Button
                            dialog.cancel();
                            Toast.makeText(getApplicationContext(), "You canceled the logout",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.setTitle("Logout");
            alert.show();
        } else if (id == R.id.action_GoHome) {
            finish();
            return true;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }

    void restartapp() {
        Intent i = new Intent(getApplicationContext(), TrainingPlansListActivity.class);
        i.putExtra("WE", getIntent().getIntExtra("WE", 0));
        startActivity(i);
        finish();
    }

    void restartAppToHomePage(){
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
     //   i.putExtra("WE", getIntent().getIntExtra("WE", 0));
        startActivity(i);
        finish();
    }
}
