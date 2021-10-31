package com.example.gymforyou;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView l1;
    MyListAdapter adapter1;
    ArrayList<String> aTitle = new ArrayList<>(), aDescription = new ArrayList<>();
    SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedPref = new SharedPref(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aTitle.add("Excersie 1");
        aTitle.add("Excersie 2");
        aTitle.add("Excersie 3");
        aTitle.add("Excersie 4");

        aDescription.add("blablabla");
        aDescription.add("blablabla");
        aDescription.add("blablabla");
        aDescription.add("blablabla");

        adapter1 = new MyListAdapter(this, aTitle, aDescription);

        l1 = findViewById(R.id.LTest);
        l1.setAdapter(adapter1);

        l1.setOnItemClickListener(this);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent1 = new Intent(this, ExerciseDetailsActivity.class);
        switch (position)
        {
            case 0:
                intent1.putExtra("WE", "Exercise 1");
                break;
            case 1:
                intent1.putExtra("WE", "Exercise 2");
                break;
            case 2:
                intent1.putExtra("WE", "Exercise 3");
                break;
            case 3:
                intent1.putExtra("WE", "Exercise 4");
                break;
        }

        startActivity(intent1);
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