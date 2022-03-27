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

    Boolean isAdmin = false;

    SharedPref sharedPref;
    String phoneNumber = "";
    String userNameOrEmail = "";

    EditText newPasswordET, codeET;

    int code = 0;
    Button sendCodeBT, submitBT;
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

        phoneNumber = getIntent().getStringExtra("CPI2");
        userNameOrEmail = getIntent().getStringExtra("CPI");
        sendCodeFunc();

        newPasswordET = findViewById(R.id.newPasswordET);
        codeET = findViewById(R.id.codeET);
        submitBT = findViewById(R.id.submitBT);

        sendCodeBT.setOnClickListener(this);
        submitBT.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == sendCodeBT)
        {
            sendCodeFunc();
        }
        else if (view == submitBT)
        {
            if (newPasswordET.getText().toString() != "")
            {
                int pos = findPosOfUser();
                if (!isAdmin)
                {
                    DataModel.users.get(pos).setPassword(newPasswordET.getText().toString());
                    DataModel.userSave();
                }
                else
                {
                    DataModel.admins .get(pos).setPassword(newPasswordET.getText().toString());
                    DataModel.adminsSave();
                }
            }
        }
    }

    public int findPosOfUser() {
        boolean flag = false;
        int pos = 0;
        for (int i = 0; i < DataModel.users.size() && !flag; i++) {
            if (DataModel.users.get(i).getUsername().equals(userNameOrEmail)
                    || DataModel.users.get(i).getEmail().equals(userNameOrEmail)){
                flag = true;
                pos = i;
            }
        }
        for (int j = 0; j < DataModel.admins.size() && !flag; j++) {
            if (DataModel.admins.get(j).getUsername().equals(userNameOrEmail)
                    || DataModel.admins.get(j).getEmail().equals(userNameOrEmail)) {
                flag = true;
                pos = j;
                isAdmin = true;
            }
        }
        return pos;
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
        SmsManager sms = SmsManager.getDefault();
        try {
            sms.sendTextMessage(phoneNumber, null, "(GymForYou) Your code:"+code, null, null);
        } catch (Exception e) {

        }
    }

}