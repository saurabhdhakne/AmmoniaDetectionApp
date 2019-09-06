package com.example.ammoniadetection;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;
    Marker marker;
    Location currentLocation;
    Location chipLocation;
    Button button;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_maps );
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = ( SupportMapFragment ) getSupportFragmentManager ( )
                .findFragmentById ( R.id.map );
        mapFragment.getMapAsync ( this );
        button = findViewById ( R.id.setLocation );




if(Main2Activity.DataModel.setLocation == 1) {

    locationManager = ( LocationManager ) this.getSystemService ( LOCATION_SERVICE );
    locationListener = new LocationListener ( ) {
        @Override
        public void onLocationChanged( Location location ) {

            LatLng latLng = new LatLng ( location.getLatitude ( ) , location.getLongitude ( ) );
            if (mMap != null) {
                marker.setPosition ( latLng );
                currentLocation = location;
            }

        }

        @Override
        public void onStatusChanged( String provider , int status , Bundle extras ) {

        }

        @Override
        public void onProviderEnabled( String provider ) {

        }

        @Override
        public void onProviderDisabled( String provider ) {

        }
    };


    if (ContextCompat.checkSelfPermission ( this , Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {

        ActivityCompat.requestPermissions ( this , new String[]{Manifest.permission.ACCESS_FINE_LOCATION} , 1 );


    } else {

        locationManager.requestLocationUpdates ( LocationManager.GPS_PROVIDER , 0 , 0 , locationListener );

    }
}


if(Main2Activity.DataModel.showStatus2  == 1){

    button.setVisibility ( View.INVISIBLE );
    DatabaseReference reference3 = FirebaseDatabase.getInstance ().getReference ().child ( "Location" );
    reference3.addValueEventListener ( new ValueEventListener ( ) {
        @Override
        public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {

            try {
                HashMap <String, Double> map = ( HashMap ) dataSnapshot.getValue ( );
                chipLocation.setLatitude (map.get ( "Lat" ));
                chipLocation.setLongitude (map.get ( "Long" ) );
                LatLng latLng = new LatLng ( chipLocation.getLatitude ( ) , chipLocation.getLongitude ( ) );
                marker.setPosition ( latLng );
            }catch (Exception e){


                System.out.print ( e.getMessage () );
            }



        }

        @Override
        public void onCancelled( @NonNull DatabaseError databaseError ) {

        }
    } );



}

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady( GoogleMap googleMap ) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng ( -34 , 151 );
        mMap.moveCamera ( CameraUpdateFactory.newLatLng ( sydney ) );
        marker = ( Marker ) mMap.addMarker (new MarkerOptions ( ).position (sydney).title ("My Location"));

    }



    public void setLocation( View view) {

        if (currentLocation != null) {
            DatabaseReference reference3 = FirebaseDatabase.getInstance ( ).getReference ( ).child ( "Location" );
            HashMap <String, Double> map = new HashMap <> ( );
            map.put ( "Lat" , currentLocation.getLatitude ( ) );
            map.put ( "Long" , currentLocation.getLongitude ( ) );

            reference3.setValue ( map ).addOnCompleteListener ( new OnCompleteListener <Void> ( ) {
                @Override
                public void onComplete( @NonNull Task <Void> task ) {


                    if (task.isSuccessful ( )) {

                        Toast.makeText ( MapsActivity.this , "Location set" , Toast.LENGTH_SHORT ).show ( );

                    } else {

                        Toast.makeText ( MapsActivity.this , "Location set Failed" , Toast.LENGTH_SHORT ).show ( );

                    }

                }
            } );


        }else{

            Toast.makeText ( MapsActivity.this , "Location set Failed" , Toast.LENGTH_SHORT ).show ( );


        }
    }


    }