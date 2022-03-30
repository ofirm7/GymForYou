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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class TrainingPlansListActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    SharedPref sharedPref;
    AlertDialog.Builder builder;
    TextView dontHaveTrainingPlansTV;

    //var that says if the user is admin or just a user
    Boolean isAdminVar;

    //checking the position of the user or admin (makeListFunc find it and put the right value)
    int i;

    //for the list
    ListView trainingPlansList;
    MyListAdapter adapter3;
    ArrayList<String> aTitle = new ArrayList<>(), aDescription = new ArrayList<>();

    //for the addDialog
    Dialog addDialog;
    Button openAddDialog, addTrainingPlan;
    EditText nameOfTrainingPlan, typeOfTrainingPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_plans_list);

        dontHaveTrainingPlansTV = findViewById(R.id.dontHaveTrainingPlansTV);
        dontHaveTrainingPlansTV.setVisibility(View.GONE);

        trainingPlansList = findViewById(R.id.LTrainingsPlans);

        isAdminVar = sharedPref.isAdmin();
        makeListFunc();

        openAddDialog = findViewById(R.id.openAddDialog);

        builder = new AlertDialog.Builder(this);

        trainingPlansList.setOnItemClickListener(this);
        openAddDialog.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if (view == openAddDialog) {
            openAddTrainingPlanDialog();
        }
        else if (view == addTrainingPlan) {
            if (isAdminVar) {
                if (DataModel.admins.get(i).getTrainingPlansList() != null) {
                    DataModel.admins.get(i).getTrainingPlansList().add(
                            new TrainingPlan(nameOfTrainingPlan.getText().toString(), DataModel.admins.get(i).getUsername(),
                                    null, typeOfTrainingPlan.getText().toString()));
                }
                else{

                    TrainingPlan firstT = new TrainingPlan(nameOfTrainingPlan.getText().toString(), DataModel.admins.get(i).getUsername(),
                            null, typeOfTrainingPlan.getText().toString());
                    ArrayList<TrainingPlan> tempArr = new ArrayList<TrainingPlan>();
                    tempArr.add(firstT);
                    DataModel.admins.get(i).setTrainingPlansList(tempArr);
                }
                DataModel.adminsSave();
                addDialog.dismiss();
                restartapp();
            } else {
                if (DataModel.users.get(i).getTrainingPlansList() != null) {
                    DataModel.users.get(i).getTrainingPlansList().add(
                            new TrainingPlan(nameOfTrainingPlan.getText().toString(), DataModel.users.get(i).getUsername(),
                                    null, typeOfTrainingPlan.getText().toString()));
                }
                else{

                    TrainingPlan firstT = new TrainingPlan(nameOfTrainingPlan.getText().toString(), DataModel.users.get(i).getUsername(),
                            null, typeOfTrainingPlan.getText().toString());
                    ArrayList<TrainingPlan> tempArr = new ArrayList<TrainingPlan>();
                    tempArr.add(firstT);
                    DataModel.users.get(i).setTrainingPlansList(tempArr);
                }
                DataModel.userSave();
                addDialog.dismiss();
                restartapp();
            }
        }
    }

    public void openAddTrainingPlanDialog() {
        addDialog = new Dialog(this);
        addDialog.setContentView(R.layout.custom_dialog_add_training_plan);
        addDialog.setTitle("Name Of Training Plan");

        addDialog.setCancelable(true);

        typeOfTrainingPlan = addDialog.findViewById(R.id.typeOfTrainingPlan);
        nameOfTrainingPlan = addDialog.findViewById(R.id.nameOfTrainingPlan);
        addTrainingPlan = addDialog.findViewById(R.id.addTrainingPlan);

        addTrainingPlan.setOnClickListener(this);

        addDialog.show();
    }

    public void makeListFunc() {
        boolean stopLoop = false;
        //checking if user
        if (isAdminVar) {
            for (i = 0; i < DataModel.admins.size(); i++) {
                if (sharedPref.GetUsername().equals(DataModel.admins.get(i).getUsername())) {
                    break;
                }
            }
            if (DataModel.admins.get(i).getTrainingPlansList() != null) {
                for (int j = 0; j < DataModel.admins.get(i).getTrainingPlansList().size(); j++) {
                    aTitle.add(DataModel.admins.get(i).getTrainingPlansList().get(j).getNameOfTrainingPlan());
                    aDescription.add(DataModel.admins.get(i).getTrainingPlansList().get(j).getTypeOfTrainingPlan());
                }
                adapter3 = new MyListAdapter(this, aTitle, aDescription);

                trainingPlansList = findViewById(R.id.LTrainingsPlans);
                trainingPlansList.setAdapter(adapter3);
            } else {
                dontHaveTrainingPlansTV.setVisibility(View.VISIBLE);
            }
        } else {
            for (i = 0; i < DataModel.users.size() && !stopLoop; i++) {
                String s=DataModel.users.get(i).getUsername();
                if (DataModel.users.get(i).getUsername().equals(sharedPref.GetUsername())) {
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent1 = new Intent(this, TrainingPlanPageActivity.class);
        intent1.putExtra("TONEXT", position);
        intent1.putExtra("USERPOSITION", i);
        startActivity(intent1);
        finish();
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
            //item.setTitle(sharedPref.GetUsername());
            item.setTitle(MainActivity.usernameFromInternalFileString);
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

    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }*/

    void restartapp() {
        Intent i = new Intent(getApplicationContext(), TrainingPlansListActivity.class);
        i.putExtra("WE", getIntent().getIntExtra("WE", 0));
        startActivity(i);
        finish();
    }

    void restartAppToHomePage() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        //   i.putExtra("WE", getIntent().getIntExtra("WE", 0));
        startActivity(i);
        finish();
    }
}
