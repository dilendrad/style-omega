package com.example.styleomega;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.styleomega.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

                String fName = firstName.getText().toString();
                String lName = lastName.getText().toString();
                final String phone = phoneNumber.getText().toString();
                String pass = password.getText().toString();


                if (TextUtils.isEmpty(fName)) {

                    Toast.makeText(RegisterActivity.this, "Enter Your First Name", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(lName)) {

                    Toast.makeText(RegisterActivity.this, "Enter Your Last Name", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(phone)) {

                    Toast.makeText(RegisterActivity.this, "Enter Your Phone Number", Toast.LENGTH_SHORT).show();
                }

                else if (TextUtils.isEmpty(pass)) {

                    Toast.makeText(RegisterActivity.this, "Enter a Password", Toast.LENGTH_SHORT).show();
                }
                else {

                    progressDialog.setTitle("Register Account");
                    progressDialog.setMessage("Creating An Account");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    Validations(phone);

                }






            }
        });

    }

    public void Validations (final String phone) {

        final DatabaseReference firebaseDBref;
        firebaseDBref = FirebaseDatabase.getInstance().getReference().child("User");

        firebaseDBref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(!dataSnapshot.child(phone).exists()) {

                    User user = new User(firstName.getText().toString().trim(),lastName.getText().toString().trim(),phoneNumber.getText().toString().trim(),password.getText().toString().trim());

                    firebaseDBref.child(phoneNumber.getText().toString()).setValue(user);

                    Toast.makeText(RegisterActivity.this, "You Have Successfully Created An Account", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    }

                else{

                    Toast.makeText(RegisterActivity.this, "This Phone Number Already Exists", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                    Intent intent = new Intent(RegisterActivity.this, RegisterActivity.class);
                    startActivity(intent);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
