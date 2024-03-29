package com.example.naiifi;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.example.naiifi.databinding.ActivityDashBoardBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import Adapters.SalonAdapter;
import Codes.Distance;
import Fragments.AppointmentFragment;
import Fragments.HomeFragment;
import Fragments.MapFragment;
import Fragments.ProfileFragment;
import Fragments.SettingsFragment;
import Models.SalonData;



public class DashBoardActivity extends AppCompatActivity {


    private FirebaseAuth firebaseAuth ;
    private DatabaseReference databaseReference ;
    private FirebaseFirestore firebaseFirestore;

    private static final String TAG = "DataTag" ;
    HashMap<String , String> hashMap ;

    FusedLocationProviderClient mFusedLocationClient;
    int PERMISSION_ID = 44;

    ActivityDashBoardBinding activityDashBoardBinding ;
    Distance distance = new Distance();

    private SalonAdapter salonAdapter;
    private ArrayList<SalonData> salonDataArrayList = new ArrayList<>();

    private Fragment navFragment;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        activityDashBoardBinding = ActivityDashBoardBinding.inflate(getLayoutInflater());
        View rootView = activityDashBoardBinding.getRoot();
        setContentView(rootView);

        getWindow().setStatusBarColor(getResources().getColor(R.color.dashboard_background));
        getWindow().setNavigationBarColor(getResources().getColor(R.color.color_blue));

        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, homeFragment, "");
        fragmentTransaction.commit();



        activityDashBoardBinding.bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.home:
                        navFragment = new HomeFragment();
                        break;

                    case R.id.map:
                        navFragment = new MapFragment();
                        break;

                    case R.id.appointment:
                        navFragment = new AppointmentFragment();
                        break;

                    case R.id.profile:
                        navFragment = new ProfileFragment();
                        break;

                    case R.id.setting:
                        navFragment = new SettingsFragment();
                        break;



                }
                if (navFragment != null) {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, navFragment, "");
                    fragmentTransaction.commit();
                }
                return true;

            }
        });

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();

        //Log.d("MapLocation", "onCreate: "+accessSharedPreference("userLocation").get("latitude"));




        Log.d("cityTag", "onCreate: "+fetchCity());




    }

    private HashMap<String, Double> accessSharedPreference(String preferenceName){
        SharedPreferences sharedPreferences = getSharedPreferences(preferenceName, MODE_PRIVATE);

        float latitude = sharedPreferences.getFloat("latitude",0);
        float longitude = sharedPreferences.getFloat("longitude",0);

        HashMap<String, Double> locationMap = new HashMap<>();
        locationMap.put("latitude", (double)latitude);
        locationMap.put("longitude", (double)longitude);

        return locationMap ;

    }

    private void editSharedPreference(String appendMode, double Latitude, double Longitude){

        SharedPreferences sharedPreferences = getSharedPreferences("userLocation",MODE_PRIVATE);
        SharedPreferences.Editor myEditor = sharedPreferences.edit();
        myEditor.putFloat("latitude", (float) Latitude);
        myEditor.putFloat("longitude", (float) Longitude);
        myEditor.commit();

    }

    private String fetchCity(){

        String cityName = "";
        Double latitude = accessSharedPreference("userLocation").get("latitude");
        Double longitude = accessSharedPreference("userLocation").get("longitude");
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude ,1);
            if(addresses.size() !=0){
                cityName = addresses.get(0).getLocality();
            }

            //Log.d("cityTag", "onCreate: "+cityName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cityName;
    }


    private void fetchSalons(){

    }


    @SuppressLint("MissingPermission")
    private void getLastLocation() {

        if (checkPermissions()) {


            if (isLocationEnabled()) {

                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {
                            editSharedPreference("Append", location.getLatitude(), location.getLongitude());
                            Log.d(TAG, "onComplete: latitude : "+location.getLatitude());
                            Log.d(TAG, "onComplete: longitude : "+location.getLongitude());

                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {

            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

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
            Log.d(TAG, "onLocationResult: Latitude : "+ mLastLocation.getLatitude());
            Log.d(TAG, "onLocationResult: Longitude :"+mLastLocation.getLongitude());

        }
    };


    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }


    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }


    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


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


}