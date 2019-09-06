package com.example.ammoniadetection;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity{


    ImageView imageView;
    EditText limit;
    TextView textView;
    View view2;
    ProgressBar progressBar;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );

        imageView = findViewById ( R.id.imageView );
        limit = findViewById ( R.id.limit );
        textView = findViewById ( R.id.sensorValue );
//        view2 = findViewById ( R.id.view2 );
//        progressBar = findViewById ( R.id.progressBar );


        DatabaseReference reference = FirebaseDatabase.getInstance ().getReference ().child ( "Status" );
        reference.addValueEventListener ( new ValueEventListener ( ) {
            @Override
            public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {

                int status = Integer.parseInt ( dataSnapshot.getValue ().toString () );
                if(status == 0){

                    imageView.setImageResource ( R.drawable.issafe );

                }else{

                    imageView.setImageResource ( R.drawable.isunsafe );

                }


            }

            @Override
            public void onCancelled( @NonNull DatabaseError databaseError ) {

            }
        } );


        DatabaseReference reference3 = FirebaseDatabase.getInstance ().getReference ().child ( "SensorValue" );
        reference3.addValueEventListener ( new ValueEventListener ( ) {
            @Override
            public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {

                try {


                    textView.setText ( String.valueOf ( dataSnapshot.getValue ( ).toString ( ) ) );
                }catch (Exception e){

                    System.out.print ( e );

                }
            }

            @Override
            public void onCancelled( @NonNull DatabaseError databaseError ) {

            }
        } );




    }


    public void set( View view){

        progressBar.setVisibility ( View.VISIBLE);
        view2.setVisibility ( View.VISIBLE );
        DatabaseReference reference = FirebaseDatabase.getInstance ().getReference ().child ( "Limit" );
        int limit2 = Integer.parseInt (limit.getText ().toString ());
        reference.setValue (limit2).addOnCompleteListener ( new OnCompleteListener <Void> ( ) {
            @Override
            public void onComplete( @NonNull Task<Void> task ) {

                if(task.isSuccessful ()){

                    progressBar.setVisibility ( View.INVISIBLE);
                    view2.setVisibility ( View.INVISIBLE );
                    Toast.makeText ( MainActivity.this,"Limit Set",Toast.LENGTH_SHORT ).show ();

                }else{
                    progressBar.setVisibility ( View.INVISIBLE);
                    view2.setVisibility ( View.INVISIBLE );
                    Toast.makeText ( MainActivity.this,"Failed to Set Limit",Toast.LENGTH_SHORT ).show ();

                }
            }
        } );

    }


    public void showMap(View view){

        Intent intent = new Intent ( MainActivity.this,Main2Activity.class );
        startActivity ( intent );

    }
}
