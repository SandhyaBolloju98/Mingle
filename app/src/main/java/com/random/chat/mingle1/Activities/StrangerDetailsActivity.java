package com.random.chat.mingle1.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.random.chat.mingle1.R;
import com.v.ad.vadenhancerLibrary.VAdEnhancer;


public class StrangerDetailsActivity extends AppCompatActivity {
    private String name, gender, chatRoomId, age;
    public TextView otherUserGender, otherUserName, otherUserAge;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    public FirebaseFirestore firebaseFirestore;
    private ImageButton btn_back;
    private VAdEnhancer vAdEnhancer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stranger_details);
        findViews();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();


        Intent intent = getIntent();
        name = intent.getStringExtra("OtherUserName");
        gender = intent.getStringExtra("OtherUserGender");
        age = intent.getStringExtra("OtherUserAge");
        chatRoomId = intent.getStringExtra("ChatRoomId");
        otherUserGender.setText(gender);
        otherUserAge.setText(age);


        firebaseFirestore.collection("Chats")
                .document(chatRoomId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        /* other_user_name.setText(documentSnapshot.getString("User2"));*/
                        otherUserGender.setText(gender);
                    }
                });

        otherUserName.setText(name);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
    public void placeMrecAd(View view) {
        try {
            LinearLayout banner_ad_layout_2 = findViewById(R.id.banner_ad_layout2);
            banner_ad_layout_2.removeAllViews();
            banner_ad_layout_2.addView(view);
        } catch (IllegalStateException e) {
            Log.e("AdsClass", "crashed " + view + " error " + e);
        }
    }

    private void findViews() {
        otherUserGender = findViewById(R.id.other_user_gender);
        otherUserName = findViewById(R.id.other_user_name);
        otherUserAge = findViewById(R.id.other_user_age);
        btn_back = findViewById(R.id.back_btn);
    }
}