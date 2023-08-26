package com.random.chat.mingle1.ViewHolders;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
import com.random.chat.mingle1.Activities.RandomUserActivity;
import com.random.chat.mingle1.ModelClasses.ChatsModelClass;
import com.random.chat.mingle1.R;

public class ChatsViewHolder extends RecyclerView.ViewHolder {
    public TextView strangerName, message, age;
    private RelativeLayout parent;
    private ConstraintLayout holder;
    public String chatRoomId;
    public String userId;
    public String userName;
    public String userGender, userAge;
    public TextView time;
    public ImageView stranger_profile;
    public ListenerRegistration listenerRegistration = null;
    public String deleteId;
    public String deleteNow;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    public ChatsViewHolder(@NonNull View itemView) {
        super(itemView);
        strangerName = itemView.findViewById(R.id.strangername);
        parent = itemView.findViewById(R.id.parent);
        time = itemView.findViewById(R.id.time);
        message = itemView.findViewById(R.id.message_text);
        holder = itemView.findViewById(R.id.holder);
        stranger_profile = itemView.findViewById(R.id.stranger_profile);
    }
    public void keepStrangerName(ChatsModelClass chatsModelClass, Context context) {
        strangerName.setText(chatsModelClass.getName());
        userId = chatsModelClass.getUserId();
        userName = chatsModelClass.getName();
        chatRoomId = chatsModelClass.getChatRoomId();
        userGender = chatsModelClass.getGender();
        userAge = chatsModelClass.getAge();

        /*  chatsViewHolder.message.setText(messagesModelClass.getMessage());*/

        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RandomUserActivity.class);
                intent.putExtra("ChatRoomId", chatRoomId);
                intent.putExtra("OtherUserId", userId);
                intent.putExtra("OtherUserGender", userGender);
                intent.putExtra("OtherUserName", userName);
                intent.putExtra("OtherUserAge", userAge);
                intent.putExtra("from", "chat");
                view.getContext().startActivity(intent);
            }
        });

      /*  firebaseFirestore.collection("Chats")
                .document(chatRoomId)
                .collection("Messages")
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        *//* other_user_name.setText(documentSnapshot.getString("User2"));*//*
                        otherUserGender.setText(gender);
                    }
                });*/

    }

    /* private void deleteChat() {
         firebaseFirestore.collection("WaitingRoom")
                 .document(deleteId)
                 .delete();
     }
 */
   private void  messageListener(){

       listenerRegistration =  firebaseFirestore.collection("Chats")
               .document(chatRoomId)
               .collection("Messages")
               .addSnapshotListener(new EventListener<QuerySnapshot>() {
                   @Override
                   public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                       if (value != null){
                           for (DocumentChange dc : value.getDocumentChanges()) {
                               DocumentSnapshot documentSnapshot = dc.getDocument();



                               if (dc.getType() == DocumentChange.Type.ADDED) {
                                   message.setText(documentSnapshot.getString("Message"));

                                   time.setText(documentSnapshot.getString("Message_time"));
                                  /* if (!(listenerRegistration == null)) {
                                       listenerRegistration.remove();
                                   }*/
                               }
                           }
                       }
                   }

               });
       listenerRegistration.remove();}
  /*  public void keepTime(MessagesModelClass message, Context context) {

        listenerRegistration = firebaseFirestore.collection("Chats")
                .document(chatRoomId)
                .collection("Messages")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value != null) {
                            for (DocumentChange dc : value.getDocumentChanges()) {
                                DocumentSnapshot documentSnapshot = dc.getDocument();

                                if (dc.getType() == DocumentChange.Type.ADDED) {
                                    documentSnapshot.getString("Message_time");
                                    time.setText(documentSnapshot.getString("Message_time"));
                                    if (!(listenerRegistration == null)) {
                                        listenerRegistration.remove();
                                    }
                                }
                            }
                        }
                    }
                });

        time.setText((int) message.getMessage_time());
    }*/
}
