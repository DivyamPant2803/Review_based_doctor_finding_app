package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {

    //private AnimationDrawable animationDrawable;
    private LinearLayout linearLayout;

    private static final int RC_SIGN_IN = 3;
    SignInButton googledignin;
    FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;
    EditText email,password;
    Button signup,signin;
    TextView forgotpass;

    @Override
    protected void onStart () {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            mAuth.addAuthStateListener(mAuthListener);
        }
        //finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        linearLayout = findViewById(R.id.linearLayout);
        //animationDrawable = (AnimationDrawable) linearLayout.getBackground();

        //animationDrawable.setEnterFadeDuration(2000);
        //animationDrawable.setExitFadeDuration(1000);

        forgotpass=(TextView) findViewById(R.id.forgotpass);
        SpannableString content = new SpannableString("Forgot Password?");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        forgotpass.setText(content);
        googledignin=(SignInButton)findViewById(R.id.googlebtn);
        signup=(Button)findViewById(R.id.signup);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        signin=(Button)findViewById(R.id.signin);
        mAuth=FirebaseAuth.getInstance();
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email1=email.getText().toString().trim();
                String password1=password.getText().toString().trim();
                if(TextUtils.isEmpty(Email1))
                {
                    Toast.makeText(LoginActivity.this, "Enter the Email", Toast.LENGTH_SHORT).show();
                }

                if(TextUtils.isEmpty(password1))
                {
                    Toast.makeText(LoginActivity.this, "Enter the Password", Toast.LENGTH_SHORT).show();
                }
                if(password.length()<8)
                {
                    Toast.makeText(LoginActivity.this,"Password is too Sort",Toast.LENGTH_SHORT).show();
                }
                mAuth.signInWithEmailAndPassword(Email1,password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            startActivity(new Intent(LoginActivity.this,Secondone.class));
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, "Invalid Username/Password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,Signup.class));
            }
        });
        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,resetLink.class));
            }
        });
        googledignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null)
                {
                    startActivity(new Intent(LoginActivity.this,Secondone.class));
                }
            }
        };

    }
    private void signIn() {

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(LoginActivity.this,"username/password is wrong ",Toast.LENGTH_LONG).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            //updateUI(null);
                        }
                    }
                });


    }

    /*@Override
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
    }*/
}
