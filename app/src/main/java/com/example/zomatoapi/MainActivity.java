package com.example.zomatoapi;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements LocationListener {

    Location location;
    LocationManager locationManager;
    Button get;
    double latitude;
    double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        get = findViewById(R.id.get);

        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                if (!check())
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);

                if (check())
                    getCurrentLocation();

                searchRestaurant();
            }
        });
    }

    private boolean check() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

        @SuppressLint("MissingPermission")
    private void getCurrentLocation() {

//        Log.d("b", "before");


        locationManager.requestLocationUpdates(locationManager.NETWORK_PROVIDER,5000, 0, this);
//        Log.d("a","after");


    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

        if(location == null) {
            Log.d("null", "null");
            return;
        }

        latitude = location.getLatitude();
        longitude = location.getLongitude();
        Log.d("location: ","Latitude: "+latitude+" longitude "+longitude);

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    private void searchRestaurant()
    {
        Call<SearchZomato> result = Api.getService().searchRestaurantbyName("delhi");
        Log.d("SEA: ","sea");
        result.enqueue(new Callback<SearchZomato>() {
            @Override
            public void onResponse(Call<SearchZomato> call, Response<SearchZomato> response) {
                Log.d("Line1","line146");
                SearchZomato item = response.body();
                Log.d("done2", String.valueOf(call.request().url()));


//                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_LONG).show();
                if(response.body()!=null)
                {
                    List<Restaurant> list = item.getRestaurants();
                    Restaurant restaurant = list.get(0);
                    Log.d("Name: " , restaurant.getName() + " S "+ list.size());
                }
                else
                {
                    Log.d("NULL: " , "HJ");
                }
            }

            @Override
            public void onFailure(Call<SearchZomato> call, Throwable t) {

            }
        });
    }
}


