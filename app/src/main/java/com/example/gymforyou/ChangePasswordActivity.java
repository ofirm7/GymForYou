package com.example.gymforyou;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Random;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    SharedPref sharedPref;
    int code = 0;
    Random random=new Random();

    Button sendCodeBT;
    EditText etPhoneNumber;
    String[] permissions = {

            Manifest.permission.READ_SMS,
            Manifest.permission.SEND_SMS
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ActivityCompat.requestPermissions(this, permissions, 0);
        sendCodeBT = findViewById(R.id.sendCodeBT);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);

        sendCodeBT.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == sendCodeBT)
        {
            sendCodeFunc();
        }
    }

    public void sendCodeFunc()
    {
        Random random=new Random();
        code=random.nextInt(999999);
        while (code<100000)
        {
            code=random.nextInt(999999);
        }
        TelephonyManager telephony = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        String number = etPhoneNumber.getText().toString();
        SmsManager sms = SmsManager.getDefault();
        try {
            sms.sendTextMessage(number, null, "(GymForYou) Your code:"+code, null, null);
        } catch (Exception e) {

        }
    }

}