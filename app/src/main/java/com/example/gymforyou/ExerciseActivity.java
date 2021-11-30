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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ExerciseActivity extends AppCompatActivity {

    SharedPref sharedPref;
    TextView nameOfExerciseTv, exerciseCreatorTv, exerciseDescriptionTv;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        nameOfExerciseTv = findViewById(R.id.nameOfExerciseTv);
        nameOfExerciseTv.setText(DataModel.muscles.get(getIntent().getIntExtra("WESEC", 0))
                .getExercisesList().get(getIntent().getIntExtra("ELTOE", 0)).getName());

        exerciseCreatorTv = findViewById(R.id.exerciseCreatorTv);
        exerciseCreatorTv.setText(" BY " + DataModel.muscles.get(getIntent().getIntExtra("WESEC", 0))
                .getExercisesList().get(getIntent().getIntExtra("ELTOE", 0)).getCreator());

        exerciseDescriptionTv = findViewById(R.id.exerciseDescriptionTv);
        exerciseDescriptionTv.setText(" " + DataModel.muscles.get(getIntent().getIntExtra("WESEC", 0))
                .getExercisesList().get(getIntent().getIntExtra("ELTOE", 0)).getDescriptionOfExercise() + " ");

        builder = new AlertDialog.Builder(this);
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
            item.setTitle(sharedPref.GetUsername());
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.action_login) {
            Intent intent=new Intent(this,LoginPage.class);
            startActivityForResult(intent, 0);
            //Toast.makeText(this,"you selected login",Toast.LENGTH_LONG).show();
            return true;
        }
        else if (id == R.id.action_register) {
            Intent intent = new Intent(this, SignUp.class);
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
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }
}