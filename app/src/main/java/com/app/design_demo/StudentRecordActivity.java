package com.app.design_demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.design_demo.Adapter.CustomAdapter;
import com.app.design_demo.Model.Model;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StudentRecordActivity extends AppCompatActivity {

    CustomAdapter adapter;
    List<Model> list;
    TextView student,semester,fee,slipno,email;
    Button record;
    ProgressDialog progressDialog;

    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_student_record );
        student=findViewById( R.id.student );
        semester=findViewById( R.id.semester );
        fee=findViewById( R.id.fee );
        slipno=findViewById( R.id.fee_no );
        email=findViewById( R.id.email );
        record=findViewById( R.id.record );
        progressDialog=new ProgressDialog( this );
        progressDialog.setMessage( "please Wait" );

        database = FirebaseDatabase.getInstance();
        reference = database.getReference( "Class" );
        record.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                reference.addValueEventListener( new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Model model = new Model();
                        String name=snapshot.child( "Student Name" ).getValue().toString();
                        String Semester=snapshot.child( "Semester" ).getValue().toString();
                        String Fee=snapshot.child(  "Fee" ).getValue().toString();
                        String Slipno=snapshot.child( "Slip No" ).getValue().toString();
                        String Email=snapshot.child( "Email Address" ).getValue().toString();
                        student.setText( name );
                        semester.setText( Semester );
                        fee.setText( Fee );
                        slipno.setText( Slipno );
                        email.setText( Email );
                        progressDialog.dismiss();


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        progressDialog.dismiss();
                        Toast.makeText( StudentRecordActivity.this, ""+error, Toast.LENGTH_SHORT ).show();


                    }
                } );
            }
        } );


    }
}