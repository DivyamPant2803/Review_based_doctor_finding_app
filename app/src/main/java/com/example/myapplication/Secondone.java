package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.internal.InternalTokenResult;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;


public class Secondone extends AppCompatActivity {

    private static final int CHOOSE_IMAGE =101 ;
    private String ageString,bloodString;
    Button signout,save,proceed;
    FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth.AuthStateListener mauthListener;
    ImageView imageView;
    EditText displayname,age,blood;
    Uri uriprofileimage;
    String profileurl;
    GoogleApiClient googleApiClient;
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mauthListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondone);

        signout=(Button)findViewById(R.id.b1);
        proceed = findViewById(R.id.proceed);
        displayname=(EditText) findViewById(R.id.displayname);
        age = findViewById(R.id.age);
        blood = findViewById(R.id.blood);
        imageView=(ImageView)findViewById(R.id.profile);
        save=(Button)findViewById(R.id.save);
        mAuth=FirebaseAuth.getInstance();
        mauthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null)
                {
                    startActivity(new Intent(Secondone.this,MainActivity.class));
                }
            }
        };

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();
        googleApiClient.connect();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageChooser();
            }
        });
        loaduserinfo();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveinfo();
            }

        });
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //signout();
                Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        signout();
                        //Intent intent = new Intent(Secondone.this,LoginActivity.class);
                        //startActivity(intent);
                    }
                });
            }
        });
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Secondone.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void saveinfo(){
        String display=displayname.getText().toString();

        if(display.isEmpty())
        {
            displayname.setError("Name Required");
            displayname.requestFocus();
            return;
        }
        FirebaseUser user=mAuth.getCurrentUser();
        if(user!=null&&profileurl!=null){
            UserProfileChangeRequest profile=new UserProfileChangeRequest.Builder().setDisplayName(display).setPhotoUri(Uri.parse(profileurl)).build();
            user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(Secondone.this,"Profile Updated",Toast.LENGTH_LONG).show();
                }
            });
        }
        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("age",age.getText().toString());
        editor.putString("blood",blood.getText().toString());
        editor.apply();
        Toast.makeText(Secondone.this,"Profile Updated",Toast.LENGTH_LONG).show();

    }

    private void loaduserinfo()
    {
        FirebaseUser user =mAuth.getCurrentUser();

        if(user!=null) {
            if(user.getPhotoUrl()!=null){
                Glide.with(Secondone.this).load(user.getPhotoUrl().toString()).into(imageView);
            }
            if(user.getDisplayName()!=null){
                displayname.setText(user.getDisplayName());

            }
            SharedPreferences sharedPreferences = getSharedPreferences("userinfo",Context.MODE_PRIVATE);
            ageString = sharedPreferences.getString("age","");
            bloodString = sharedPreferences.getString("blood","");
            age.setText(ageString);
            blood.setText(bloodString);
        }
    }
    private void showImageChooser(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Profile Image"),CHOOSE_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CHOOSE_IMAGE&&resultCode==RESULT_OK&& data!=null){
            uriprofileimage=data.getData();
            try {
                Bitmap bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),uriprofileimage);
                imageView.setImageBitmap(bitmap);
                uploadImage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    private void uploadImage() {
        final StorageReference profileImageRef = FirebaseStorage.getInstance().getReference("profilepics/" + System.currentTimeMillis() + ".jpg");

        if (uriprofileimage != null) {

            profileImageRef.putFile(uriprofileimage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // profileImageUrl taskSnapshot.getDownloadUrl().toString(); //this is depreciated

                            //this is the new way to do it
                            profileImageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    profileurl=task.getResult().toString();
                                    Log.i("URL",profileurl);
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Secondone.this, "aaa "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void signout()
    {

        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(Secondone.this,MainActivity.class);
                        intent.putExtra("finish",true);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        mAuth.signOut();
                        finish();


                    }
                });


    }


}
