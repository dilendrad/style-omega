package com.example.styleomega;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.styleomega.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AccountSettings extends AppCompatActivity {

    private EditText updateFirstName, updateLastName, updatePassword;
    private TextView closeTextButton, updateTextButton;
    public String checker = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        updateFirstName = (EditText) findViewById(R.id.settings_firstName);
        updateLastName = (EditText) findViewById(R.id.settings_lastName);
        updatePassword = (EditText) findViewById(R.id.settings_password);
        updateTextButton = (TextView) findViewById(R.id.settings_update);
        closeTextButton = (TextView) findViewById(R.id.settings_close);

        closeTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        updateTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateUserInfo();

            }
        });


    }

    private void updateUserInfo() {

        DatabaseReference updateDBReference = FirebaseDatabase.getInstance().getReference().child("User");

        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("firstName", updateFirstName.getText().toString());
        userMap.put("lastName", updateLastName.getText().toString());
        userMap.put("password", updatePassword.getText().toString());

        updateDBReference.child(Prevalent.onlineUsers.getPhoneNumber()).updateChildren(userMap);

        startActivity(new Intent(AccountSettings.this, HomeMainActivity.class));
        Toast.makeText(AccountSettings.this, "Accounts Details Successfully Updated", Toast.LENGTH_SHORT).show();
        finish();



        }
    }

