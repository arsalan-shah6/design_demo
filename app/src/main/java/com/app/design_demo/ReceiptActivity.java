package com.app.design_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class ReceiptActivity extends AppCompatActivity {
    TextView student,semester,fee,slipno,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_receipt );

        student=findViewById( R.id.student );
        semester=findViewById( R.id.semester );
        fee=findViewById( R.id.fee );
        slipno=findViewById( R.id.fee_no );
        email=findViewById( R.id.email );



        Intent intent=getIntent();
        String Semester=intent.getStringExtra( "Semester" );
        String studentname=intent.getStringExtra( "StudentName" );
        String Fee=intent.getStringExtra( "Fee");
        String Slipno=intent.getStringExtra( "SlipNo" );
        String Email=intent.getStringExtra( "EmailAddress" );

        student.setText( studentname );
        semester.setText( Semester );
        fee.setText( Fee );
        slipno.setText( Slipno );
        email.setText( Email );


    }
}