package com.example.gymforyou;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    SharedPreferences mySharedPrefrences;


    public SharedPref(Context context) {
        mySharedPrefrences = context.getSharedPreferences("filename", context.MODE_PRIVATE);
    }

    public void SetUsername(String username) {
        SharedPreferences.Editor editor = mySharedPrefrences.edit();
        editor.putString("username", username);
        editor.commit();
    }

    public String GetUsername() {
        String user = mySharedPrefrences.getString("username", "YouRGuest");
        return user;
    }

    public void UpdateAdmin()
    {
        SharedPreferences.Editor editor = mySharedPrefrences.edit();
        if (!GetUsername().equals("YouRGuest"))
        {
            for (int j = 0; j < DataModel.admins.size(); j++) {
                if (DataModel.admins.get(j).getUsername().equals(GetUsername())) {

                    editor.putBoolean("isAdmin", true);
                    editor.commit();
                    return;
                }
            }
        }
        editor.putBoolean("isAdmin", false);
        editor.commit();
    }

    public boolean isAdmin()
    {
       boolean isAdminBool = mySharedPrefrences.getBoolean("isAdmin", false);
       return  isAdminBool;
    }

}
