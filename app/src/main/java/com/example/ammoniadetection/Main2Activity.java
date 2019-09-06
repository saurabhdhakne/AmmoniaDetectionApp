package com.example.ammoniadetection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main2 );
    }



   static class DataModel {


      static int setLocation;
      static int showStatus2;

    }


    public void setLocation( View view ){

        DataModel.setLocation = 1;
        DataModel.showStatus2 = 0;
        Intent intent = new Intent ( Main2Activity.this,MapsActivity.class );
        startActivity ( intent );

    }



    public void showStatus( View view){

        DataModel.setLocation = 0;
        DataModel.showStatus2 = 1;
        Intent intent = new Intent ( Main2Activity.this,MapsActivity.class );
        startActivity ( intent );
    }
}