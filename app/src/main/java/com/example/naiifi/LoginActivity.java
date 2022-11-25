package com.example.naiifi;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.naiifi.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;



public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding activityLoginBinding ;
    private FirebaseAuth firebaseAuth;

    private DatabaseReference databaseReference;
    private DatabaseReference databaseReference2;
    private String verificationId;
    private Dialog otpDialog , loginDialog ;
    private FirebaseUser user ;

    private HashMap<String , Integer> countMap = new HashMap<>() ;

    private static final String TAG  = "naiifiTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_login);
        activityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(activityLoginBinding.getRoot());



        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        firebaseAuth = FirebaseAuth.getInstance();

        activityLoginBinding.txtMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                if(s.length()<10 && s.length()>10){
                    activityLoginBinding.btnGetOtp.setVisibility(View.GONE);
                }


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.length()<10 && s.length()>10){
                    activityLoginBinding.btnGetOtp.setVisibility(View.GONE);
                }



            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()==10){
                    activityLoginBinding.txtPhoneProgress.setVisibility(View.VISIBLE);
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);


                    Handler handler  = new Handler();  // We use handlers to update UI from inside a thread .

                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            synchronized (this) {
                                try {
                                    readPhone(s.toString().trim());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }

                            handler.post(new Runnable() {
                                @Override
                                public void run() {


                                }
                            });

                        }
                    };
                    Thread thread = new Thread(runnable);
                    thread.start();


                    System.out.println("Thread State : "+thread.getState());
                }
                else if(s.length()>10){
                    activityLoginBinding.txtPhoneProgress.setVisibility(View.GONE);
                    activityLoginBinding.txtMobile.setError("Phone No Invalid !");
                }
                else if(s.length()<10){
                    activityLoginBinding.btnGetOtp.setVisibility(View.GONE);
                }

            }
        });

        activityLoginBinding.signUpHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this , RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });


        activityLoginBinding.btnGetOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber ="+91"+ activityLoginBinding.txtMobile.getText().toString().trim();

                getOtpOnclick(phoneNumber);
            }
        });

        activityLoginBinding.txtOtp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {





            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {




            }

            @Override
            public void afterTextChanged(Editable s) {

                if(s.length()==0 ||s.length()<6){
                    activityLoginBinding.txtOtp.setError("Otp Invalid");
                }
                else {
                    loginDialog = new Dialog(LoginActivity.this);
                    loginDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    loginDialog.getWindow().setBackgroundDrawableResource(R.drawable.transparent_background);
                    loginDialog.setContentView(R.layout.progress_dialog);
                    loginDialog.setCanceledOnTouchOutside(false);
                    loginDialog.show();
                    loginOnClick();
                }

            }
        });

    }


    private void readPhone(String phoneNo){



        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Salons");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int c = 0;
                for(DataSnapshot d : snapshot.getChildren()){
                    if(d.getKey().equals(phoneNo)){
                        c=c+1;
                    }
                }
                countMap.put("c", c);


                databaseReference2 = FirebaseDatabase.getInstance().getReference("userData");
                databaseReference2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int d = 0;

                        for(DataSnapshot d1 : snapshot.getChildren()){
                            if(d1.getKey().equals(phoneNo)){
                                d=d+1;
                            }
                        }
                        countMap.put("d", d);



                        if(countMap.get("c")>0){
                            activityLoginBinding.txtPhoneProgress.setVisibility(View.GONE);
                            activityLoginBinding.txtMobile.setError("Invalid phone number !");
                        }
                        else if(countMap.get("d")!=1){
                            activityLoginBinding.txtPhoneProgress.setVisibility(View.GONE);
                            activityLoginBinding.txtMobile.setError("Account does not exists , sign up to continue .");
                        }
                        else if(countMap.get("c")==0 && countMap.get("d")==1){
                            activityLoginBinding.txtPhoneProgress.setVisibility(View.GONE);
                            activityLoginBinding.btnGetOtp.setVisibility(View.VISIBLE);

                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                        Log.d(TAG, "onCancelled: Error In Searching For PhoneNo"+error);

                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        System.out.println("countMap out of function : "+countMap);


    }




    public void getOtpOnclick(String phoneNumber){


        sendVerificationCode(phoneNumber);
        otpDialog = new Dialog(LoginActivity.this);
        otpDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        otpDialog.setContentView(R.layout.progress_dialog);
        otpDialog.getWindow().setBackgroundDrawableResource(R.drawable.transparent_background);
        otpDialog.setCanceledOnTouchOutside(false);
        otpDialog.show();

    }

    private void sendVerificationCode(String phoneNumber) {

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(firebaseAuth).setPhoneNumber(phoneNumber).setTimeout(60L, TimeUnit.SECONDS).setActivity(this).setCallbacks(mCallBack)
                .build();

        PhoneAuthProvider.verifyPhoneNumber(options);


    }

    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


        @Override
        public void onCodeSent(@NonNull String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {

            otpDialog.dismiss();
            //otpsent.setText("OTP has been sent yo your mobile number");
            //otpsent.setVisibility(View.VISIBLE);
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;

            activityLoginBinding.btnGetOtp.setVisibility(View.INVISIBLE);
            activityLoginBinding.otpTimer.setVisibility(View.VISIBLE);
            activityLoginBinding.txtOtpLayout.setVisibility(View.VISIBLE);
            //auto_capture.setVisibility(View.VISIBLE);
            activityLoginBinding.autoDetectLayout.setVisibility(View.VISIBLE);

            new CountDownTimer(60000,1000){


                @Override
                public void onTick(long millisUntilFinished) {

                    activityLoginBinding.otpTimer.setText("" + millisUntilFinished/1000);

                }

                @Override
                public void onFinish() {

                    //activityRegisterBinding.autoDetectLayout.setVisibility(View.INVISIBLE);
                    activityLoginBinding.resendOtp.setVisibility(View.VISIBLE);
                    //otpsent.setVisibility(View.GONE);
                    activityLoginBinding.otpTimer.setVisibility(View.INVISIBLE);

                }
            }.start();
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            String code = phoneAuthCredential.getSmsCode();
            if (code != null){
                activityLoginBinding.autoDetectLayout.setVisibility(View.INVISIBLE);
                activityLoginBinding.txtOtp.setText(code);
                verifyCode(code);

            }

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {


            //getotp.setTextColor(Color.parseColor("00000FF"));
            otpDialog.dismiss();
            Log.d(TAG, "onVerificationFailed: "+e.getMessage());
            Toast.makeText(LoginActivity.this,"Otp Verification Failed",Toast.LENGTH_LONG).show();

        }
    };

    public void loginOnClick(){

        String otp1 = activityLoginBinding.txtOtp.getText().toString().trim();
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
       /* loginDialog = new Dialog(RegisterActivity.this);
        loginDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loginDialog.getWindow().setBackgroundDrawableResource(R.drawable.transparent_background);
        loginDialog.setContentView(R.layout.progress_dialog);
        loginDialog.setCanceledOnTouchOutside(false);
        loginDialog.show();*/
        verifyCode(otp1);



    }


    private void verifyCode(String code){

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId,code);
        FirebaseAuth.getInstance().getFirebaseAuthSettings().setAppVerificationDisabledForTesting(false);
        signInWithCredential(credential);


    }

    private void signInWithCredential(PhoneAuthCredential credential) {

        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){

                    Intent intent = new Intent(LoginActivity.this , DashBoardActivity.class);
                    startActivity(intent);
                    finish();


                }
                else{
                    Toast.makeText(LoginActivity.this, "Sign Up Failed !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}