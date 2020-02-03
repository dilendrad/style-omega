package com.example.styleomega.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.styleomega.Interface.ItemClickListener_New;
import com.example.styleomega.R;

public class InquiryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView inquiryEmail, inquirySubject, inquiryMessage;
    public ItemClickListener_New listener;

    public InquiryViewHolder(@NonNull View itemView) {
        super(itemView);

        inquiryEmail = itemView.findViewById(R.id.view_email_inquiry);
        inquirySubject = itemView.findViewById(R.id.view_subject_inquiry);
        inquiryMessage = itemView.findViewById(R.id.view_message);
    }

    public void setOnClickListener(ItemClickListener_New listener) {

        this.listener = listener;

    }

    @Override
    public void onClick(View view) {

        listener.onClick(view, getAdapterPosition(), false);

    }
}



