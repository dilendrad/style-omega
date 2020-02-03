package com.example.styleomega;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.styleomega.Model.Cart;
import com.example.styleomega.Model.Product;
import com.example.styleomega.Prevalent.Prevalent;
import com.example.styleomega.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button nextButton;
    private TextView totalAmountForProducts;

    private double totalPrice = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.item_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        nextButton= (Button) findViewById(R.id.next_button_list);
        totalAmountForProducts = (TextView) findViewById(R.id.total_price_textview);
    }

    @Override
    protected void onStart() {
        super.onStart();

        final DatabaseReference cartListReference = FirebaseDatabase.getInstance().getReference().child("Cart List");

        FirebaseRecyclerOptions<Cart> options = new FirebaseRecyclerOptions.Builder<Cart>().setQuery(cartListReference.child("User View")
                .child(Prevalent.onlineUsers.getPhoneNumber()).child("Products"), Cart.class).build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i, @NonNull final Cart cart) {


                cartViewHolder.textProductName.setText("Product Name:" +cart.getProductName());
                cartViewHolder.textProductPrice.setText("Price:" +cart.getProductPrice());
                cartViewHolder.textProductQuantity.setText("Quantity: " +cart.getProductQuantity());

               double totalPriceOfOneProduct = ((Double.valueOf(cart.getProductPrice()))) * Integer.valueOf(cart.getProductQuantity());
               totalPrice = totalPrice + totalPriceOfOneProduct;

                nextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        totalAmountForProducts.setText(String.valueOf(totalPrice));

                        Intent intent = new Intent(CartActivity.this, ConfirmFinalOrderActivity.class);
                        intent.putExtra("Total Price", String.valueOf(totalPrice));
                        startActivity(intent);
                        finish();
                    }
                });




                cartViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        CharSequence options [] = new CharSequence[] {

                                "Edit",
                                "Delete"
                        };

                        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);

                        builder.setTitle("Cart Options: ");

                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {

                                if(i == 0) {

                                    Intent intent = new Intent(CartActivity.this, ViewProductDetailsActivity.class);
                                    intent.putExtra("productID", cart.getProductID());
                                    startActivity(intent);
                                }

                                if (i == 1) {

                                    cartListReference.child("User View").child(Prevalent.onlineUsers.getPhoneNumber()).child("Products").child(cart.getProductID())
                                            .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if(task.isSuccessful()) {

                                                Toast.makeText(CartActivity.this, "Item has been removed", Toast.LENGTH_SHORT).show();

                                                Intent intent = new Intent(CartActivity.this, CartActivity.class);
                                                startActivity(intent);
                                            }
                                        }
                                    });
                                }
                            }
                        });

                        builder.show();
                    }
                });

            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


                View view = getLayoutInflater().from(parent.getContext()).inflate(R.layout.cart_items_layout, parent, false);

                CartViewHolder holder = new CartViewHolder(view);
                return holder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
