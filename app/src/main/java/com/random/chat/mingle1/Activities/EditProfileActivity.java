package com.random.chat.mingle1.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.SetOptions;
import com.random.chat.mingle1.R;
import com.random.chat.mingle1.SharedPreferencesL;
import com.v.ad.vadenhancerLibrary.VAdEnhancer;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {
    public SharedPreferencesL sharedPreferencesL;
    private EditText name, age;
    private Button btn_save;
    private ImageView btn_back;
    private ImageButton my_profile;
    private RadioGroup radioGroup;
    private RadioButton maleBtn, femaleBtn;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private ListenerRegistration listenerRegistration = null;
    private String Gender;
    private String Sex;
    private VAdEnhancer vAdEnhancer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        sharedPreferencesL = new SharedPreferencesL(EditProfileActivity.this);
        findViews();
        clickListeners();
        name.setText(sharedPreferencesL.getUserName());
        age.setText(sharedPreferencesL.getUserAge());


        if (sharedPreferencesL.getUserGender().equals("Male")) {
            maleBtn.setChecked(true);
        } else {
            femaleBtn.setChecked(true);
        }
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

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.male_btn:
                        Sex = "Male";
                        break;
                    case R.id.female_btn:
                        Sex = "female";
                        break;
                }
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().length() == 0) {
                    Log.e("RegisterUserActivity", "name not entered");
                    Toast.makeText(EditProfileActivity.this, "please  enter name", Toast.LENGTH_SHORT).show();
                } else if (name.getText().toString().length() >= 24) {
                    Log.e("RegisterUserActivity", " name should not exceed 24 characters");
                    Toast.makeText(EditProfileActivity.this, "name should not exceed 24 characters", Toast.LENGTH_SHORT).show();
                } else if (age.getText().toString().length() == 0) {
                    Toast.makeText(EditProfileActivity.this, "please enter your age", Toast.LENGTH_SHORT).show();
                } else {
                    new AlertDialog.Builder(EditProfileActivity.this)
                            .setIcon(android.R.drawable.ic_media_play)
                            .setTitle("Watch Advertisement")
                            .setMessage("You need to watch an advertisement to save.\n\nYou will be shown either Rewarded advertisement or a Interstitial advertisement based on availability.\n\nEither you watch an Rewarded advertisement or Interstitial advertisement,your reward will be the result for the action you are trying to perform.")
                            .setPositiveButton("Watch Advertisement", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (vAdEnhancer.isRewardedVideoAdReady(EditProfileActivity.this)) {
                                        vAdEnhancer.showRewardedVideoAd(EditProfileActivity.this);
                                    } else if (vAdEnhancer.isInterstitialAdReady(EditProfileActivity.this)) {
                                        vAdEnhancer.showInterstitialAd(EditProfileActivity.this);
                                    } else {
                                        updateInfo();
                                    }
                                }
                            })
                            .setNegativeButton("Cancel", null)
                            .show();
                }
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void updateInfo() {
        if (name.getText().toString().length() > 0) {

            //update info in waiting room
            Map<String, Object> userdata = new HashMap<>();
            userdata.put("Name", name.getText().toString());
            userdata.put("Age", age.getText().toString());
            userdata.put("Gender", Sex);
            firebaseFirestore.collection("Users")
                    .document(sharedPreferencesL.getUserId())
                    .set(userdata, SetOptions.merge())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                            sharedPreferencesL.setUserName(name.getText().toString());
                            sharedPreferencesL.setUserAge(age.getText().toString());
                            sharedPreferencesL.setUserGender(Sex);
                            Toast.makeText(EditProfileActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                            /*  finish();*/

                        }
                    });
        } else {
            Toast.makeText(EditProfileActivity.this, "please enter a valid name", Toast.LENGTH_SHORT).show();
        }
    }

    private void findViews() {
        name = findViewById(R.id.name);
        btn_save = findViewById(R.id.btn_save);
        btn_back = findViewById(R.id.btn_back);
        age = findViewById(R.id.edited_age);
        maleBtn = findViewById(R.id.male_btn);
        femaleBtn = findViewById(R.id.female_btn);
        radioGroup = findViewById(R.id.radio_Group);
        my_profile = findViewById(R.id.my_profile);
    }

}