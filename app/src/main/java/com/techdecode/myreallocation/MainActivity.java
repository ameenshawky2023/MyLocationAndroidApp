package com.techdecode.myreallocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements IBaseGpsListener{
    private static final int PERMISSION_LOCATION=1000;
    TextView tv_location;
    Button b_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_location=findViewById(R.id.tv_location);
        b_location=findViewById(R.id.b_location);
        b_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check for location permission
                if(Build.VERSION.SDK_INT>=
                        Build.VERSION_CODES.M
                        &&checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_LOCATION );
                }else{
                    showLocation();
                }

            }
        });

    }

    @Override
    public void  onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showLocation();
            } else {
                Toast.makeText(this, "permission noe generated!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void showLocation(){
        LocationManager locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //check if gbs enabled
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            //start loading
            tv_location.setText("loading location....");
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,0,this);

        }else{
            //enable gps
            Toast.makeText(this,"Enable Gps!",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));

        }

    }
    //show location as string
    private String herelocation(Location location){
        return "lat:"+location.getAltitude()+"/nLon:"+location.getAltitude();
    }

    @Override
    public void onLocationChanged(Location location) {
        //update location
        tv_location.setText(herelocation(location ));

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onGpsStatusChanged(int event) {

    }
}