package com.random.chat.mingle1.ViewHolders;

import android.text.format.DateFormat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.random.chat.mingle1.ModelClasses.MessagesModelClass;
import com.random.chat.mingle1.R;

import java.util.Date;

public class MessageInViewHolder extends RecyclerView.ViewHolder {

    private TextView message_text;
    private LinearLayout message_layout;
    private TextView time;


    public MessageInViewHolder(@NonNull View itemView) {
        super(itemView);
        message_text = itemView.findViewById(R.id.message_text);
        message_layout = itemView.findViewById(R.id.message_container);
        time = itemView.findViewById(R.id.time);
    }

    public void keepMessage(MessageInViewHolder messageInViewHolder,
                            MessagesModelClass message) {
        messageInViewHolder.message_text.setText(message.getMessage());
        messageInViewHolder.time.setText(DateFormat.format("hh:mm", new Date(message.getMessage_time() * 1000)).toString());
    }
}
