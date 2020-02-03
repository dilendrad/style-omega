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

import com.example.styleomega.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ConfirmFinalOrderActivity extends AppCompatActivity {

    private EditText shippingName, shippingPhoneNumber, shippingAddress;
    private Button shippingConfirmButton;

    private String totalAmount = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);

        totalAmount = getIntent().getStringExtra("Total Price");
        Toast.makeText(this, "Total Price: Rs. " +totalAmount, Toast.LENGTH_SHORT).show();

        shippingName = (EditText) findViewById(R.id.shipping_name);
        shippingPhoneNumber = (EditText) findViewById(R.id.shipping_phone_number);
        shippingAddress = (EditText) findViewById(R.id.shipping_address);

        shippingConfirmButton = (Button) findViewById(R.id.shipping_confirm_button);

        shippingConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Check();
            }
        });
    }

    private void Check() {

        if(TextUtils.isEmpty(shippingName.getText().toString())) {

            Toast.makeText(this, "Enter Your Name", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(shippingAddress.getText().toString())) {

            Toast.makeText(this, "Enter Your Address", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(shippingPhoneNumber.getText().toString())) {

            Toast.makeText(this, "Enter Your Phone", Toast.LENGTH_SHORT).show();
        }

        else {

            ConfirmOrder();
        }
    }

    private void ConfirmOrder() {

        final String saveCurrentTime, saveCurrentDate;

        Calendar checkForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentTime = currentDate.format(checkForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentDate = currentTime.format(checkForDate.getTime());

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Orders")
                .child("Orders").child(Prevalent.onlineUsers.getPhoneNumber());

        HashMap<String, Object> ordersMap = new HashMap<>();
        ordersMap.put("totalAmount", totalAmount);
        ordersMap.put("shippingName", shippingName.getText().toString());
        ordersMap.put("shippingPhoneNumber" ,shippingPhoneNumber.getText().toString());
        ordersMap.put("shippingAddress" ,shippingAddress.getText().toString());

        databaseReference.updateChildren(ordersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()) {

                    FirebaseDatabase.getInstance().getReference().child("Cart List").child("User View").child(Prevalent.onlineUsers.getPhoneNumber())
                            .removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()) {

                                        Toast.makeText(ConfirmFinalOrderActivity.this, "Your order has been placed", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(ConfirmFinalOrderActivity.this, HomeMainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    }

                                }
                            });
                }
            }
        });
    }
}
