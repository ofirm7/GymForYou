package com.example.gymforyou;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Button;


// ALERT DIALOG
// Sources : http://techblogon.com/alert-dialog-with-edittext-in-android-example-with-source-code/

public class BroadcastAlert extends Activity
{

    Button nbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("error")
                .setMessage("there is no internet")
                .setCancelable(false)
                .setNeutralButton("Reconnect", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        dialog.cancel();
                        finish();
                        System.exit(0);
                        Intent i = new Intent(null, MainActivity.class);
                        startActivity(i);
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
        /*
        nbutton = alert.getButton(DialogInterface.BUTTON_NEUTRAL);
        nbutton.setTextColor(Color.BLACK);*/
    }

}
