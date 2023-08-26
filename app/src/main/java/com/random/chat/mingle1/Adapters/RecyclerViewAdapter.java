package com.random.chat.mingle1.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.random.chat.mingle1.ModelClasses.MessagesModelClass;
import com.random.chat.mingle1.R;
import com.random.chat.mingle1.SharedPreferencesL;
import com.random.chat.mingle1.ViewHolders.AdViewHolder;
import com.random.chat.mingle1.ViewHolders.EmptyViewHolder;
import com.random.chat.mingle1.ViewHolders.MessageInViewHolder;
import com.random.chat.mingle1.ViewHolders.MessageOutViewHolder;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity context;

    public static int DESIGN_INCOMING = R.layout.design_incoming;
    public static int DESIGN_OUTGOING = R.layout.design_outgoing;
    public static int EMPTY_VIEW = R.layout.empty_view;
    public static int AD_VIEW = R.layout.design_ad;
    private ArrayList<MessagesModelClass> messagesModelClass = new ArrayList<>();
    private SharedPreferencesL sharedPreferencesL;

    public RecyclerViewAdapter(Activity context) {
        this.context = context;
        sharedPreferencesL = new SharedPreferencesL(context);
    }


    public void setMessagesModelClass(ArrayList<MessagesModelClass> messagesModelClass) {
        this.messagesModelClass = messagesModelClass;
    }

    public int getItemViewType(int position) {
        if (messagesModelClass.get(position).isBanner_ad()) {
            return AD_VIEW;
        } else if (messagesModelClass.get(position).getMessage_user_id().equals(sharedPreferencesL.getUserId())) {
            return DESIGN_OUTGOING;
        } else if (!messagesModelClass.get(position).getMessage_user_id().equals(sharedPreferencesL.getUserId())) {
            return DESIGN_INCOMING;
        } else {
            return EMPTY_VIEW;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;

        if (viewType == DESIGN_INCOMING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.design_incoming, parent, false);
            vh = new MessageInViewHolder(view);
        } else if (viewType == DESIGN_OUTGOING) {
            View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.design_outgoing, parent, false);
            vh = new MessageOutViewHolder(view2);
        } else if (viewType == AD_VIEW) {
            View view3 = LayoutInflater.from(parent.getContext()).inflate(R.layout.design_ad, parent, false);
            vh = new AdViewHolder(view3);
        } else {
            View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_view, parent, false);
            vh = new EmptyViewHolder(view2);
        }

        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.@NonNull ViewHolder holder, int position) {

        if (holder instanceof MessageInViewHolder) {
            MessageInViewHolder messageInViewHolder = (MessageInViewHolder) holder;
            messageInViewHolder.keepMessage(messageInViewHolder, messagesModelClass.get(position));
        } else if (holder instanceof MessageOutViewHolder) {
            MessageOutViewHolder messageOutViewHolder = (MessageOutViewHolder) holder;
            messageOutViewHolder.keepMessage(messageOutViewHolder, messagesModelClass.get(position));
        } else if (holder instanceof AdViewHolder) {
            AdViewHolder adViewHolder = (AdViewHolder) holder;
            adViewHolder.showAd(adViewHolder,position,context);
        }

    }

    @Override
    public int getItemCount() {
        return messagesModelClass.size();
    }
}
