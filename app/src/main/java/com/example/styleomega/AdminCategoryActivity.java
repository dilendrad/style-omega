package com.example.styleomega;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AdminCategoryActivity extends AppCompatActivity {

    //Initializing Variables
    private ImageView man, woman, boy, girl, bag, watches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        man = (ImageView) findViewById(R.id.man_imageView);
        woman = (ImageView) findViewById(R.id.woman_imageView);
        boy = (ImageView) findViewById(R.id.boy_imageView);
        girl = (ImageView) findViewById(R.id.girl_imageView);
        bag = (ImageView) findViewById(R.id.bag_imageView);
        watches = (ImageView) findViewById(R.id.watch_imageView);

        man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent (AdminCategoryActivity.this, ProductAddingActivity.class);
                intent.putExtra("category", "Men");
                startActivity(intent);

            }
        });

        woman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent (AdminCategoryActivity.this, ProductAddingActivity.class);
                intent.putExtra("category", "Women");
                startActivity(intent);

            }
        });

        boy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent (AdminCategoryActivity.this, ProductAddingActivity.class);
                intent.putExtra("category", "Boy");
                startActivity(intent);

            }
        });

        girl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent (AdminCategoryActivity.this, ProductAddingActivity.class);
                intent.putExtra("category", "Girl");
                startActivity(intent);

            }
        });

        bag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent (AdminCategoryActivity.this, ProductAddingActivity.class);
                intent.putExtra("category", "Bags");
                startActivity(intent);

            }
        });

        watches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent (AdminCategoryActivity.this, ProductAddingActivity.class);
                intent.putExtra("category", "Watches");
                startActivity(intent);

            }
        });
    }
}
