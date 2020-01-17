package com.example.styleomega.ViewHolder;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.styleomega.Interface.ItemClickListener_New;
import com.example.styleomega.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView viewProductName, viewProductCategory, viewProductPrice;
    public ImageView viewProductImage;
    public ItemClickListener_New listener;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        viewProductImage = (ImageView) itemView.findViewById(R.id.view_product_image);
        viewProductName = (TextView) itemView.findViewById(R.id.view_product_name);
        viewProductCategory = (TextView) itemView.findViewById(R.id.view_product_category);
        viewProductPrice = (TextView) itemView.findViewById(R.id.view_product_category);
    }

    public void setOnClickListener(ItemClickListener_New listener) {

        this.listener = listener;

    }

    @Override
    public void onClick(View view) {

        listener.onClick(view, getAdapterPosition(), false);

    }
}
