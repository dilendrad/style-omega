package com.example.styleomega;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ProductAddingActivity extends AppCompatActivity {

    private String categoryName;
    private Button addProductButton;
    private ImageView inputProductImage;
    private EditText inputProductName, inputProductID, inputImageURL, inputDescription, price, quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_adding);

        categoryName = getIntent().getExtras().get("category").toString();

        //Toast.makeText(this, categoryName, Toast.LENGTH_SHORT).show();

        addProductButton = (Button) findViewById(R.id.add_item_button);
        inputProductImage = (ImageView) findViewById(R.id.select_product_image);
        inputProductName = (EditText) findViewById(R.id.add_product_name);
        inputProductID = (EditText) findViewById(R.id.add_product_id);
        inputImageURL = (EditText) findViewById(R.id.add_image_url);
        inputDescription = (EditText) findViewById(R.id.add_product_price);
        price = (EditText) findViewById(R.id.add_product_price);
        quantity = (EditText) findViewById(R.id.add_product_quantity);




    }
}
