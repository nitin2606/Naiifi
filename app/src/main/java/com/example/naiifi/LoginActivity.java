package com.example.naiifi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.naiifi.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding activityLoginBinding ;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_login);
        activityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(activityLoginBinding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

        activityLoginBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUserAccount();
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("Naiifi",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("value","Welcome To Naiifi ");
        editor.apply();

    }
    private void loginUserAccount(){

        activityLoginBinding.progressBar.setVisibility(View.VISIBLE);

        String email , password ;

        email = activityLoginBinding.txtEmail.getText().toString();
        password = activityLoginBinding.txtPassword.getText().toString() ;

        if(TextUtils.isEmpty(email)){

            Toast.makeText(this, "Email Can't Be Empty !", Toast.LENGTH_SHORT).show();
            return ;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Enter Password !", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(email , password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Login Successful !", Toast.LENGTH_SHORT).show();

                            activityLoginBinding.progressBar.setVisibility(View.GONE);

                            Intent intent = new Intent(LoginActivity.this , DashBoardActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        else{
                            Toast.makeText(LoginActivity.this, "Login Failed !", Toast.LENGTH_SHORT).show();
                            activityLoginBinding.progressBar.setVisibility(View.GONE);
                        }
                    }
                });

    }
}