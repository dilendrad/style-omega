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
import com.example.styleomega.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private Button LoginButton;
    private EditText firstName, lastName, phoneNumber, password;
    private ProgressDialog progressDialog;

    private DatabaseReference firebaseDBref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseDBref = FirebaseDatabase.getInstance().getReference().child("User");


        LoginButton = (Button) findViewById(R.id.login_button);
        phoneNumber = (EditText) findViewById(R.id.login_phoneNumber);
        password = (EditText) findViewById(R.id.login_password);

        progressDialog = new ProgressDialog(this); //Login Process

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginUser();

            }
        });
    }

    public void loginUser() {

        String phone = phoneNumber.getText().toString();
        String pass = password.getText().toString();

        if (TextUtils.isEmpty(phone)) {

            Toast.makeText(LoginActivity.this, "Enter Your Phone Number", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(pass)) {

            Toast.makeText(LoginActivity.this, "Enter Your Password", Toast.LENGTH_SHORT).show();
        } else {

            progressDialog.setTitle("Login Account");
            progressDialog.setMessage("Verifying Credentials");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            AuthenticateAccount(phone, pass);
        }
    }

    public void AuthenticateAccount(final String phone, final String pass) {

        final DatabaseReference firebaseDBref;
        firebaseDBref = FirebaseDatabase.getInstance().getReference();

        firebaseDBref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.child("User").child(phone).exists()) {

                    User user = dataSnapshot.child("User").child(phone).getValue(User.class);

                    if(user.getPhoneNumber().equals(phone)) {

                        if(user.getPassword().equals(pass)) {

                            Toast.makeText(LoginActivity.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                            Intent intent = new Intent(LoginActivity.this, HomeMainActivity.class);
                            Prevalent.onlineUsers = user;
                            startActivity(intent);


                        }
                    }
                }

                else{

                    Toast.makeText(LoginActivity.this, "Invalid Login Credentials", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}

