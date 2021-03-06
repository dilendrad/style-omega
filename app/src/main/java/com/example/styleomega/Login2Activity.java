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

import com.example.styleomega.Model.Admin;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login2Activity extends AppCompatActivity {

    private Button adminLoginButton;
    private EditText phoneNumber, password;
    private ProgressDialog progressDialog;

    private DatabaseReference firebaseDBref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        firebaseDBref = FirebaseDatabase.getInstance().getReference().child("Admin");


        adminLoginButton = (Button) findViewById(R.id.main_login2_button);
        phoneNumber = (EditText) findViewById(R.id.login_adminLogin_phone);
        password = (EditText) findViewById(R.id.login_adminLogin_password);

        progressDialog = new ProgressDialog(this); //Login Process

        adminLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                adminLoginUser();

            }
        });
    }

    public void adminLoginUser() {

        String phone = phoneNumber.getText().toString();
        String pass = password.getText().toString();

        if (TextUtils.isEmpty(phone)) {

            Toast.makeText(Login2Activity.this, "Enter Your Phone Number", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(pass)) {

            Toast.makeText(Login2Activity.this, "Enter Your Password", Toast.LENGTH_SHORT).show();
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

                if(dataSnapshot.child("Admin").child(phone).exists()) {

                    Admin user = dataSnapshot.child("Admin").child(phone).getValue(Admin.class);

                    if(user.getPhoneNumber().equals(phone)) {

                        if(user.getPassword().equals(pass)) {

                            Toast.makeText(Login2Activity.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                            Intent intent = new Intent(Login2Activity.this, AdminHomepage.class);
                            startActivity(intent);


                        }
                    }
                }

                else{

                    Toast.makeText(Login2Activity.this, "Invalid Login Credentials", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }
}

