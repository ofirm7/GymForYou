package com.example.gymforyou;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class ExerciseActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton downloadVideoIB;
    ImageView afterDownloadIV;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    StorageReference storageReference2;

    SharedPref sharedPref;
    TextView nameOfExerciseTv, exerciseCreatorTv, exerciseDescriptionTv, exerciseDetailsTv;
    AlertDialog.Builder builder;
    Button changeOrAddVideo, editDetails, removeDetails, submitDetails, addVideo, deleteVideo;
    EditText exerciseDetailsEt, urlEv;
    TextView noVideoTv;
    Dialog addDialog;

    ProgressDialog progressDialog;

    static String url = null;
    VideoView exerciseVideoExample;
    Uri uriVideo;
    MediaController mc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        changeOrAddVideo = findViewById(R.id.addVideo);

        nameOfExerciseTv = findViewById(R.id.nameOfExerciseTv);
        nameOfExerciseTv.setText(DataModel.muscles.get(getIntent().getIntExtra("WESEC", 0))
                .getExercisesList().get(getIntent().getIntExtra("ELTOE", 0)).getName());

        exerciseCreatorTv = findViewById(R.id.exerciseCreatorTv);
        exerciseCreatorTv.setText(" BY " + DataModel.muscles.get(getIntent().getIntExtra("WESEC", 0))
                .getExercisesList().get(getIntent().getIntExtra("ELTOE", 0)).getCreator());

        exerciseDescriptionTv = findViewById(R.id.exerciseDescriptionTv);
        exerciseDescriptionTv.setText(" " + DataModel.muscles.get(getIntent().getIntExtra("WESEC", 0))
                .getExercisesList().get(getIntent().getIntExtra("ELTOE", 0)).getDescriptionOfExercise() + " ");
        if (exerciseDescriptionTv.getText().toString().length() <=3)
        {
            exerciseDescriptionTv.setVisibility(View.GONE);
        }

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
        changeOrAddVideo = findViewById(R.id.addVideo);

        url = DataModel.muscles.get(getIntent().getIntExtra("WESEC", 0))
                .getExercisesList().get(getIntent().getIntExtra("ELTOE", 0)).getUrl();

        changeOrAddVideo.setVisibility(View.GONE);
        if (url == null) {
            exerciseVideoExample.setVisibility(View.GONE);
//            mc.setEnabled(false);
            if (isCreatorOrAdmin())
            {
                changeOrAddVideo.setVisibility(View.VISIBLE);
            }
            noVideoTv.setVisibility(View.VISIBLE);
            deleteVideo.setVisibility(View.GONE);
        } else {
            if (isCreatorOrAdmin()) {
                changeOrAddVideo.setVisibility(View.VISIBLE);
                changeOrAddVideo.setText(" Change video");
            } else {
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

        //Download video
        downloadVideoIB = findViewById(R.id.downloadVideo);
        downloadVideoIB.setOnClickListener(this);
        if (url == null)
        {
            downloadVideoIB.setVisibility(View.GONE);
        }

        afterDownloadIV = findViewById(R.id.afterDownloadImage);
        afterDownloadIV.setVisibility(View.GONE);
        //

        submitDetails.setOnClickListener(this);
        changeOrAddVideo.setOnClickListener(this);
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
        } else if (view == changeOrAddVideo) {
            // Code for showing progressDialog while uploading
            progressDialog = new ProgressDialog(ExerciseActivity.this);
            chooseVideo();
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
        else if (view == downloadVideoIB)
        {
            downloadVideoFunc();
        }
    }

    public void downloadVideoFunc()
    {
        storageReference = firebaseStorage.getInstance().getReference("Files");
        String videoName = DataModel.muscles.get(getIntent().getIntExtra("WESEC", 0))
                .getExercisesList().get(getIntent().getIntExtra("ELTOE", 0)).getUrl().split("%2F")[1].split("alt")[0];
        videoName = videoName.substring(0, videoName.length()-1);
        storageReference2 = storageReference.child(videoName);

        String finalVideoName = videoName;
        storageReference2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url = uri.toString();
                String name = finalVideoName.substring(0,finalVideoName.length()-4);
                downloadVideoInternalFunc(ExerciseActivity.this , name, ".mp4", DIRECTORY_DOWNLOADS, url);
                downloadVideoIB.setVisibility(View.GONE);
                afterDownloadIV.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Error",e.getMessage());
            }
        });
    }

    public void downloadVideoInternalFunc(Context context, String fileName,
                                          String fileExtension, String destinationDirectory,
                                          String url)
    {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri =Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destinationDirectory, fileName + fileExtension);

        downloadManager.enqueue(request);
    }


    // choose a video from phone storage
    private void chooseVideo() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 5);
    }

    Uri videouri;

    // startActivityForResult is used to receive the result, which is the selected video.
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 5 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            videouri = data.getData();
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            uploadvideo();
        }
    }

    private String getfiletype(Uri videouri) {
        ContentResolver r = getContentResolver();
        // get the file type ,in this case its mp4
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(r.getType(videouri));
    }

    private void uploadvideo() {
        if (videouri != null) {
            // save the selected video in Firebase storage
            final StorageReference reference = FirebaseStorage.getInstance().getReference("Files/" + System.currentTimeMillis() + "." + getfiletype(videouri));
            reference.putFile(videouri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isSuccessful()) ;
                    // get the link of video
                    String downloadUri = uriTask.getResult().toString();
                    DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Video");
                    HashMap<String, String> map = new HashMap<>();
                    map.put("videolink", downloadUri);
                    reference1.child("" + System.currentTimeMillis()).setValue(map);
                    // Video uploaded successfully
                    // Dismiss dialog
                    progressDialog.dismiss();
                    Toast.makeText(ExerciseActivity.this, "Video Uploaded!!", Toast.LENGTH_SHORT).show();
                    DataModel.muscles.get(getIntent().getIntExtra("WESEC", 0))
                            .getExercisesList().get(getIntent().getIntExtra("ELTOE",
                            0)).setUrl(downloadUri);
                    DataModel.muscleSave();
                    restartapp();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Error, Image not uploaded
                    progressDialog.dismiss();
                    Toast.makeText(ExerciseActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                // Progress Listener for loading
                // percentage on the dialog box
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    // show the progress bar
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded " + (int) progress + "%");
                }
            });
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
            //item.setTitle(sharedPref.GetUsername());
            item.setTitle(MainActivity.usernameFromInternalFileString);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_login) {
            Intent intent = new Intent(this, LoginPageActivity.class);
            startActivityForResult(intent, 0);
            //Toast.makeText(this,"you selected login",Toast.LENGTH_LONG).show();
            return true;
        } else if (id == R.id.action_register) {
            Intent intent = new Intent(this, SignUpActivity.class);
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