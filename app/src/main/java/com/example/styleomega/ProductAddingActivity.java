package com.example.styleomega;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.styleomega.Model.Product;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ProductAddingActivity extends AppCompatActivity {

    private String categoryName, productName, productDescription, saveCurrentTime, saveCurrentDate;
    private double productPrice;
    private int productQuantity;
    private Button addProductButton;
    private ImageView inputProductImage;
    private EditText inputProductName, inputDescription, inputPrice, inputQuantity;
    private static final int GalleryChoose = 1;
    private Uri ImageUri;
    private String productID, downloadImageURL;
    private StorageReference productImageReference;
    private DatabaseReference dbReference;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_adding);

        categoryName = getIntent().getExtras().get("category").toString();
        productImageReference = FirebaseStorage.getInstance().getReference().child("Product Images");
        dbReference = FirebaseDatabase.getInstance().getReference().child("Products");

        //Toast.makeText(this, categoryName, Toast.LENGTH_SHORT).show();

        addProductButton = (Button) findViewById(R.id.add_item_button);
        inputProductImage = (ImageView) findViewById(R.id.select_product_image);
        inputProductName = (EditText) findViewById(R.id.add_product_name);
        inputDescription = (EditText) findViewById(R.id.add_product_description);
        inputPrice = (EditText) findViewById(R.id.add_product_price);
        inputQuantity = (EditText) findViewById(R.id.add_product_quantity);
        progressDialog = new ProgressDialog(this); //Login Process

        inputProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openGallery();

            }
        });

        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validateProduct();

            }
        });
    }

    public void openGallery() {

        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryChoose);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GalleryChoose && resultCode == RESULT_OK && data != null) {

            ImageUri = data.getData();
            inputProductImage.setImageURI(ImageUri);
        }

    }

    private void validateProduct() {

        productName = inputProductName.getText().toString();
        productDescription = inputDescription.getText().toString();
        productPrice = Double.parseDouble(inputPrice.getText().toString());
        productQuantity = Integer.parseInt(inputQuantity.getText().toString());

        if(ImageUri == null) {

            Toast.makeText(this, "Product Image is Mandatory", Toast.LENGTH_SHORT).show();
        }

        else if(productName.isEmpty()) {

            Toast.makeText(this, "Type a Product Name", Toast.LENGTH_SHORT).show();
        }

        else if(productDescription.isEmpty()) {

            Toast.makeText(this, "Type a Product Description", Toast.LENGTH_SHORT).show();
        }

        else if(productPrice < 0) {

            Toast.makeText(this, "Type a Product Price", Toast.LENGTH_SHORT).show();
        }

        else if(productQuantity < 0) {

            Toast.makeText(this, "Type a Product Quantity", Toast.LENGTH_SHORT).show();
        }

        else {

            storingProductInformation();
        }
    }

    private void storingProductInformation()
    {

        progressDialog.setTitle("Adding Product");
        progressDialog.setMessage("Please wait until the product has been added");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate= new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime= new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productID = saveCurrentDate + saveCurrentTime;

        final StorageReference filePath = productImageReference.child(ImageUri.getLastPathSegment() + productID + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                String message = e.toString();
                Toast.makeText(ProductAddingActivity.this, "There Has Been Error", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Toast.makeText(ProductAddingActivity.this, "The Image Has Been Uploaded Successfully", Toast.LENGTH_SHORT).show();

                Task <Uri> urlTask =uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                        if(!task.isSuccessful()) {

                            throw task.getException();

                        }

                        downloadImageURL = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();



                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        if(task.isSuccessful()) {

                            downloadImageURL = task.getResult().toString();

                            Toast.makeText(ProductAddingActivity.this, "The product image URL has been saved successfully", Toast.LENGTH_SHORT).show();

                            savingProductInformationToDatabase();
                        }

                    }
                });

            }
        });

    }

    public void savingProductInformationToDatabase() {

        Product product = new Product(productID, downloadImageURL, productName, productDescription, categoryName, productPrice, productQuantity);



        dbReference.child(productID).setValue(product).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()) {

                    progressDialog.dismiss();
                    Toast.makeText(ProductAddingActivity.this, "Product Has Been Added", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ProductAddingActivity.this, AdminCategoryActivity.class);
                    startActivity(intent);
                }

                else{

                    progressDialog.dismiss();
                    Toast.makeText(ProductAddingActivity.this, "Failed to Add Product", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ProductAddingActivity.this, AdminCategoryActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
