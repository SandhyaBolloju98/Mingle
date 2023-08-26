package com.random.chat.mingle1.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.random.chat.mingle1.ModelClasses.ChatsModelClass;
import com.random.chat.mingle1.ModelClasses.MessagesModelClass;
import com.random.chat.mingle1.R;
import com.random.chat.mingle1.SharedPreferencesL;
import com.random.chat.mingle1.ViewHolders.ChatsViewHolder;

import java.util.ArrayList;

public class ChatsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private ArrayList<ChatsModelClass> chatsList = new ArrayList<>();
    private ArrayList<MessagesModelClass> messageList = new ArrayList<>();
    private SharedPreferencesL sharedPreferencesL;
    public ChatsAdapter(Context context) {
        this.context = context;
        sharedPreferencesL = new SharedPreferencesL(context);
    }
    public void setStrangerNamesModelClass(ArrayList<ChatsModelClass> chatsList) {
        this.chatsList = chatsList;

        notifyItemInserted(0);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.design_chats, parent, false);
        RecyclerView.ViewHolder holder = new ChatsViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ChatsViewHolder) {

            ChatsViewHolder chatsViewHolder = (ChatsViewHolder) holder;
            chatsViewHolder.keepStrangerName(chatsList.get(position), context);

        }

    }

    @Override
    public int getItemCount() {
        return chatsList.size();
    }
}
