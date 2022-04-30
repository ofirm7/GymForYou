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

public class ExercisesListActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    SharedPref sharedPref;
    TextView nameOfMuscleTv, creatorTv;
    AlertDialog.Builder builder;
    ListView l2;
    MyListAdapter adapter2;
    ArrayList<String> aTitle = new ArrayList<>(), aDescription = new ArrayList<>();
    Dialog addDialog;
    EditText nameOfExercise, descriptionOfExercise;
    Button openAddDialog, addExercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises_list);

        nameOfMuscleTv = findViewById(R.id.nameOfMuscleTv);
        nameOfMuscleTv.setText(DataModel.muscles.get(getIntent().getIntExtra("WE", 0)).getName());

        if (DataModel.muscles.get(getIntent().getIntExtra("WE", 0)).getExercisesList() != null)
        {
            for (int i = 0; i < DataModel.muscles.get(getIntent().getIntExtra("WE", 0)).getExercisesList().size(); i++) {
                aTitle.add(DataModel.muscles.get(getIntent().getIntExtra("WE", 0)).getExercisesList().get(i).getName());
                aDescription.add(DataModel.muscles.get(getIntent().getIntExtra("WE", 0)).getExercisesList().get(i).getCreator());
            }
        }

        creatorTv = findViewById(R.id.creatorTv);
        creatorTv.setText("By " + DataModel.muscles.get(getIntent().getIntExtra("WE", 0)).getCreator());

        builder = new AlertDialog.Builder(this);

        adapter2 = new MyListAdapter(this, aTitle, aDescription);

        openAddDialog = findViewById(R.id.openAddExerciseDialog);

        l2 = findViewById(R.id.ExercisesList);
        l2.setAdapter(adapter2);

        openAddDialog.setOnClickListener(this);
        l2.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view == openAddDialog) {
            if (sharedPref.GetUsername().equals("YouRGuest")) {
                builder.setMessage("This option is only for users")
                        .setCancelable(false)
                        .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(ExercisesListActivity.this, LoginPageActivity.class);
                                startActivityForResult(intent, 0);
                            }
                        })
                        .setNegativeButton("back", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.setTitle("Add Exercise");
                alert.show();
            } else {
                OpenAddMuscleDialog();
            }
        }
    }

    public void OpenAddMuscleDialog() {
        addDialog = new Dialog(this);
        addDialog.setContentView(R.layout.custom_dialog_add_exercise);
        addDialog.setTitle("Add Exercise");

        addDialog.setCancelable(true);

        nameOfExercise= addDialog.findViewById(R.id.nameOfExercise);
        descriptionOfExercise = addDialog.findViewById(R.id.descriptionOfExercise);
        addExercise = addDialog.findViewById(R.id.addExercise);


        addExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v == addExercise)
                {
                    if (DataModel.muscles.get(getIntent().getIntExtra("WE", 0)).getExercisesList() != null)
                    {
                        DataModel.muscles.get(getIntent().getIntExtra("WE", 0)).addExercise(
                                new Exercise(nameOfExercise.getText().toString(),
                                        sharedPref.GetUsername(),
                                        descriptionOfExercise.getText().toString(),
                                        null, null, null, "false"));
                    }
                    else
                    {
                        ArrayList<Exercise> temp = new ArrayList<>();
                        temp.add(new Exercise(nameOfExercise.getText().toString(),
                                sharedPref.GetUsername(),
                                descriptionOfExercise.getText().toString(),
                                null, null, null, "false"));
                        DataModel.muscles.get(getIntent().getIntExtra("WE", 0)).setExercisesList(temp);
                    }
                    DataModel.muscleSave();
                    addDialog.dismiss();
                    restartapp();
                }
            }
        });

        addDialog.show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent1 = new Intent(this, ExerciseActivity.class);
        intent1.putExtra("ELTOE", position);
        intent1.putExtra("WESEC", getIntent().getIntExtra("WE", 0));
        startActivity(intent1);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);

        for(int i=0;i<menu.size();i++)
        {
            MenuItem item= menu.getItem(i);
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
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.action_login) {
            Intent intent=new Intent(this, LoginPageActivity.class);
            startActivityForResult(intent, 0);
            //Toast.makeText(this,"you selected login",Toast.LENGTH_LONG).show();
            return true;
        }
        else if (id == R.id.action_register) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivityForResult(intent, 0);
        return true;
        }
        else if (id == R.id.action_exit){
            builder.setMessage("Do you want to logout?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                            sharedPref.SetUsername("YouRGuest");
                            Toast.makeText(getApplicationContext(), "You logged out",
                                    Toast.LENGTH_SHORT).show();
                            restartapp();
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
        }
        else if (id == R.id.action_GoHome) {
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
        Intent i = new Intent(getApplicationContext(), ExercisesListActivity.class);
        i.putExtra("WE", getIntent().getIntExtra("WE", 0));
        startActivity(i);
        finish();
    }
}