package com.example.naiifi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.naiifi.databinding.ActivityDashBoardBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class DashBoardActivity extends AppCompatActivity {


    private FirebaseAuth firebaseAuth ;
    private DatabaseReference databaseReference ;

    private static final String TAG = "DataTag" ;
    HashMap<String , String> hashMap ;

    ActivityDashBoardBinding activityDashBoardBinding ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_dash_board);

        activityDashBoardBinding = ActivityDashBoardBinding.inflate(getLayoutInflater());
        setContentView(activityDashBoardBinding.getRoot());

        SharedPreferences sharedPreferences = getSharedPreferences("Naiifi",MODE_PRIVATE);
        String value  = sharedPreferences.getString("value","");
        activityDashBoardBinding.textView.setText(value);

        firebaseAuth = FirebaseAuth.getInstance();

        hashMap = new HashMap<>();

        String firebaseId = firebaseAuth.getCurrentUser().getUid().toString();

        activityDashBoardBinding.firebaseId.setText(firebaseId);

        databaseReference = FirebaseDatabase.getInstance().getReference("PhoneNo");

        databaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                for(DataSnapshot snapshot : task.getResult().getChildren()){

                    String key  = snapshot.getKey();
                    String value = (String)snapshot.getValue();

                    hashMap.put(key , value);

                    activityDashBoardBinding.txtInfo.setText(key+" : "+value);
                }
            }
        }).addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onSuccess: "+hashMap.toString());
            }
        });


    }
}