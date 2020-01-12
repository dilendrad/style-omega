package com.example.styleomega;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.styleomega.Model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private Button RegisterAccountButton;
    private EditText firstName, lastName, phoneNumber, password;
    private ProgressDialog progressDialog;

    private DatabaseReference firebaseDBref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseDBref = FirebaseDatabase.getInstance().getReference().child("User");



        RegisterAccountButton = (Button) findViewById(R.id.register_button);
        firstName = (EditText) findViewById(R.id.register_fName);
        lastName = (EditText) findViewById(R.id.register_lastName);
        phoneNumber = (EditText) findViewById(R.id.register_phoneNumber);
        password = (EditText) findViewById(R.id.register_password);

        progressDialog = new ProgressDialog(this); //Registration Process

        RegisterAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user=new User(firstName.getText().toString().trim(),lastName.getText().toString().trim(),phoneNumber.getText().toString().trim(),password.getText().toString().trim());

                firebaseDBref.child(phoneNumber.getText().toString()).setValue(user);

            }
        });
    }
}
