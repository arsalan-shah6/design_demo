package com.app.design_demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
 DrawerLayout drawerLayout;
 ActionBarDrawerToggle actionBarDrawerToggle;
 Toolbar toolbar;
 NavigationView navigationView;
 FragmentManager fragmentManager;
 FragmentTransaction fragmentTransaction;
 FirebaseAuth auth;
 FirebaseDatabase database;
 DatabaseReference reference;
 ProgressDialog progressDialog;

 EditText student,semester,fee,slipno,email;
 Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_home );
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        reference=database.getReference("Class");
        progressDialog=new ProgressDialog( this );
        progressDialog.setMessage( "Please Wait" );


        drawerLayout=findViewById( R.id.drawerLayout );
        toolbar=findViewById( R.id.toolbar );
        navigationView=findViewById( R.id.nevigantion_view );
        navigationView.setNavigationItemSelectedListener( this );
        
        setSupportActionBar( toolbar );
        actionBarDrawerToggle=new ActionBarDrawerToggle( this,drawerLayout,toolbar,R.string.open,R.string.close );
        drawerLayout.addDrawerListener( actionBarDrawerToggle );
        actionBarDrawerToggle.setDrawerIndicatorEnabled( true );
        actionBarDrawerToggle.syncState();

        student=findViewById( R.id.student );
        semester=findViewById( R.id.semester );
        fee=findViewById( R.id.fee );
        slipno=findViewById( R.id.fee_no );
        email=findViewById( R.id.email );
        submit=findViewById( R.id.submit );

    submit.setOnClickListener( new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String StudentName=student.getText().toString().trim();
            String Semester=semester.getText().toString().trim();
            String Fee=fee.getText().toString().trim();
            String SlipNo=slipno.getText().toString().trim();
            String Email=email.getText().toString().trim();
            if (StudentName.isEmpty()){
                student.setError( "Enter Student Name" );
            } else if (Semester.isEmpty()){
                semester.setError( "Enter Your Semester" );
            }else if (Fee.isEmpty()){
                fee.setError( "Enter Your Amount" );
            }else if (SlipNo.isEmpty()){
                slipno.setError( "Enter Slip No" );
            }else if (Email.isEmpty()){
                email.setError( "Enter Email Address" );
            }else {
                progressDialog.show();
                Submit(StudentName,Semester,Fee,SlipNo,Email);

            }

        }
    } );


    }

    private void Submit(String studentName, String semester, String fee, String slipNo, String email) {

        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put( "Student Name", studentName);
        hashMap.put( "Semester",semester );
        hashMap.put( "Fee",fee );
        hashMap.put( "Slip No",slipNo );
        hashMap.put( "Email Address",email );
        progressDialog.show();
        reference.setValue( hashMap ).addOnCompleteListener( new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){
                    progressDialog.dismiss();
                    Intent intent=new Intent(HomeActivity.this,ReceiptActivity.class);
                    intent.putExtra(  "Semester",semester );
                    intent.putExtra( "StudentName",studentName );
                    intent.putExtra( "Fee",fee);
                    intent.putExtra( "SlipNo",slipNo );
                    intent.putExtra(  "EmailAddress",email );
                    startActivity( intent );

                    Toast.makeText( HomeActivity.this, "Fee Submitted", Toast.LENGTH_SHORT ).show();
                }


            }
        } ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();





            }
        } );


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.Dashboard){
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.add( R.id.container_fragment ,new fragment());
            fragmentTransaction.commit();
            drawerLayout.closeDrawer( GravityCompat.START );

        }
        if (item.getItemId()==R.id.meal_plan){
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace( R.id.container_fragment ,new FragmentSecond());
            fragmentTransaction.commit();
            drawerLayout.closeDrawer( GravityCompat.START );

        }
        if (item.getItemId()==R.id.logout){
             auth.signOut();
             startActivity( new Intent(HomeActivity.this,LoginActivity2.class) );
            drawerLayout.closeDrawer( GravityCompat.START );
        }

        return true;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}