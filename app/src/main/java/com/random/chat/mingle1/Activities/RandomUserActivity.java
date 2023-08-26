package com.random.chat.mingle1.Activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.random.chat.mingle1.Adapters.RecyclerViewAdapter;
import com.random.chat.mingle1.ModelClasses.MessagesModelClass;
import com.random.chat.mingle1.R;
import com.random.chat.mingle1.SharedPreferencesL;
import com.v.ad.vadenhancerLibrary.VAdEnhancer;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class RandomUserActivity extends AppCompatActivity {
    public SharedPreferencesL sharedPreferencesL;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private EditText send_message;
    private TextView other_user_name;
    private ImageButton send_btn;
    private Button exitBtn;
    private ImageButton block_btn;
    private ImageButton report_btn;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;
    private ListenerRegistration msgsListener = null;
    private ListenerRegistration leftListener = null;
    private ListenerRegistration blockListener = null;
    private ConstraintLayout parent;
    private String otherUid;
    private String chatRoomId;
    private String deleteId;
    private String deleteNow;
    private String otherUserName, otherUserGender, otherUserAge;
    private String from;
    private VAdEnhancer vAdEnhancer;

    private Spinner spinnerMenu;
    private View blockView, reportView, exitView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_user);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();

        sharedPreferencesL = new SharedPreferencesL(RandomUserActivity.this);

        Intent intent = getIntent();
        otherUid = intent.getStringExtra("OtherUserId");
        chatRoomId = intent.getStringExtra("ChatRoomId");
        otherUserGender = intent.getStringExtra("OtherUserGender");
        otherUserName = intent.getStringExtra("OtherUserName");
        otherUserAge = intent.getStringExtra("OtherUserAge");

        from = intent.getStringExtra("from");

        if (from.equals("direct")) {
            deleteId = intent.getStringExtra("deleteId");
            deleteNow = intent.getStringExtra("deleteNow");
        }

        findViews();
        startBlockListener();
        clickListeners();
        messageListener();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setSubtitle("");

        /*   itemListeners();*/


     /*   ArrayList<String> options =new ArrayList<>();
        options.add("OPTIONS");
        options.add("block");
        options.add("report");
        options.add("exit");
        ArrayAdapter <String> optionsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,options);
        spinnerMenu.setAdapter(optionsAdapter);*/


        //recycler view
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        //layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        //adapter
        recyclerViewAdapter = new RecyclerViewAdapter(RandomUserActivity.this);
        recyclerView.setAdapter(recyclerViewAdapter);


        if (from.equals("direct")) {
            startRoomExitListener();
        }

        if (from.equals("direct")) {
            if (!deleteNow.equals("true")) {
                firebaseFirestore.collection("WaitingRoom")
                        .document(deleteId)
                        .delete();
            }
        }


        if (from.equals("direct")) {
            //ADDING USERS NAME IN CHATROOMID DOCUMENT AS FIELDS, GETTING AND SETTING
            Map<String, Object> data = new HashMap<>();
            data.put("User1", sharedPreferencesL.getUserName());
            data.put("User2", otherUserName);
            data.put("User1Gender", sharedPreferencesL.getUserGender());
            data.put("User2Gender", otherUserGender);
            data.put("User1Age", sharedPreferencesL.getUserAge());
            data.put("User2Age", otherUserAge);
            firebaseFirestore.collection("Chats")
                    .document(chatRoomId)
                    .set(data, SetOptions.merge());
        }
        other_user_name.setText(otherUserName);
    }

    @Override
    protected void onResume() {
        vAdEnhancer = VAdEnhancer.getInstance(this);
        vAdEnhancer.getMultipleBannerAds(this);
        vAdEnhancer.getMultipleMrecAds(this);
        super.onResume();
    }

    public void placeBannerAd(View view) {
        Log.e("VAdEnhancerBanner", "placeBannerAd view " + view);
        try {
            LinearLayout banner_ad_layout = findViewById(R.id.banner_ad_layout);
            banner_ad_layout.removeAllViews();
            banner_ad_layout.addView(view);
        } catch (IllegalStateException e) {
            Log.e("VAdEnhancerBanner", "crashed " + view + " error " + e);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.block:
                blockDialogBox();
                break;
            case R.id.report:
                Toast.makeText(this, "reported", Toast.LENGTH_SHORT).show();
                break;
            case R.id.exit:
                adDialog();
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    private void clickListeners() {
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (send_message.getText().length() > 0) {
                    Log.d("Message sent", "onEvent: sent");
                    messagesStoring();
                } else {
                    Toast.makeText(RandomUserActivity.this, "Type message", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /*info_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RandomUserActivity.this, StrangerDetailsActivity.class);
                intent.putExtra("OtherUserName", otherUserName);
                intent.putExtra("ChatRoomId", chatRoomId);
                intent.putExtra("OtherUserGender", otherUserGender);
                intent.putExtra("OtherUserAge", otherUserAge);
                startActivity(intent);
            }
        });*/
    }

    private void exitDialogueBox() {
        new AlertDialog.Builder(RandomUserActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exit")
                .setMessage("Are you sure, you want to exit ")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                      exitChat();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void adDialog() {
        new AlertDialog.Builder(RandomUserActivity.this)
                .setIcon(android.R.drawable.ic_media_play)
                .setTitle("Watch Advertisement")
                .setMessage("You need to watch an advertisement to Exit.\n\nYou will be shown either Interstitial advertisement or a Rewarded advertisement based on availability.\n\nEither you watch an Interstitial advertisement or Rewarded advertisement,your reward will be the result for the action you are trying to perform. ")
                .setPositiveButton("Watch Advertisement", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (vAdEnhancer.isInterstitialAdReady(RandomUserActivity.this)) {
                            vAdEnhancer.showInterstitialAd(RandomUserActivity.this);
                        } else if (vAdEnhancer.isRewardedVideoAdReady(RandomUserActivity.this)) {
                            vAdEnhancer.showRewardedVideoAd(RandomUserActivity.this);
                        } else {
                           exitDialogueBox();
                        }
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
    public void interstitialAdRewarded(){
        exitDialogueBox();
    }
    public void rewardedAdRewarded(){
        exitDialogueBox();
    }

    private void blockDialogBox() {
        new AlertDialog.Builder(RandomUserActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Block")
                .setMessage("Do you want to block the user")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        blockChat();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void blockChat() {

        if (leftListener != null) {
            leftListener.remove();
        }

        if (msgsListener != null) {
            msgsListener.remove();
        }
        /*  Toast.makeText(RandomUserActivity.this, otherUserName + " removed", Toast.LENGTH_SHORT).show();*/
        Log.e("MainActivity", "my user id = " + sharedPreferencesL.getUserId() + " other user id = " + otherUid);


        if (from.equals("direct")) {
            if (!deleteNow.equals("false")) {
                firebaseFirestore.collection("WaitingRoom")
                        .document(deleteId)
                        .delete();
            }
            Map<String, Object> data = new HashMap<>();

            data.put("Block", true);
            data.put("Left", true);
            firebaseFirestore.collection("Chats")
                    .document(chatRoomId)
                    .set(data, SetOptions.merge());
        } else if (from.equals("chat")) {
            Map<String, Object> data = new HashMap<>();

            data.put("Block", true);
            data.put("Left", true);
            firebaseFirestore.collection("Chats")
                    .document(chatRoomId)
                    .set(data, SetOptions.merge());
        }
        /* finish();*/
    }

    private void exitChat() {
        finish();
        if (leftListener != null) {
            leftListener.remove();
        }

        if (msgsListener != null) {
            msgsListener.remove();
        }
        if (blockListener != null) {
            blockListener.remove();
        }
        Toast.makeText(this, "User Disconnected", Toast.LENGTH_SHORT).show();
        Log.e("MainActivity", "my user id = " + sharedPreferencesL.getUserId() + " other user id = " + otherUid);
        if (from.equals("direct")) {
            if (!deleteNow.equals("false")) {
                firebaseFirestore.collection("WaitingRoom")
                        .document(deleteId)
                        .delete();
            }
        }


        if (from.equals("direct")) {
            Map<String, Object> data = new HashMap<>();
            data.put("Left", true);
            firebaseFirestore.collection("Chats")
                    .document(chatRoomId)
                    .set(data, SetOptions.merge());
        }

    }


    private void findViews() {
        send_message = findViewById(R.id.send_message);
        send_btn = findViewById(R.id.send_btn);
        /*   exitBtn = findViewById(R.id.exitBtn);*/
        other_user_name = findViewById(R.id.other_user_name);
        parent = findViewById(R.id.parent);
        blockView = findViewById(R.id.block);
        reportView = findViewById(R.id.report);
        exitView = findViewById(R.id.exit);
       // info_btn = findViewById(R.id.info);

        if (from.equals("chat")) {
          /*  exitBtn.setVisibility(View.GONE);
            block_btn.setVisibility(View.GONE);*/
        }
    }

    private void startBlockListener() {
        blockListener = firebaseFirestore.collection("Chats")
                .document(chatRoomId)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value.contains("Block")) {
                            if (value.getBoolean("Block")) {

                                if (msgsListener != null) {
                                    msgsListener.remove();
                                }

                                if (leftListener != null) {
                                    leftListener.remove();
                                }
                                if (blockListener != null) {
                                    blockListener.remove();

                                    new AlertDialog.Builder(RandomUserActivity.this)
                                            .setIcon(android.R.drawable.ic_media_play)
                                            .setTitle("User Blocked ")
                                            .setMessage("exit chat ")
                                            .setCancelable(false)
                                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    firebaseFirestore.collection("Users")
                                                            .document(sharedPreferencesL.getUserId())
                                                            .collection("Chats")
                                                            .document(chatRoomId)
                                                            .delete();

                                                    Intent intent = new Intent(RandomUserActivity.this, MainActivity.class);
                                                    startActivity(intent);
                                                }
                                            })
                                            .setNegativeButton("No", null)
                                            .show();
                                }
                            }
                        }
                    }
                });
    }
    private void startRoomExitListener() {
        leftListener = firebaseFirestore.collection("Chats")
                .document(chatRoomId)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value.contains("Left")) {
                            if (value.getBoolean("Left")) {

                                if (msgsListener != null) {
                                    msgsListener.remove();
                                }
                                if (leftListener != null) {
                                    leftListener.remove();
                                }
                                new AlertDialog.Builder(RandomUserActivity.this)
                                        .setIcon(android.R.drawable.ic_media_play)
                                        .setTitle("User Left,Watch Advertisement")
                                        .setMessage("You need to watch an advertisement to Exit.\n\nYou will be shown either Interstitial advertisement or a Rewarded advertisement based on availability.\n\nEither you watch an Interstitial advertisement or Rewarded advertisement,your reward will be the result for the action you are trying to perform. ")
                                        .setPositiveButton("Watch Advertisement", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                if (vAdEnhancer.isInterstitialAdReady(RandomUserActivity.this)) {
                                                    vAdEnhancer.showInterstitialAd(RandomUserActivity.this);
                                                } else if (vAdEnhancer.isRewardedVideoAdReady(RandomUserActivity.this)) {
                                                    vAdEnhancer.showRewardedVideoAd(RandomUserActivity.this);
                                                } else {
                                                    exitDialogueBox();
                                                }
                                            }
                                        })
                                        .setNegativeButton("No", null)
                                        .show();
                            }
                        }
                    }
                });
    }

    public void messagesStoring() {
        Log.e("MainActivity", "msg kept in chatroom = " + chatRoomId);


        long time = System.currentTimeMillis() / 1000L;

        Map<String, Object> data = new HashMap<>();
        data.put("Message", send_message.getText().toString());
        data.put("Message_user_name", sharedPreferencesL.getUserName());
        data.put("Message_user_id", sharedPreferencesL.getUserId());
        data.put("Message_time", time);
        data.put("banner_ad", false);

        firebaseFirestore.collection("Chats")
                .document(chatRoomId)
                .collection("Messages")
                .document(String.valueOf(time))
                .set(data);

        send_message.getText().clear();
    }

    public void messageListener() {
        msgsListener = firebaseFirestore.collection("Chats")
                .document(chatRoomId)
                .collection("Messages")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    final ArrayList<MessagesModelClass> messagesList = new ArrayList<>();

                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value != null) {

                            for (DocumentChange dc : value.getDocumentChanges()) {

                                DocumentSnapshot documentSnapshot = dc.getDocument();

                                switch (dc.getType()) {

                                    case ADDED:

                                        if (messagesList.size() > 0) {

                                            messagesList.add(messagesList.size(), new MessagesModelClass(documentSnapshot.getString("Message_user_id"),
                                                    documentSnapshot.getString("Message_user_name"),
                                                    documentSnapshot.getString("Message"),
                                                    documentSnapshot.getLong("Message_time"),
                                                    false));
                                            recyclerViewAdapter.setMessagesModelClass(messagesList);
                                            recyclerViewAdapter.notifyItemInserted(messagesList.size());
                                            recyclerView.smoothScrollToPosition(messagesList.size());

                                            if (messagesList.size() % 5 == 0) {
                                                messagesList.add(messagesList.size(), new MessagesModelClass("",
                                                        "",
                                                        "",
                                                        0,
                                                        true));
                                            }

                                        } else {
                                            messagesList.add(0, new MessagesModelClass(documentSnapshot.getString("Message_user_id"),
                                                    documentSnapshot.getString("Message_user_name"),
                                                    documentSnapshot.getString("Message"),
                                                    documentSnapshot.getLong("Message_time"),
                                                    false));
                                            recyclerViewAdapter.setMessagesModelClass(messagesList);
                                            recyclerViewAdapter.notifyItemInserted(0);
                                            recyclerView.smoothScrollToPosition(0);

                                            if (messagesList.size() % 5 == 0) {
                                                messagesList.add(0, new MessagesModelClass("",
                                                        "",
                                                        "",
                                                        0,
                                                        true));
                                            }

                                        }


                                        break;

                                }

                            }
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {

        if (from.equals("direct")) {
           adDialog();
        } else {
            finish();
        }

    }


}