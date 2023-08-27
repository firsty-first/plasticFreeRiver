package com.example.plasticfreeriver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;

import com.example.plasticfreeriver.databinding.ActivityMainBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
ActivityMainBinding binding;
public String lattitude;
public String longitude;
    public String lati;
    public String longi;
    FusedLocationProviderClient mFusedLocationClient;


    int PERMISSION_ID = 44;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFrag(new home1Fragment());//by default home frag
        binding.bottomNavView.setOnItemSelectedListener(item -> {
            if(item.getItemId()==R.id.home)

            {
                FragmentManager fragmentManager=getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
//                Bundle bundle=new Bundle();
//                bundle.putString("location",lati+","+longi);
                fragmentTransaction.replace(R.id.framelayout,new home1Fragment());
                fragmentTransaction.commit();
            }
                else if(item.getItemId()==R.id.feed)
                replaceFrag(new feedFragment());
                    else
                        replaceFrag(new insightFragment());

//
//
//            switch (item.getItemId()) {
//                case R.id.home:
//                    replaceFrag(new homeFragment());
//                    break;
//                case R.id.feed:
//                    replaceFrag(new feedFragment());
//                    break;
//                case R.id.insight:
//                    replaceFrag(new insightFragment());
//                    break;
//
//            }

            return  true;
        });


        // initializing
        // FusedLocationProviderClient
        // object

//            latitudeTextView = findViewById(R.id.latTextView);
//            longitTextView = findViewById(R.id.lonTextView);


        }
      @Override
      protected void onStart(){
        super.onStart();
          mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());

          // method to get the location
          getLastLocation();
      }


        @SuppressLint("MissingPermission")
        private void getLastLocation() {
            // check if permissions are given
            if (checkPermissions()) {

                // check if location is enabled
                if (isLocationEnabled()) {

                    // getting last
                    // location from
                    // FusedLocationClient
                    // object
                    mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            Location location = task.getResult();
                            if (location == null) {
                                requestNewLocationData();
                            } else {
//                                SharedPreferences pref=getSharedPreferences("userlocation",MODE_PRIVATE);
//                                 lattitude=pref.getString(lattitude,"");
                               Toast.makeText(MainActivity.this, Double.toString(location.getLatitude()), Toast.LENGTH_SHORT).show();
                                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("key",Double.toString(location.getLatitude())+","+Double.toString(location.getLongitude()));
                                editor.apply();
//
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            }
            else {
                // if permissions aren't available,
                // request for permissions
                requestPermissions();
            }
        }

        @SuppressLint("MissingPermission")
        private void requestNewLocationData() {

            // Initializing LocationRequest
            // object with appropriate methods
            LocationRequest mLocationRequest = new LocationRequest();
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            mLocationRequest.setInterval(5);
            mLocationRequest.setFastestInterval(0);
            mLocationRequest.setNumUpdates(1);
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
        }
        private LocationCallback mLocationCallback = new LocationCallback() {

            @Override
            public void onLocationResult(LocationResult locationResult) {
                Location mLastLocation = locationResult.getLastLocation();
lati= Double.toString(mLastLocation.getLatitude());
longi=Double.toString(mLastLocation.getLongitude());
                Bundle bundle=new Bundle();
                bundle.putString("location",lati+","+longi);
//                latitudeTextView.setText("Latitude: " + mLastLocation.getLatitude() + "");
//                longitTextView.setText("Longitude: " + mLastLocation.getLongitude() + "");
            }
               };
        // method to check for permissions
        private boolean checkPermissions() {
            return ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

            // If we want background location
            // on Android 10.0 and higher,
            // use:
            // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
        }

        // method to request for permissions
        private void requestPermissions() {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
        }
        // method to check
        // if location is enabled
        private boolean isLocationEnabled() {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }

        // If everything is alright then
        @Override
        public void
        onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            if (requestCode == PERMISSION_ID) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLastLocation();
                }
            }
        }

        @Override
        public void onResume() {
            super.onResume();
            if (checkPermissions()) {
                getLastLocation();
            }
        }

    private  void replaceFrag(Fragment fragment)
    {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout,fragment);
        fragmentTransaction.commit();
    }


}