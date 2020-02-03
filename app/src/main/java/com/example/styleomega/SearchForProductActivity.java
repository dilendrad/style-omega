package com.example.styleomega;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.styleomega.Model.Product;
import com.example.styleomega.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class SearchForProductActivity extends AppCompatActivity {

    private Button searchButton;
    private EditText inputSearch;
    private RecyclerView searchList;
    private String inputProductSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_for_product);

        searchButton = findViewById(R.id.search_button_view);
        inputSearch = findViewById(R.id.input_search_edittext);
        searchList = findViewById(R.id.recycler_search_view);
        searchList.setLayoutManager(new LinearLayoutManager(SearchForProductActivity.this));

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                inputProductSearch = inputSearch.getText().toString();
                onStart();
            }
        });
    }

    @Override
    protected void onStart() {

        super.onStart();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Products");

        FirebaseRecyclerOptions<Product> options = new FirebaseRecyclerOptions.Builder<Product>()
                .setQuery(databaseReference.orderByChild("productName").startAt(inputProductSearch),Product.class).build();

        FirebaseRecyclerAdapter<Product, ProductViewHolder> adapter = new FirebaseRecyclerAdapter<Product, ProductViewHolder>(options) {


            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int i, @NonNull final Product product) {

                String price = Double.toString(product.getProductPrice());

                productViewHolder.viewProductName.setText(product.getProductName());
                productViewHolder.viewProductCategory.setText(product.getProductCategory());
                productViewHolder.viewProductPrice.setText(price);
                Picasso.get().load(product.getProductImageURL()).into(productViewHolder.viewProductImage);

                productViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(SearchForProductActivity.this, ViewProductDetailsActivity.class);
                        intent.putExtra("pid", product.getProductID());
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_layout, parent, false);
                ProductViewHolder holder = new ProductViewHolder(view);
                return holder;


            }
        };

        searchList.setAdapter(adapter);
        adapter.startListening();
    }
}
