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
import android.widget.Toast;

import java.util.ArrayList;

public class ExercisesListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    ListView l1;
    MyListAdapter adapter1;
    ArrayList<String> aTitle = new ArrayList<>(), aDescription = new ArrayList<>();
    SharedPref sharedPref;
    AlertDialog.Builder builder;
    Button openAddDialog, addMuscle;
    EditText nameOfMuscle;
    Dialog addDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises_list);

        for (int i = 0; i < DataModel.muscles.size(); i++) {
            aTitle.add(DataModel.muscles.get(i).getName());
            aDescription.add(DataModel.muscles.get(i).getCreator());
        }

        adapter1 = new MyListAdapter(this, aTitle, aDescription);

        l1 = findViewById(R.id.LTest);
        l1.setAdapter(adapter1);

        openAddDialog = findViewById(R.id.openAddDialog);

        builder = new AlertDialog.Builder(this);

        openAddDialog.setOnClickListener(this);
        l1.setOnItemClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view == openAddDialog) {
            if (sharedPref.GetUsername().equals("YouRGuest")) {
                builder.setMessage("This option is only for users")
                        .setCancelable(false)
                        .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(ExercisesListActivity.this, LoginPage.class);
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
                alert.setTitle("Add Muscle");
                alert.show();
            } else {
                OpenAddMuscleDialog();
            }
        } else if (view == addMuscle) {

            DataModel.muscles.add(new Muscle(nameOfMuscle.getText().toString(), sharedPref.GetUsername(), ""));
            DataModel.muscleSave();
            addDialog.dismiss();
        }
    }

    public void OpenAddMuscleDialog() {
        addDialog = new Dialog(this);
        addDialog.setContentView(R.layout.custom_dialog_add_muscle);
        addDialog.setTitle("Add Muscle");

        nameOfMuscle = findViewById(R.id.nameOfMuscle);
        addMuscle = findViewById(R.id.addMuscle);


        addMuscle.setOnClickListener(this);

        addDialog.show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        boolean startActivityBool = true;
        Intent intent1 = new Intent(this, ExerciseDetailsActivity.class);
        switch (position) {
            case 0:
                intent1.putExtra("WE", "Chest");
                break;
            case 1:
                intent1.putExtra("WE", "Back");
                break;
            case 2:
                intent1.putExtra("WE", "Exercise 3");
                break;
            case 3:
                intent1.putExtra("WE", "Exercise 4");
                break;
        }


        if (startActivityBool) {
            startActivity(intent1);
            finish();
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
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }
}