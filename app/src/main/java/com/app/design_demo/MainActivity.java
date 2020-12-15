package com.app.design_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

       new Handler().postDelayed( new Runnable() {
           @Override
           public void run() {
               startActivity( new Intent(MainActivity.this,LoginActivity2.class) );

           }
       },3000 );


    }


    }
