package com.example.myapplication;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class resetLink extends AppCompatActivity {

    private LinearLayout linearLayout2;
    private AnimationDrawable animationDrawable;

    Button sendlink;
    EditText passreset;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_link);

        linearLayout2 = findViewById(R.id.linearLayout2);
        animationDrawable = (AnimationDrawable)linearLayout2.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(1000);

        firebaseAuth=FirebaseAuth.getInstance();
        passreset=(EditText)findViewById(R.id.passreset);
        sendlink=(Button)findViewById(R.id.sendlink);
        sendlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.sendPasswordResetEmail(passreset.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(resetLink.this, "Password sent to your email", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(resetLink.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();

                        }
                    }
                });
            }
        });

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(animationDrawable !=null && !animationDrawable.isRunning()){
            animationDrawable.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(animationDrawable !=null && animationDrawable.isRunning()){
            animationDrawable.stop();
        }
    }
}
