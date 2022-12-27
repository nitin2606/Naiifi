package com.example.naiifi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.naiifi.databinding.ActivityIntroBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;

public class IntroActivity extends AppCompatActivity {

    ActivityIntroBinding activityIntroBinding ;
    private FirebaseAuth firebaseAuth ;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityIntroBinding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(activityIntroBinding.getRoot());


        activityIntroBinding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = activityIntroBinding.txtEmail.getText().toString().trim();
                String name = activityIntroBinding.txtName.getText().toString().trim();
                String age = activityIntroBinding.txtAge.getText().toString().trim();

                uploadData(email , name , age);


            }
        });


    }

    private void uploadData(String email , String name , String age){

        databaseReference = FirebaseDatabase.getInstance().getReference("userData");

        SharedPreferences sharedPreferences = getSharedPreferences("userData", MODE_APPEND);

       // String firebaseId = sharedPreferences.getString("firebaseId","");
        String phoneNumber = sharedPreferences.getString("phoneNo","");



        HashMap<String , Object> map = new HashMap<>();

        map.put("emailId",email);
        map.put("name",name);
        map.put("age",age);
        map.put("status","complete");

        databaseReference.child(phoneNumber).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(IntroActivity.this, "Data Uploaded !", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(IntroActivity.this, DashBoardActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}