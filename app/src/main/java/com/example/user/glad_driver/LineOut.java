package com.example.user.glad_driver;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class LineOut extends BaseActivity{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_out);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

    }
    public void lineIn (View v){
        Intent intent = new Intent(this, LineIn.class);
        startActivity(intent);
    }


}
