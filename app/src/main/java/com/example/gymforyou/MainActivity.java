package com.example.gymforyou;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button toExercises, toGyms, toUsersList, toTrainingPlans;
    SharedPref sharedPref;
    AlertDialog.Builder builder;

    LinearLayout adminsLayout;
    BroadcastReceiver BRNetwork;

    InputStream in;
    //Global var for username menu item in other pages
    public static String usernameFromInternalFileString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedPref = new SharedPref(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BRNetwork = new NetworkChangeReceiver();

        try {
            in=openFileInput("userNameInternalFile");
            byte[]buffer=new byte[4096];
            try {
                in.read(buffer);
                usernameFromInternalFileString = new String(buffer);
                in.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }


        toGyms = findViewById(R.id.toGyms);
        toExercises = findViewById(R.id.toExercises);
        toTrainingPlans = findViewById(R.id.toTrainingPlans);

        builder = new AlertDialog.Builder(this);

        toUsersList = findViewById(R.id.toUsersList);

        adminsLayout = findViewById(R.id.adminsLayout);

        sharedPref.UpdateAdmin();
        if (!sharedPref.isAdmin()) {
            adminsLayout.setVisibility(View.GONE);
        }

        toTrainingPlans.setOnClickListener(this);
        toUsersList.setOnClickListener(this);
        toGyms.setOnClickListener(this);
        toExercises.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == toExercises) {
            Intent intent1 = new Intent(this, MusclesListActivity.class);
            startActivity(intent1);
        } else if (view == toGyms) {
            if (sharedPref.GetUsername().equals("YouRGuest")) {
                builder.setMessage("This page is only for users")
                        .setCancelable(false)
                        .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(MainActivity.this, LoginPage.class);
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
                alert.setTitle("Gyms");
                alert.show();
            } else {
                // Search for gyms nearby
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=gym");

                // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

                // Make the Intent explicit by setting the Google Maps package
                mapIntent.setPackage("com.google.android.apps.maps");

                // Attempt to start an activity that can handle the Intent
                startActivity(mapIntent);

                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }
        } else if (view == toUsersList) {
            Intent intent = new Intent(this, UsersListActivity.class);
            startActivityForResult(intent, 0);
        } else if (view == toTrainingPlans) {
            if (sharedPref.GetUsername().equals("YouRGuest")) {
                builder.setMessage("This page is only for users")
                        .setCancelable(false)
                        .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(MainActivity.this, LoginPage.class);
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
                alert.setTitle("Training Plans");
                alert.show();
            } else {
                Intent intent = new Intent(this, TrainingPlansListActivity.class);
                startActivityForResult(intent, 0);
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
        item = menu.getItem(4);
        item.setEnabled(false);
        item.setVisible(false);

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
            item.setTitle(usernameFromInternalFileString);
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