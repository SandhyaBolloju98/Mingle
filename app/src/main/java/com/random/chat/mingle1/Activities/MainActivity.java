package com.random.chat.mingle1.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.random.chat.mingle1.Adapters.ChatsAdapter;
import com.random.chat.mingle1.ModelClasses.ChatsModelClass;
import com.random.chat.mingle1.ModelClasses.MessagesModelClass;
import com.random.chat.mingle1.R;
import com.random.chat.mingle1.SharedPreferencesL;
import com.v.ad.vadenhancerLibrary.VAdEnhancer;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity {
    public SharedPreferencesL sharedPreferencesL;
    private FirebaseUser firebaseUser;
    private ImageButton search_btn;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private ListenerRegistration listenerRegistration = null;
    private ListenerRegistration chatListener = null;
    private ListenerRegistration onBackListener = null;
    private int count = 0;


    /* private ProgressBar progress_bar;*/
    private ImageView settings_btn;

    private RecyclerView recyclerView;
    private ChatsAdapter chatsAdapter;

    private ArrayList<ChatsModelClass> chatList = new ArrayList<>();
    private ArrayList<String> chatListTemp = new ArrayList<>();
    private final ArrayList<MessagesModelClass> messageList = new ArrayList<>();
    private VAdEnhancer vAdEnhancer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();


        //recycler view
        recyclerView = findViewById(R.id.recycler_view2);
        recyclerView.setHasFixedSize(true);

        //layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        //adapter
        chatsAdapter = new ChatsAdapter(MainActivity.this);

        recyclerView.setAdapter(chatsAdapter);


        sharedPreferencesL = new SharedPreferencesL(MainActivity.this);

        findViews();
        clickListeners();
        chatListener();
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

    private void clickListeners() {
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_btn.setAlpha(0.5F);
                search_btn.setEnabled(false);
                count++;
                /*  Toast.makeText(MainActivity.this, "" + count, Toast.LENGTH_SHORT).show();*/
                /*   searchUser();*/

                if (count % 2 == 0) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setIcon(android.R.drawable.ic_media_play)
                            .setTitle("Watch Advertisement")
                            .setMessage("You need to watch an advertisement to Search.\n\nYou will be shown either Rewarded advertisement or a Interstitial advertisement based on availability.\n\nEither you watch an Rewarded advertisement or Interstitial advertisement,your reward will be the result for the action you are trying to perform.")

                            .setPositiveButton("Watch Advertisement", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (vAdEnhancer.isRewardedVideoAdReady(MainActivity.this)) {
                                        vAdEnhancer.showRewardedVideoAd(MainActivity.this);

                                    } else if (vAdEnhancer.isInterstitialAdReady(MainActivity.this)) {
                                        vAdEnhancer.showInterstitialAd(MainActivity.this);

                                    } else {
                                        searchUser();
                                    }
                                    count = 0;
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();


                } else {

                    searchUser();
                }
            }
        });

        settings_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (firebaseAuth.getUid() != null) {
                    Log.e("RegisterUserActivity", " go to main activity ");

                    Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
                    startActivity(intent);

                }
            }
        });


    }
    public void interstitialAdRewarded(){
        searchUser();
    }
    public void rewardedAdRewarded(){
        searchUser();
    }

    private void searchUser() {

        Log.e("MainActivity", "searchUser()");

        firebaseFirestore.collection("WaitingRoom")
                .whereNotEqualTo("default", true)
                .limit(1)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot querySnapshot) {
                        if (!querySnapshot.isEmpty()) {
                            //some other user is present in the waiting room
                            Log.e("MainActivity", "collection have documents");
                            Log.d("MainActivity", "onSuccess: " + sharedPreferencesL.getUserId());

                            for (DocumentSnapshot documentSnapshot : querySnapshot.getDocuments()) {
                                Log.e("MainActivity", "inside for loop");
                                Log.e("MainActivity", "User found");

                                long time = System.currentTimeMillis() / 1000L;
                                String ChatRoomId = "chatroom_" + time;
                                if (!sharedPreferencesL.getUserId().equals(documentSnapshot.getString("OwnerId"))) {

                                    //setting in firebase in the document of the user found
                                    Map<String, Object> data3 = new HashMap<>();
                                    data3.put("ChatRoomId", ChatRoomId);
                                    data3.put("JoinerId", sharedPreferencesL.getUserId());
                                    data3.put("JoinerName", sharedPreferencesL.getUserName());
                                    data3.put("JoinerGender", sharedPreferencesL.getUserGender());
                                    data3.put("JoinerAge", sharedPreferencesL.getUserAge());
                                    firebaseFirestore.collection("WaitingRoom")
                                            .document(documentSnapshot.getId())
                                            .set(data3, SetOptions.merge());

                                    //our side
                                    Map<String, Object> data4 = new HashMap<>();
                                    data4.put("ChatRoomId", ChatRoomId);
                                    data4.put("user_id", documentSnapshot.getString("OwnerId"));
                                    data4.put("user_name", documentSnapshot.getString("OwnerName"));
                                    data4.put("user_gender", documentSnapshot.getString("OwnerGender"));
                                    data4.put("user_age", documentSnapshot.getString("OwnerAge"));
                                    firebaseFirestore.collection("Users")
                                            .document(sharedPreferencesL.getUserId())
                                            .collection("Chats")
                                            .document(ChatRoomId)
                                            .set(data4);

                                  /*  //other side
                                    Map<String, Object> data5 = new HashMap<>();
                                    data5.put("ChatRoomId", ChatRoomId);
                                    data5.put("user_id", sharedPreferencesL.getUserId());
                                    data5.put("user_name", sharedPreferencesL.getUserName());
                                    data5.put("user_gender", sharedPreferencesL.getUserGender());
                                    data5.put("user_age", sharedPreferencesL.getUserAge());
                                    firebaseFirestore.collection("Users")
                                            .document(documentSnapshot.getString("OwnerId"))
                                            .collection("Chats")
                                            .document(ChatRoomId)
                                            .set(data5);
*/
                                    search_btn.setEnabled(true);
                                    /* search_btn.setAlpha(1);*/

                                    //opening chat activity in our phone
                                    Intent intent = new Intent(MainActivity.this, RandomUserActivity.class);
                                    intent.putExtra("ChatRoomId", ChatRoomId);
                                    intent.putExtra("OtherUserId", documentSnapshot.getString("OwnerId"));
                                    intent.putExtra("deleteId", documentSnapshot.getString("OwnerId"));
                                    intent.putExtra("deleteNow", "false");
                                    intent.putExtra("OtherUserGender", documentSnapshot.getString("OwnerGender"));
                                    intent.putExtra("OtherUserName", documentSnapshot.getString("OwnerName"));
                                    intent.putExtra("OtherUserAge", documentSnapshot.getString("OwnerAge"));
                                    intent.putExtra("from", "direct");
                                    startActivity(intent);


                                }

                            }

                        } else { //if there is no other  user in waiting room current user will enter into waiting room
                            Log.e("MainActivity", "collection empty");
                            Map<String, Object> userData = new HashMap<>();
                            userData.put("OwnerId", sharedPreferencesL.getUserId());
                            userData.put("default", false);
                            userData.put("OwnerName", sharedPreferencesL.getUserName());
                            userData.put("OwnerGender", sharedPreferencesL.getUserGender());
                            userData.put("OwnerAge", sharedPreferencesL.getUserAge());
                            if (sharedPreferencesL.getUserId() != null)
                                firebaseFirestore.collection("WaitingRoom")
                                        .document(sharedPreferencesL.getUserId())
                                        .set(userData, SetOptions.merge())
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Log.e("MainActivity", "joined collection");
                                                Log.e("MainActivity", "turning listener on");
                                                //after entering waiting room listener is turned on to trigger event
                                                listenerRegistration = firebaseFirestore.collection("WaitingRoom")
                                                        .whereEqualTo("OwnerId", sharedPreferencesL.getUserId())
                                                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                                            @Override
                                                            public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException error) {

                                                                for (QueryDocumentSnapshot documentSnapshot : querySnapshot) {
                                                                    if (documentSnapshot.contains("ChatRoomId") && documentSnapshot.contains("JoinerId")) {
                                                                        Log.e("MainActivity", "listener worked");
                                                                        //when the doc contains other user details listener turned off and enter into chat activity
                                                                        listenerRegistration.remove();

                                                                        search_btn.setEnabled(true);
                                                                        /* search_btn.setAlpha(1);
                                                                         */
                                                                        //our side
                                                                        Map<String, Object> data4 = new HashMap<>();
                                                                        data4.put("ChatRoomId", documentSnapshot.getString("ChatRoomId"));
                                                                        data4.put("user_id", documentSnapshot.getString("JoinerId"));
                                                                        data4.put("user_name", documentSnapshot.getString("JoinerName"));
                                                                        data4.put("user_gender", documentSnapshot.getString("JoinerGender"));
                                                                        data4.put("user_age", documentSnapshot.getString("JoinerAge"));
                                                                        firebaseFirestore.collection("Users")
                                                                                .document(sharedPreferencesL.getUserId())
                                                                                .collection("Chats")
                                                                                .document(documentSnapshot.getString("ChatRoomId"))
                                                                                .set(data4);

                                                                        //other side
                                                                    /*    Map<String, Object> data5 = new HashMap<>();
                                                                        data5.put("ChatRoomId", documentSnapshot.getString("ChatRoomId"));
                                                                        data5.put("user_id", sharedPreferencesL.getUserId());
                                                                        data5.put("user_name",sharedPreferencesL.getUserName());
                                                                        data5.put("user_gender", sharedPreferencesL.getUserGender());
                                                                        data5.put("user_age", sharedPreferencesL.getUserAge());
                                                                        firebaseFirestore.collection("Users")
                                                                                .document(documentSnapshot.getString("OwnerId"))
                                                                                .collection("Chats")
                                                                                .document(documentSnapshot.getString("ChatRoomId"))
                                                                                .set(data5);*/

                                                                        //opening chat activity
                                                                        Intent intent = new Intent(MainActivity.this, RandomUserActivity.class);
                                                                        intent.putExtra("ChatRoomId", documentSnapshot.getString("ChatRoomId"));
                                                                        intent.putExtra("OtherUserId", documentSnapshot.getString("JoinerId"));
                                                                        intent.putExtra("deleteId", documentSnapshot.getId());
                                                                        intent.putExtra("deleteNow", "true");
                                                                        intent.putExtra("OtherUserGender", documentSnapshot.getString("JoinerGender"));
                                                                        intent.putExtra("OtherUserName", documentSnapshot.getString("JoinerName"));
                                                                        intent.putExtra("OtherUserAge", documentSnapshot.getString("JoinerAge"));
                                                                        intent.putExtra("from", "direct");
                                                                        startActivity(intent);
                                                                    }
                                                                }
                                                            }
                                                        });

                                            }
                                        });
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("MainActivity", "error  ", e);
                    }
                });
    }

    public void chatListener() {
        chatListener = firebaseFirestore.collection("Users")
                .document(sharedPreferencesL.getUserId())
                .collection("Chats")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value != null) {
                            for (DocumentChange dc : value.getDocumentChanges()) {
                                DocumentSnapshot documentSnapshot = dc.getDocument();
                                switch (dc.getType()) {

                                    case ADDED:
                                        if (!chatListTemp.contains(documentSnapshot.getString("user_id"))) {

                                            if (chatList.size() > 0) {
                                                chatList.add(0, new ChatsModelClass(
                                                        documentSnapshot.getString("user_name"),
                                                        documentSnapshot.getString("user_id"),
                                                        documentSnapshot.getString("ChatRoomId"),
                                                        documentSnapshot.getString("user_gender"),
                                                        documentSnapshot.getString("user_age")));
                                                chatsAdapter.setStrangerNamesModelClass(chatList);
                                                chatsAdapter.notifyItemRangeChanged(0, chatList.size());
                                                recyclerView.smoothScrollToPosition(0);

                                            } else {
                                                chatList.add(chatList.size(), new ChatsModelClass(
                                                        documentSnapshot.getString("user_name"),
                                                        documentSnapshot.getString("user_id"),
                                                        documentSnapshot.getString("ChatRoomId"),
                                                        documentSnapshot.getString("user_gender"),
                                                        documentSnapshot.getString("user_age")));
                                                chatsAdapter.setStrangerNamesModelClass(chatList);
                                                chatsAdapter.notifyItemRangeChanged(0, chatList.size());
                                                recyclerView.smoothScrollToPosition(0);
                                            }

                                        }
                                        break;
                                    case MODIFIED:
                                        int modifiedPosition = chatListTemp.indexOf(documentSnapshot.getString("user_id"));
                                        chatList.add(0, new ChatsModelClass(
                                                documentSnapshot.getString("user_name"),
                                                documentSnapshot.getString("user_id"),
                                                documentSnapshot.getString("ChatRoomId"),
                                                documentSnapshot.getString("user_gender"),
                                                documentSnapshot.getString("user_age")));
                                        chatsAdapter.setStrangerNamesModelClass(chatList);
                                        chatsAdapter.notifyItemChanged(modifiedPosition);
                                        chatsAdapter.notifyItemRangeChanged(modifiedPosition, 0);
                                        recyclerView.smoothScrollToPosition(0);
                                        break;
                                    case REMOVED:
                                        finish();
                                        startActivity(getIntent());

                                      /*  int removePos = chatListTemp.indexOf("OtherUserName");
                                        chatList.remove(removePos);*/
                                        /*   chatsAdapter.notifyItemRemoved(removePos);*/
                                       /* recyclerView.smoothScrollToPosition(0);
                                        finish();
                                        startActivity(getIntent());*/

                                            /*int removedPosition = dc.getNewIndex();
                                            chatList.remove(removedPosition, new ChatsModelClass(documentSnapshot.getString("user_name"),
                                                    documentSnapshot.getString("user_id"),
                                                    documentSnapshot.getString("ChatRoomId")));
                                            chatsAdapter.notifyItemRemoved(removedPosition);
                                            chatsAdapter.notifyItemRangeChanged(0, chatList.size()-1);
                                            recyclerView.smoothScrollToPosition(0);*/
                                      /*  case REMOVED:
                                            int removedPosition = chatListTemp.indexOf(documentSnapshot.getString("user_id"));
                                            Toast.makeText(MainActivity.this, "holder deleted", Toast.LENGTH_SHORT).show();
                                            chatList.remove(removedPosition,new ChatsModelClass(
                                                    documentSnapshot.getString("user_name"),
                                                    documentSnapshot.getString("user_id"),
                                                    documentSnapshot.getString("ChatRoomId")));
                                            chatsAdapter.setStrangerNamesModelClass(chatList);
                                            chatsAdapter.notifyItemRangeChanged(0, chatList.size());
                                            recyclerView.smoothScrollToPosition(0);
*/


                                }
                            }
                        }
                    }


                });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /* progress_bar.setVisibility(View.INVISIBLE);*/
        if (!(chatListener == null)) {
            chatListener.remove();
        }
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    private void findViews() {
        search_btn = findViewById(R.id.search_btn);
        settings_btn = findViewById(R.id.settings_btn);
    }
}
