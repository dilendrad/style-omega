package com.example.styleomega;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.styleomega.Model.Inquiry;
import com.example.styleomega.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AddInquiry extends AppCompatActivity {

    private EditText inquiryEmail, inquirySubject, inquiryMessage;
    private Button addInquiryButton;
    private String saveCurrentDate, saveCurrentTime, inquiryID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_inquiry);

        //inquiryID = getIntent().getStringExtra("inquiryID");
        inquiryEmail = (EditText) findViewById(R.id.email_inquiry);
        inquirySubject = (EditText) findViewById(R.id.subject_inquiry);
        inquiryMessage = (EditText) findViewById(R.id.message_body_inquiry);

        addInquiryButton = (Button) findViewById(R.id.add_inquiry_button);

        addInquiryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Check();
                Toast.makeText(AddInquiry.this, "Inquiry Has Been Sent", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddInquiry.this, HomeMainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void Check() {

        if (TextUtils.isEmpty(inquiryEmail.getText().toString())) {

            Toast.makeText(this, "Enter Your Email", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(inquirySubject.getText().toString())) {

            Toast.makeText(this, "Enter Your Subject", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(inquiryMessage.getText().toString())) {

            Toast.makeText(this, "Enter Your Message", Toast.LENGTH_SHORT).show();
        } else {

            Add_Inquiry();
        }


    }

    private void Add_Inquiry() {

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM DD, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());
        inquiryID = saveCurrentDate + saveCurrentTime;

        DatabaseReference updateDBReference = FirebaseDatabase.getInstance().getReference().child("Inquiries");

        Inquiry inquiry = new Inquiry(inquiryEmail.getText().toString().trim(),inquirySubject.getText().toString().trim(),inquiryMessage.getText().toString().trim());

        updateDBReference.child(inquiryID).setValue(inquiry);

    }
}






