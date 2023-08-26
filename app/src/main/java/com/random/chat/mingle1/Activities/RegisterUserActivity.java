package com.random.chat.mingle1.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.random.chat.mingle1.R;
import com.random.chat.mingle1.SharedPreferencesL;
import com.v.ad.vadenhancerLibrary.VAdEnhancer;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.Map;

public class RegisterUserActivity extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    private EditText user_name, edited_age;
    private Button btn_login;
    public String userId;
    private TextView app_name;
    private TextView terms;
    private CheckBox check_box;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private String Gender = "Male";

    private VAdEnhancer vAdEnhancer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        findViews();
        clickListener();


        if (firebaseAuth.getUid() != null) {
            Log.e("RegisterUserActivity", " go to main activity ");
            Intent intent = new Intent(RegisterUserActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

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

    private void clickListener() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == 0) {
                    Gender = "Male";
                } else {
                    Gender = "Female";
                }
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Log.e("RegisterUserActivity", " usernme = " + user_name.getText() + " gender = " + Gender);

                if (user_name.getText().toString().length() == 0) {
                    Log.e("RegisterUserActivity", "name not entered");
                    Toast.makeText(RegisterUserActivity.this, "please  enter name", Toast.LENGTH_SHORT).show();
                } else if (user_name.getText().toString().length() >= 24) {
                    Log.e("RegisterUserActivity", " name should not exceed 24 characters");
                    Toast.makeText(RegisterUserActivity.this, "name should not exceed 24 characters", Toast.LENGTH_SHORT).show();
                } else if (edited_age.getText().toString().length() == 0) {
                    Toast.makeText(RegisterUserActivity.this, "please enter your age", Toast.LENGTH_SHORT).show();
                } else if (!check_box.isChecked()) {
                    Toast.makeText(RegisterUserActivity.this, "Please agree our terms and conditions to go further", Toast.LENGTH_SHORT).show();
                } else {
                    btn_login.setAlpha(0.5F);
                    btn_login.setEnabled(false);
                    loginUser();
                }

            }


        });
        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicked_terms("http://sandyaapps.com");
            }
        });

    }

    public void clicked_terms(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    private void loginUser() {
        firebaseAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                            Map<String, Object> userdata = new HashMap<>();
                            userdata.put("Name", user_name.getText().toString());
                            if (firebaseUser != null) {

                                userdata.put("ID", firebaseUser.getUid());
                                userdata.put("Age", edited_age.getText().toString());
                                userdata.put("Gender", Gender);
                                firebaseFirestore.collection("Users")
                                        .document(firebaseUser.getUid())
                                        .set(userdata)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Log.d("MainActivity", "onComplete:  ");


                                                SharedPreferencesL sharedPreferencesL = new SharedPreferencesL(RegisterUserActivity.this);
                                                sharedPreferencesL.setUserName(user_name.getText().toString());
                                                sharedPreferencesL.setUserId(firebaseUser.getUid());
                                                sharedPreferencesL.setUserGender(Gender);
                                                sharedPreferencesL.setUserAge(edited_age.getText().toString());
                                                Toast.makeText(RegisterUserActivity.this, "signed in", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(RegisterUserActivity.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();

                                            }
                                        });
                            } else {
                                Log.e("MainActivity", "task fail");
                                Toast.makeText(RegisterUserActivity.this, "not signed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void findViews() {
        user_name = findViewById(R.id.edited_name);
        btn_login = findViewById(R.id.btn_login);
        terms = findViewById(R.id.terms);
        app_name = findViewById(R.id.app_name);
        check_box = findViewById(R.id.check_box);
        radioGroup = findViewById(R.id.radio_Group);
        edited_age = findViewById(R.id.edited_age);
    }

}