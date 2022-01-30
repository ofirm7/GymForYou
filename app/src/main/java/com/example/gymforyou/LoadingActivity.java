package com.example.gymforyou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;
import java.util.ArrayList;

public class LoadingActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference dbUserRef;
    DatabaseReference dbMuscleRef;
    DatabaseReference dbAdminsRef;
    BroadcastReceiver BRNetwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        BRNetwork = new NetworkChangeReceiver();
        registerNetworkBroadcastReciever();
        database = FirebaseDatabase.getInstance();
        dbUserRef = database.getReference("user");
        dbMuscleRef = database.getReference("muscles");
        dbAdminsRef = database.getReference("admins");

        dbMuscleRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<ArrayList<Muscle>> t = new GenericTypeIndicator<ArrayList<Muscle>>() {
                };
                ArrayList<Muscle> fbMuscle = dataSnapshot.getValue(t);
                DataModel.muscles.clear();
                DataModel.muscles.addAll(fbMuscle);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        dbUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                GenericTypeIndicator<ArrayList<User>> t = new GenericTypeIndicator<ArrayList<User>>() {
                };
                ArrayList<User> fbUsers = dataSnapshot.getValue(t);
                DataModel.users.clear();
                DataModel.users.addAll(fbUsers);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("GymForYou", "Failed to read value.", error.toException());
            }
        });

        dbAdminsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                GenericTypeIndicator<ArrayList<Admin>> t = new GenericTypeIndicator<ArrayList<Admin>>() {
                };
                ArrayList<Admin> fbAdmins = snapshot.getValue(t);
                DataModel.admins.clear();
                DataModel.admins.addAll(fbAdmins);

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivityForResult(intent, 0);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }//End of OnCreate

    protected void registerNetworkBroadcastReciever() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registerReceiver(BRNetwork, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            registerReceiver(BRNetwork, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }
    protected void unregisterNetwork() {
        try {
            unregisterReceiver(BRNetwork);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

}