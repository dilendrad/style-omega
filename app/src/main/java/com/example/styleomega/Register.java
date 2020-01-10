package com.example.styleomega;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Register extends AppCompatActivity {

    private EditText firstName;
    private EditText lastName;
    private EditText address;
    private EditText email;
    private EditText password;
    //private EditText confirmPassword;
    private boolean validateUser;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstName = (EditText)findViewById(R.id.firstnameTextField);
        lastName = (EditText)findViewById(R.id.lastnameTextfield);
        address = (EditText)findViewById(R.id.addressTextField);
        email = (EditText)findViewById(R.id.emailRegisterTextField);
        password = (EditText)findViewById(R.id.passwordRegisterTextField);
        //confirmPassword = (EditText)findViewById(R.id.confirmPassword);

        Button createAnAccountButton = (Button)findViewById(R.id.createAnAccountButton);
        createAnAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validateUser = true;
            }
        });


    }
}
