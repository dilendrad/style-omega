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

import com.example.styleomega.Model.Inquiry;
import com.example.styleomega.Model.Product;
import com.example.styleomega.ViewHolder.InquiryViewHolder;
import com.example.styleomega.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ViewInquiries extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_inquiries);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Inquiries");

        recyclerView = findViewById(R.id.item_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();

       FirebaseRecyclerOptions<Inquiry> options =
                new FirebaseRecyclerOptions.Builder<Inquiry>()
                        .setQuery(databaseReference, Inquiry.class)
                        .build();

        FirebaseRecyclerAdapter<Inquiry, InquiryViewHolder> adapter =

                new FirebaseRecyclerAdapter<Inquiry, InquiryViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull InquiryViewHolder inquiryViewHolder, int i, @NonNull Inquiry inquiry) {
                        inquiryViewHolder.inquiryEmail.setText("Email: "+inquiry.getInquiryEmail());
               inquiryViewHolder.inquirySubject.setText("Subject: "+inquiry.getInquirySubject());
                inquiryViewHolder.inquiryMessage.setText("Message: "+inquiry.getInquiryMessage());
                    }



                    @NonNull
                    @Override
                    public InquiryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inquiry_layout, parent, false);
                        InquiryViewHolder holder = new InquiryViewHolder(view);
                        return holder;
                    }
                };

        recyclerView.setAdapter(adapter);
       adapter.startListening();
    }

}
