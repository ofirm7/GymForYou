package com.example.gymforyou;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class ExerciseActivity extends AppCompatActivity implements View.OnClickListener {

    SharedPref sharedPref;
    TextView nameOfExerciseTv, exerciseCreatorTv, exerciseDescriptionTv, exerciseDetailsTv;
    AlertDialog.Builder builder;
    Button openAddVideo, editDetails, removeDetails, submitDetails, addVideo, deleteVideo;
    EditText exerciseDetailsEt, urlEv;
    TextView noVideoTv;
    Dialog addDialog;

    static String url = null;
    VideoView exerciseVideoExample;
    Uri uriVideo;
    MediaController mc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        openAddVideo = findViewById(R.id.addVideo);

        nameOfExerciseTv = findViewById(R.id.nameOfExerciseTv);
        nameOfExerciseTv.setText(DataModel.muscles.get(getIntent().getIntExtra("WESEC", 0))
                .getExercisesList().get(getIntent().getIntExtra("ELTOE", 0)).getName());

        exerciseCreatorTv = findViewById(R.id.exerciseCreatorTv);
        exerciseCreatorTv.setText(" BY " + DataModel.muscles.get(getIntent().getIntExtra("WESEC", 0))
                .getExercisesList().get(getIntent().getIntExtra("ELTOE", 0)).getCreator());

        exerciseDescriptionTv = findViewById(R.id.exerciseDescriptionTv);
        exerciseDescriptionTv.setText(" " + DataModel.muscles.get(getIntent().getIntExtra("WESEC", 0))
                .getExercisesList().get(getIntent().getIntExtra("ELTOE", 0)).getDescriptionOfExercise() + " ");

        exerciseDetailsTv = findViewById(R.id.exerciseDetailsTv);
        exerciseDetailsEt = findViewById(R.id.exerciseDetailsEt);
        exerciseDetailsEt.setVisibility(View.GONE);

        exerciseDetailsTv.setText(DataModel.muscles.get(getIntent().getIntExtra("WESEC", 0))
                .getExercisesList().get(getIntent().getIntExtra("ELTOE", 0)).getDetailsOfExercise());
        exerciseDetailsEt.setText(exerciseDetailsTv.getText());

        submitDetails = findViewById(R.id.submitDetails);
        submitDetails.setVisibility(View.GONE);

        editDetails = findViewById(R.id.editDetails);
        removeDetails = findViewById(R.id.removeDetails);

        noVideoTv = findViewById(R.id.noVideoTv);
        noVideoTv.setVisibility(View.GONE);

        deleteVideo = findViewById(R.id.deleteVideo);

        //Video

        exerciseVideoExample = findViewById(R.id.exerciseVideoExample);
        openAddVideo = findViewById(R.id.addVideo);

        url = DataModel.muscles.get(getIntent().getIntExtra("WESEC", 0))
                .getExercisesList().get(getIntent().getIntExtra("ELTOE", 0)).getUrl();

        if (url == null) {
            exerciseVideoExample.setVisibility(View.GONE);
//            mc.setEnabled(false);
            if (isCreatorOrAdmin())
            {
                openAddVideo.setVisibility(View.VISIBLE);
            }
            noVideoTv.setVisibility(View.VISIBLE);
            deleteVideo.setVisibility(View.GONE);
        } else {
            if (isCreatorOrAdmin()) {
                openAddVideo.setText(" Change video");
            } else {
                openAddVideo.setVisibility(View.GONE);
                deleteVideo.setVisibility(View.GONE);
            }

            mc = (MediaController) findViewById(R.id.mc);
            uriVideo = Uri.parse(url);
            exerciseVideoExample.setVideoURI(uriVideo);
            exerciseVideoExample.setMediaController(new MediaController(this));
            exerciseVideoExample.start();
            exerciseVideoExample.requestFocus();
        }

        //

        if (!sharedPref.GetUsername().equals(DataModel.muscles.get(getIntent().getIntExtra("WESEC", 0))
                .getExercisesList().get(getIntent().getIntExtra("ELTOE", 0)).getCreator()) &&
                !sharedPref.GetUsername().equals("admin")) {
            removeDetails.setVisibility(View.GONE);
            editDetails.setVisibility(View.GONE);
        } else {
            removeDetails.setOnClickListener(this);
            editDetails.setOnClickListener(this);
        }

        submitDetails.setOnClickListener(this);
        openAddVideo.setOnClickListener(this);
        deleteVideo.setOnClickListener(this);

        builder = new AlertDialog.Builder(this);
    }

    @Override
    public void onClick(View view) {
        if (view == editDetails) {
            exerciseDetailsEt.setVisibility(View.VISIBLE);
            exerciseDetailsTv.setVisibility(View.GONE);

            submitDetails.setVisibility(View.VISIBLE);
            editDetails.setVisibility(View.GONE);
        } else if (view == submitDetails) {
            DataModel.muscles.get(getIntent().getIntExtra("WESEC", 0))
                    .getExercisesList().get(getIntent().getIntExtra("ELTOE", 0)).addDetailsOfExercise(exerciseDetailsEt.getText().toString());

            DataModel.muscleSave();

            restartapp();
        } else if (view == removeDetails) {
            DataModel.muscles.get(getIntent().getIntExtra("WESEC", 0))
                    .getExercisesList().get(getIntent().getIntExtra("ELTOE", 0)).setDetailsOfExercise("");

            DataModel.muscleSave();

            restartapp();
        } else if (view == openAddVideo) {
            OpenAddVideoDialog();
        }
        else if(view == deleteVideo)
        {
            builder.setMessage("Do you want to delete this video?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();

                            DataModel.muscles.get(getIntent().getIntExtra("WESEC", 0))
                                    .getExercisesList().get(getIntent().getIntExtra("ELTOE", 0)).setUrl(null);
                            DataModel.muscleSave();

                            Toast.makeText(getApplicationContext(), "You deleted the video",
                                    Toast.LENGTH_SHORT).show();
                            restartapp();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //  Action for 'NO' Button
                            dialog.cancel();
                            Toast.makeText(getApplicationContext(), "You canceled the delete",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.setTitle("Delete Video");
            alert.show();
        }
    }

    public void OpenAddVideoDialog() {
        addDialog = new Dialog(this);
        addDialog.setContentView(R.layout.custom_dialog_add_video_url);
        addDialog.setTitle("Add Video");

        addDialog.setCancelable(true);

        urlEv = addDialog.findViewById(R.id.urlEv);
        addVideo = addDialog.findViewById(R.id.addVideo);

        addVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v == addVideo)
                {
                    DataModel.muscles.get(getIntent().getIntExtra("WESEC", 0))
                            .getExercisesList().get(getIntent().getIntExtra("ELTOE", 0)).setUrl(urlEv.getText().toString());
                    DataModel.muscleSave();
                    addDialog.dismiss();
                    restartapp();
                }
            }
        });

        addDialog.show();
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

    boolean isCreatorOrAdmin()
    {
        if (sharedPref.GetUsername().equals(DataModel.muscles.get(getIntent().getIntExtra("WESEC", 0))
                .getExercisesList().get(getIntent().getIntExtra("ELTOE", 0)).getCreator())
                || sharedPref.GetUsername().equals("admin"))
            return true;
        return false;
    }

    void restartapp() {
        Intent i = getIntent();
        startActivity(i);
        finish();
    }

    void hideThings() {

    }

}