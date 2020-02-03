package com.example.styleomega;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminHomepage extends AppCompatActivity {

    private Button adminAddProduct, adminViewInquiries, adminLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_homepage);

        adminAddProduct = (Button) findViewById(R.id.admin_add_product_button);
        adminViewInquiries = (Button) findViewById(R.id.admin_view_inquiries_button);
        adminLogout = (Button) findViewById(R.id.admin_logout_button);

        adminAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminHomepage.this, AdminCategoryActivity.class);
                startActivity(intent);

            }
        });

        adminViewInquiries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminHomepage.this, ViewInquiries.class);
                startActivity(intent);

            }
        });

        adminLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdminHomepage.this, MainActivity.class);
                startActivity(intent);

            }
        });
    }
}
