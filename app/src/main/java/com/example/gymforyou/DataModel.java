package com.example.gymforyou;

import com.google.firebase.database.FirebaseDatabase;

import java.security.acl.Group;
import java.util.ArrayList;

public class DataModel {

    static public ArrayList<User> users = new ArrayList<>();

    public static void save()
    {
        FirebaseDatabase.getInstance().getReference("user").setValue(DataModel.users);
    }

}
