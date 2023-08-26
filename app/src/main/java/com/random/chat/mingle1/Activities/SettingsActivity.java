package com.random.chat.mingle1.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.random.chat.mingle1.R;

public class SettingsActivity extends AppCompatActivity {
    private TextView myAccount,share_text,delete_text;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setttings);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        findViews();
        clickListeners();

    }

    private void clickListeners() {
        myAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingsActivity.this, "clicked", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(SettingsActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });
        share_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingsActivity.this, "sdn", Toast.LENGTH_SHORT).show();
            }
        });
        delete_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /* try{
                   String packageName = getApplicationContext().getPackageName();
                   Runtime runtime = Runtime.getRuntime();
                   runtime.exec("pm clear "+packageName);

               }catch (Exception e){
                   e.printStackTrace();

               }

                if (firebaseAuth.getUid() == null) {
                    Log.e("RegisterUserActivity", " go to main activity ");
                    Intent intent = new Intent(SettingsActivity.this, RegisterUserActivity.class);
                    startActivity(intent);

                }*/


             /*   firebaseFirestore.collection("Users")
                        .document(firebaseUser.getUid())
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Intent intent = new Intent(SettingsActivity.this, RegisterUserActivity.class);
                                startActivity(intent);
                            }
                        });*/

            }
        });

    }

    private void findViews() {
        myAccount = findViewById(R.id.my_account);
        share_text = findViewById(R.id.shareText);
        delete_text = findViewById(R.id.deleteText);

    }

}