package com.example.styleomega;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.styleomega.Model.Product;
import com.example.styleomega.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ViewProductDetailsActivity extends AppCompatActivity {

    private ImageView productImage;
    private TextView productName, productCategory, productDescription, productPrice;
    private Button addToCart;
    private ElegantNumberButton quantityButton;
    private String productID = "";
    private Button share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product_details);

        productID = getIntent().getStringExtra("pid");
        productImage = (ImageView)findViewById(R.id.product_image);


        productName = (TextView) findViewById(R.id.product_name_details);
        productCategory = (TextView) findViewById(R.id.product_category_details);
        productDescription = (TextView) findViewById(R.id.product_description_details);
        productPrice = (TextView) findViewById(R.id.product_price_details) ;

        quantityButton = (ElegantNumberButton) findViewById(R.id.quantity_button);
        addToCart = (Button) findViewById(R.id.add_to_cart_button);
        share=(Button)findViewById(R.id.share_view);

        getProductDetails(productID);

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addingItemToCart();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share = new Intent();
                share.setAction(Intent.ACTION_SEND);
                String message=productName.getText().toString()+"\n"+productDescription.getText().toString()+"\n"+productPrice.getText().toString();
                share.putExtra(Intent.EXTRA_TEXT,message);
                share.setType("text/plain");

                Intent send = Intent.createChooser(share,null);
                startActivity(send);
            }
        });
    }

    private void addingItemToCart() {

        String saveCurrentTime, saveCurrentDate;

        Calendar checkForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentTime = currentDate.format(checkForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentDate = currentTime.format(checkForDate.getTime());

        final DatabaseReference itemCartListReference = FirebaseDatabase.getInstance().getReference().child("Cart List");

        final HashMap<String, Object> cartMap = new HashMap<>();

        cartMap.put("productID", productID);
        cartMap.put("productName", productName.getText().toString());
        cartMap.put("productCategory", productCategory.getText().toString());
        cartMap.put("productDescription", productDescription.getText().toString());
        cartMap.put("productPrice", productPrice.getText().toString());
        cartMap.put("productQuantity", quantityButton.getNumber());
        cartMap.put("date", saveCurrentDate);
        cartMap.put("time", saveCurrentTime);

        itemCartListReference.child("User View").child(Prevalent.onlineUsers.getPhoneNumber()).child("Products").child(productID)
        .updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()) {

                    itemCartListReference.child("Admin View").child(Prevalent.onlineUsers.getPhoneNumber()).child("Products").child(productID)
                            .updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()) {

                                Toast.makeText(ViewProductDetailsActivity.this, "Item Added to Cart", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent (ViewProductDetailsActivity.this, HomeMainActivity.class);
                                startActivity(intent);
                            }
                        }
                    });

                }
            }
        });




    }

    private void getProductDetails(String productID) {

        DatabaseReference productReference = FirebaseDatabase.getInstance().getReference().child("Products");

        productReference.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {

                    Product product = dataSnapshot.getValue(Product.class);
                    String price= Double.toString(product.getProductPrice());
                    //String quantity=Integer.toString(product.getProductQuantity());

                    productName.setText(product.getProductName());
                    productCategory.setText(product.getProductCategory());
                    productDescription.setText(product.getProductDescription());
                    productPrice.setText(price);
                    //productPrice.setText(product.getProductPrice());
                    Picasso.get().load(product.getProductImageURL()).into(productImage);



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
