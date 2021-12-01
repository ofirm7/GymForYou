package com.example.gymforyou;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class ExerciseActivity extends AppCompatActivity implements View.OnClickListener {

    SharedPref sharedPref;
    TextView nameOfExerciseTv, exerciseCreatorTv, exerciseDescriptionTv;
    AlertDialog.Builder builder;
    VideoView exerciseVideoExample;
    Button startVideoOrAddVideo;
    Button addDetails;
    Button removeDetails;

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

        addDetails = findViewById(R.id.addDetails);
        removeDetails = findViewById(R.id.removeDetails);

        //Video
        exerciseVideoExample = findViewById(R.id.exerciseVideoExample);

        startVideoOrAddVideo = findViewById(R.id.startVideoOrAddVideo);

        if (exerciseVideoExample == null) {
            startVideoOrAddVideo.setText(" Add video");
        } else {
            startVideoOrAddVideo.setText("play");
        }

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(exerciseVideoExample);

        Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath() + "VID-20200121-WA0034[1].mp4");

        exerciseVideoExample.setMediaController(mediaController);
        exerciseVideoExample.setVideoURI(uri);
        exerciseVideoExample.requestFocus();
        exerciseVideoExample.start();

        startVideoOrAddVideo.setOnClickListener(this);

        if (!sharedPref.GetUsername().equals(DataModel.muscles.get(getIntent().getIntExtra("WESEC", 0))
                .getExercisesList().get(getIntent().getIntExtra("ELTOE", 0)).getCreator()) &&
                !sharedPref.GetUsername().equals("admin"))
        {
            removeDetails.setVisibility(View.GONE);
            addDetails.setVisibility(View.GONE);
        }
        else
        {
            removeDetails.setOnClickListener(this);
            addDetails.setOnClickListener(this);
        }

        builder = new AlertDialog.Builder(this);
    }

    @Override
    public void onClick(View view) {

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
        Intent i = new Intent(getApplicationContext(), ExerciseActivity.class);
        startActivity(i);
        finish();
    }

    void hideThings()
    {

    }

}