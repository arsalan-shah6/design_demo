package com.app.design_demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {
  EditText Name,Usewname,Email,Password,Repass;
  Button Signup;
    String username;
    String password;
  FirebaseAuth auth;
  ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_sign_up );
        Name=findViewById( R.id.name);
        Usewname=findViewById( R.id.username );
        Email = findViewById( R.id.emailAddress );
        Password=findViewById( R.id.password );
        Repass=findViewById( R.id.conformPass );
        Signup=findViewById( R.id.signup_btn );
        auth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog( this );





        Signup.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=Name.getText().toString().trim();
                username=Usewname.getText().toString().trim();
                String email=Email.getText().toString().trim();
                 password=Password.getText().toString().trim();
                String repass=Repass.getText().toString().trim();

                if (name.isEmpty()){
                    Name.setError( "Enter name" );
                }
                else if (username.isEmpty()){
                    Usewname.setError( "Enter username" );
                }
                else if (email.isEmpty()){
                    Email.setError( "Enter email address" );
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher( email ).matches()){
                    Email.setError( "invalid Email" );
                }
                else if (password.isEmpty()){
                    Password.setError( "Enter password" );
                }
                else if (repass.isEmpty()){
                    Repass.setError( "Enter Repassword" );
                }
                else if (!password.equals( repass )){
                    Repass.setError( "Did not match password" );
                }
                else if (password.length()<6){
                    Password.setError( "Password greater then 6 charecters" );
                }
                else {
                    Apicall(email,password);
                }

            }
        } );


    }

    private void Apicall(String email, String password) {
        progressDialog.setMessage( "Please Wait" );
        progressDialog.show();
        auth.createUserWithEmailAndPassword( email,password ).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    progressDialog.dismiss();
                    FirebaseUser user=auth.getCurrentUser();
                    String Uid=user.getUid();
                    String Email=user.getEmail();


                    HashMap<Object,String> hashMap=new HashMap<>();
                    hashMap.put( "uid",Uid );
                    hashMap.put( "email",Email );
                    hashMap.put( "username", username );
                    hashMap.put( "password",password );

                    FirebaseDatabase database=FirebaseDatabase.getInstance();
                    DatabaseReference reference=database.getReference("user");

                    reference.child( Uid ).setValue( hashMap );
                    startActivity( new Intent(SignUpActivity.this,HomeActivity.class) );
                }else {
                    Toast.makeText( SignUpActivity.this, "Failed", Toast.LENGTH_SHORT ).show();
                    progressDialog.dismiss();
                }

            }
        } ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText( SignUpActivity.this, ""+e, Toast.LENGTH_SHORT ).show();
                progressDialog.dismiss();
            }
        } );
    }


}