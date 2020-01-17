package com.example.styleomega;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.styleomega.Model.Product;
import com.example.styleomega.Prevalent.Prevalent;
import com.example.styleomega.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DatabaseReference databaseProductReference;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_main);

        databaseProductReference = FirebaseDatabase.getInstance().getReference().child("Products");

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawerOpen,R.string.drawerClose);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerview = navigationView.getHeaderView(0);
        TextView name = headerview.findViewById(R.id.name_user_main);
        //CircleImageView profileHeaderView = findViewById(R.id.profilePic);


        Toast.makeText(this, Prevalent.onlineUsers.getFirstName(), Toast.LENGTH_SHORT).show();

        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        name.setText(Prevalent.onlineUsers.getFirstName());


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Product> options =
                new FirebaseRecyclerOptions.Builder<Product>()
                .setQuery(databaseProductReference, Product.class)
                .build();

        FirebaseRecyclerAdapter<Product, ProductViewHolder> adapter =

                new FirebaseRecyclerAdapter<Product, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int i, @NonNull Product product) {

                        String price = Double.toString(product.getProductPrice());

                        productViewHolder.viewProductName.setText("Product Name" +product.getProductName());
                        productViewHolder.viewProductCategory.setText("Catergory" +product.getProductCategory());
                        productViewHolder.viewProductPrice.setText("Rs. " +price);
                        Picasso.get().load(product.getProductImageURL()).into(productViewHolder.viewProductImage);


                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_layout, parent, false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;
                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {

            case R.id.profile:
                Toast.makeText(this, "Profile Has Been Selected", Toast.LENGTH_SHORT).show();
                break;

            case R.id.contact_us:
                Toast.makeText(this, "Contact Us Has Been Selected", Toast.LENGTH_SHORT).show();
                break;

            case R.id.log_out:
                Toast.makeText(this, "Logout Has Been Selected", Toast.LENGTH_SHORT).show();
                break;

        }
        return false;
    }
}
