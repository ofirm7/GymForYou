package com.example.gymforyou;

import com.google.firebase.database.FirebaseDatabase;

import java.security.acl.Group;
import java.util.ArrayList;

public class DataModel {

    static public ArrayList<User> users = new ArrayList<>();
    static public  ArrayList<Muscle> muscles = new ArrayList<>();
    public static void userSave()
    {
        FirebaseDatabase.getInstance().getReference("user").setValue(DataModel.users);

    }
    public  static void  muscleSave()
    {
        FirebaseDatabase.getInstance().getReference("muscles").setValue(DataModel.muscles);
    }


}
