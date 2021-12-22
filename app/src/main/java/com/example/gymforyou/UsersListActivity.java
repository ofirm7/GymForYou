package com.example.gymforyou;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class UsersListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    AlertDialog.Builder builder;
    SharedPref sharedPref;

    MyListAdapter usersListAdapter;
    ArrayList<String> aTitle = new ArrayList<>(), aDescription = new ArrayList<>();
    ListView usersList;

    Dialog makeAnAdminDialog;
    int adminLevel = 0;
    Button submitInDialog;

    int userPos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);

        for (int i = 0; i < DataModel.users.size(); i++) {
            aTitle.add(DataModel.users.get(i).username);
            aDescription.add("");
        }

        builder = new AlertDialog.Builder(this);

        usersList = findViewById(R.id.usersList);
        usersListAdapter = new MyListAdapter(this, aTitle, aDescription);

        usersList.setAdapter(usersListAdapter);

        usersList.setOnItemClickListener(this);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.adminLevelOne:
                if (checked)
                    adminLevel = 1;
                    break;
            case R.id.adminLevelTwo:
                if (checked)
                    adminLevel = 2;
                    break;
            case R.id.adminLevelThree:
                if (checked)
                    adminLevel = 3;
                break;
            case R.id.adminLevelFour:
                if (checked)
                    adminLevel = 4;
                break;
            case R.id.adminLevelFive:
                if (checked)
                    adminLevel = 5;
                break;
        }
    }

    public void openMakeAnAdminDialog()
    {
        makeAnAdminDialog = new Dialog(this);
        makeAnAdminDialog.setContentView(R.layout.custom_dialog_set_admin_level);

        makeAnAdminDialog.setTitle("Make An Admin");

        makeAnAdminDialog.setCancelable(true);

        submitInDialog = makeAnAdminDialog.findViewById(R.id.makeAnAdminButton);

        submitInDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v == submitInDialog)
                {
                    if (adminLevel == 0)
                    {
                        Toast.makeText(UsersListActivity.this, "Please choose a level!", Toast.LENGTH_LONG).show();
                    }
                    else {
                        //saving as admin
                        DataModel.admins.add(new Admin(DataModel.users.get(userPos).username, DataModel.users.get(userPos).password,
                                DataModel.users.get(userPos).email, DataModel.users.get(userPos).phoneNumber, adminLevel));
                        DataModel.adminsSave();

                        //remove the new admin from users list
                        DataModel.users.remove(userPos);
                        DataModel.userSave();
                    }
                    makeAnAdminDialog.dismiss();
                    restartapp();
                }
            }
        });
        makeAnAdminDialog.show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        builder.setMessage("Are you sure you want to make " + DataModel.users.get(i).username + " an admin?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        userPos = i;
                        openMakeAnAdminDialog();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                        Toast.makeText(getApplicationContext(), "You canceled the logout",
                                Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog alert = builder.create();
        alert.setTitle("Make Admin");
        alert.show();

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

        if (sharedPref.GetUsername().equals("YouRGuest")) {
            item = menu.getItem(0);
            item.setEnabled(false);
            item.setVisible(false);

            item = menu.getItem(3);
            item.setEnabled(false);
            item.setVisible(false);

        } else {

            item = menu.getItem(1);
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
            Intent intent = new Intent(this, SignUp.class);
            startActivityForResult(intent, 0);
            return true;
        }
        else if (id == R.id.action_exit){
            builder.setMessage("Do you want to logout?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                            sharedPref.SetUsername("YouRGuest");
                            Toast.makeText(getApplicationContext(), "You logged out",
                                    Toast.LENGTH_SHORT).show();
                            restartapp();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //  Action for 'NO' Button
                            dialog.cancel();
                            Toast.makeText(getApplicationContext(), "You canceled the logout",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.setTitle("Logout");
            alert.show();
        }
        else if (id == R.id.action_GoHome) {
            finish();
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
        Intent i = new Intent(getApplicationContext(), UsersListActivity.class);
        i.putExtra("WE", getIntent().getIntExtra("WE", 0));
        startActivity(i);
        finish();
    }

}