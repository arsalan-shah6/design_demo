package com.app.design_demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity2 extends AppCompatActivity {
      ImageView backBtn;
      EditText EmailTText,PasswordTextview;
      TextView ForgotPassword,facebookBtn,GoogleBtn,Dont_have_account;
      Button LoginBtn;
      FirebaseAuth auth;
      ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login2 );
        backBtn=findViewById( R.id.backBtn );
        EmailTText=findViewById( R.id.email );
        PasswordTextview=findViewById( R.id.password );
        LoginBtn=findViewById( R.id.sign_btn );
        ForgotPassword=findViewById( R.id.ForgotPassword );
        facebookBtn=findViewById( R.id.facebook );
        GoogleBtn=findViewById( R.id.google );
        Dont_have_account=findViewById( R.id.signup );
        auth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog( this );


        Dont_have_account.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

        startActivity( new Intent(LoginActivity2.this,SignUpActivity.class) );
            }
        } );

        LoginBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email= EmailTText.getText().toString().trim();
                String password=PasswordTextview.getText().toString().trim();
                if (email.isEmpty()){
                    EmailTText.setError( "Please enter email" );
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher( email ).matches()){
                    EmailTText.setError( "Invalid Enail Address" );
                }
                else if (password.isEmpty()){
                    PasswordTextview.setError( "Please enter password" );
                }
                else {
                    ApiCall(email,password);
                }
               startActivity( new Intent(LoginActivity2.this,HomeActivity.class) );
            }
        } );


    }

    private void ApiCall(String email, String password) {
        progressDialog.setMessage( "Please Wait" );
        progressDialog.show();
        auth.signInWithEmailAndPassword( email,password ).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    progressDialog.dismiss();
                    startActivity( new Intent(LoginActivity2.this,HomeActivity.class) );
                }

            }
        } ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText( LoginActivity2.this, ""+e, Toast.LENGTH_SHORT ).show();

            }
        } );

    }
}