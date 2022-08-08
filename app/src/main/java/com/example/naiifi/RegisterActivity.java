package com.example.naiifi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.example.naiifi.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class RegisterActivity extends AppCompatActivity {


    private FirebaseAuth firebaseAuth  ;

    ActivityRegisterBinding activityRegisterBinding ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        activityRegisterBinding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(activityRegisterBinding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();


        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user!=null){
            Intent intent = new Intent(RegisterActivity.this , DashBoardActivity.class);
            startActivity(intent);
            finish();
        }
        else{

            activityRegisterBinding.btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    registerNewUser();
                }
            });

            activityRegisterBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(RegisterActivity.this , LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });


        }



    }

    private void registerNewUser(){

        activityRegisterBinding.progressbar.setVisibility(View.VISIBLE);

        String email , password ;
        email = activityRegisterBinding.txtEmail.getText().toString();
        password = activityRegisterBinding.txtPasswd.getText().toString();

        if(TextUtils.isEmpty(email)){

            Toast.makeText(this, "Email Can't Be Empty !", Toast.LENGTH_SHORT).show();
            return;

        }
        if(TextUtils.isEmpty(password)){

            Toast.makeText(this, "Password Can't Be Empty !", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email , password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "Registration Successful !", Toast.LENGTH_SHORT).show();
                            activityRegisterBinding.progressbar.setVisibility(View.GONE);

                            Intent intent = new Intent(RegisterActivity.this , LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Toast.makeText(RegisterActivity.this, "Registration Failed Try Again Later !", Toast.LENGTH_SHORT).show();

                            Log.d("Register Error :", "onComplete: "+task.getException());
                            activityRegisterBinding.progressbar.setVisibility(View.GONE);
                        }

                    }


                }) ;


    }
}