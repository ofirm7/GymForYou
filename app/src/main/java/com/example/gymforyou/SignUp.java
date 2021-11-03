package com.example.gymforyou;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.lang.UScript;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    Button submit;
    EditText email, username, pass;
    SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        sharedPref = new SharedPref(this);
        submit = findViewById(R.id.submitSignUp);
        username = findViewById(R.id.usernameSignUp);
        email = findViewById(R.id.emailSignUp);
        pass = findViewById(R.id.passwordSignUp);
        submit.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view == submit) {
            if (username.getText().toString().equals("") || pass.getText().toString().equals("")) {
                Toast.makeText(this, "fill in all boxes", Toast.LENGTH_SHORT).show();
            } else {
                boolean flag = false;
                int temp = 0;
                for (int i = 0; i < DataModel.users.size() && !flag; i++) {
                    if (DataModel.users.get(i).getUsername().equals(username.getText().toString())) {
                        flag = true;
                        temp = i;
                    }
                }
                if (flag) {
                    Toast.makeText(this, "username taken", Toast.LENGTH_SHORT).show();
                    return;
                }
                //DataModel.users.add(new User(username.toString(), pass.toString()));
                DataModel.users.add(new User(username.getText().toString(), pass.getText().toString()));
                DataModel.userSave();
            }
        }
    }
}