package com.example.gymforyou;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class ExerciseDetailsActivity extends AppCompatActivity {
    SharedPref sharedPref;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_details);



        tv = findViewById(R.id.tv);

        tv.setText(getIntent().getStringExtra("WE"));
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
        if(sharedPref.GetUsername().equals("YouRGuest"))
        {
            MenuItem item = menu.getItem(0);
            item.setEnabled(false);
            item.setVisible(false);

        }
        else
        {

            MenuItem item = menu.getItem(1);
            item.setEnabled(false);
            item.setVisible(false);

            item = menu.getItem(2);
            item.setEnabled(false);
            item.setVisible(false);

            item = menu.getItem(0);
            item.setTitle(sharedPref.GetUsername());
        }
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
            Toast.makeText(this,"you selected register",Toast.LENGTH_LONG).show();
            return true;
        }
        else if (id == R.id.action_exit){
            sharedPref.SetUsername("YouRGuest");
            //Toast.makeText(this,"you sure you want to logout?",Toast.LENGTH_LONG).show();
            restartapp();
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