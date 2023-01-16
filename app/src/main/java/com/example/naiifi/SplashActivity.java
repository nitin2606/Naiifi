package com.example.naiifi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth ;
    private DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_splash);

        /*firebaseAuth = FirebaseAuth.getInstance();
        String uid = firebaseAuth.getUid();
        Log.d("userData", "onCreate: UserID "+uid);*/


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {


                firebaseAuth = FirebaseAuth.getInstance();

                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    Intent intent = new Intent(SplashActivity.this, RegisterActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    startActivity(intent);

                    finish();

                } else {

                    try {

                        String uid = FirebaseAuth.getInstance().getUid();
                        DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference("userPhoneNo").child(uid);

                        firebaseDatabase.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String value = snapshot.getValue(String.class);

                                databaseReference = FirebaseDatabase.getInstance().getReference("userData").child(value).child("status");

                                databaseReference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String status = snapshot.getValue(String.class);

                                        if (status.equals("inComplete")) {
                                            Intent mainIntent = new Intent(SplashActivity.this, IntroActivity.class);

                                            startActivity(mainIntent);
                                            finish();

                                        } else if (status.equals("complete")) {
                                            Intent mainIntent = new Intent(SplashActivity.this, DashBoardActivity.class);
                                            //mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(mainIntent);

                                            finish();
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    } catch (Exception e) {
                        Log.d("Status check error :", e.getMessage());
                    }

                }
            }
        });


        thread.start();

    }
}