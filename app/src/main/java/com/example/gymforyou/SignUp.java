package com.example.gymforyou;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.lang.UScript;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    Button submit, toLoginFromSignUp;
    EditText email, username, pass;
    SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        toLoginFromSignUp = findViewById(R.id.toLoginFromSignUp);
        sharedPref = new SharedPref(this);
        submit = findViewById(R.id.submitSignUp);
        username = findViewById(R.id.usernameSignUp);
        email = findViewById(R.id.emailSignUp);
        pass = findViewById(R.id.passwordSignUp);
        submit.setOnClickListener(this);
        toLoginFromSignUp.setOnClickListener(this);
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
        else if(view == toLoginFromSignUp)
        {
            Intent intent=new Intent(this,LoginPage.class);
            startActivity(intent
            );
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);

        for(int i=0;i<menu.size();i++)
        {
            MenuItem item= menu.getItem(i);
            item.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
        MenuItem item;
        item = menu.getItem(0);
        item.setEnabled(false);
        item.setVisible(false);

        /* shut down login item
        item = menu.getItem(1);
        item.setEnabled(false);
        item.setVisible(false);
        */
        item = menu.getItem(2);
        item.setEnabled(false);
        item.setVisible(false);

        item = menu.getItem(3);
        item.setEnabled(false);
        item.setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.action_login) {
            Intent intent=new Intent(this,LoginPage.class);
            startActivityForResult(intent, 0);
            //Toast.makeText(this,"you selected login",Toast.LENGTH_LONG).show();
            return true;
        }
        else if (id == R.id.action_register) {
            Intent intent = new Intent(this, SignUp.class);
            startActivityForResult(intent, 0);
            return true;
        }
        else if (id == R.id.action_exit){
            sharedPref.SetUsername("YouRGuest");
            //Toast.makeText(this,"you sure you want to logout?",Toast.LENGTH_LONG).show();
            restartapp();
            return true;
        }
        else if (id == R.id.action_GoHome) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivityForResult(intent, 0);
            return true;
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