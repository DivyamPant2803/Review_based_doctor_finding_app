package com.example.myapplication;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import static android.Manifest.permission.SEND_SMS;

public class Signup extends AppCompatActivity {

    FirebaseAuth mAuth,mAuth2;
    //FirebaseAuth.AuthStateListener mauthListener;
    Button signup;
    DatabaseReference databaseReference;
    private CountryCodePicker countryCodePicker;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =1 ;

    private int flag=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        final EditText txtMail = (EditText) findViewById(R.id.emailaddress);
        final EditText txtconfirmpassword = (EditText) findViewById(R.id.confirmpassword);
        final EditText txtPassword = (EditText) findViewById(R.id.Pass);
        signup=(Button)findViewById(R.id.signup1);
        //verify = findViewById(R.id.verify);
        final EditText Name = (EditText) findViewById(R.id.Name);
        final EditText contact=(EditText)findViewById(R.id.contact);
        mAuth=FirebaseAuth.getInstance();
        //mAuth2 = FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference("Users");
        countryCodePicker = findViewById(R.id.ccp);
        countryCodePicker.registerCarrierNumberEditText(contact);


        if(Build.VERSION.SDK_INT >=23){
            if(checkSelfPermission(Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(Signup.this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }




        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String Mail=txtMail.getText().toString().trim();
                final String Password=txtPassword.getText().toString().trim();
                final String ConfirmPass=txtconfirmpassword.getText().toString().trim();
                final String FirstName=Name.getText().toString().trim();
                final String Contact=contact.getText().toString().trim();



                if(TextUtils.isEmpty(FirstName))
                {
                    Toast.makeText(Signup.this, "Enter the Name", Toast.LENGTH_SHORT).show();
                    flag++;
                    return;
                }
                if(TextUtils.isEmpty(Contact))
                {
                    Toast.makeText(Signup.this, "Enter the Contact Number", Toast.LENGTH_SHORT).show();
                    flag++;
                    return;
                }
                if(TextUtils.isEmpty(Mail))
                {
                    Toast.makeText(Signup.this,"Enter the Email Address",Toast.LENGTH_SHORT).show();
                    flag++;
                    return;
                }
                if(TextUtils.isEmpty(Password))
                {
                    Toast.makeText(Signup.this,"Enter the Password",Toast.LENGTH_SHORT).show();
                    flag++;
                    return;
                }
                if(TextUtils.isEmpty(ConfirmPass))
                {
                    Toast.makeText(Signup.this,"Confirm Your Password",Toast.LENGTH_SHORT).show();
                    flag++;
                    return;
                }
                if(Password.length()<8)
                {
                    Toast.makeText(Signup.this, "Password must contain atleast 8 characters", Toast.LENGTH_SHORT).show();
                    flag++;
                }
                if(Password.equals(ConfirmPass)&&Password.length() >= 8)
                {
                    mAuth.createUserWithEmailAndPassword(Mail, Password)
                            .addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    //if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        // Users info=new Users(FirstName,LastName);
                                        //FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        flag=0;

                                        //Toast.makeText(Signup.this, "Sign up Successful",
                                           //     Toast.LENGTH_SHORT).show();

                                    //}
                                    /*else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(Signup.this, "Check your Password",
                                                Toast.LENGTH_SHORT).show();

                                    }*/

                                }
                            });
                }
                if(flag==0) {
                    if (TextUtils.isEmpty(contact.getText().toString())) {
                        Toast.makeText(Signup.this, "Enter Contact No ....", Toast.LENGTH_SHORT).show();
                    } else if (contact.getText().toString().replace(" ", "").length() != 10) {
                        Toast.makeText(Signup.this, "Enter Correct No ...", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(Signup.this, VerificationActivity.class);
                        intent.putExtra("phone", countryCodePicker.getFullNumberWithPlus().replace(" ", ""));
                        startActivity(intent);
                    }
                }

            }
        });

    }


}
