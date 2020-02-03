package com.example.styleomega.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.styleomega.Interface.ItemClickListener_New;
import com.example.styleomega.R;

import org.w3c.dom.Text;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView textProductName, textProductPrice, textProductQuantity;
    private ItemClickListener_New itemClickListener;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

        textProductName = itemView.findViewById(R.id.product_name_cart);
        textProductPrice = itemView.findViewById(R.id.product_price_cart);
        textProductQuantity = itemView.findViewById(R.id.product_quantity_cart);

    }

    @Override
    public void onClick(View v) {

        itemClickListener.onClick(v, getAdapterPosition(), false);

    }

    public void setItemClickListener(ItemClickListener_New itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
