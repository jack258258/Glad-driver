package com.example.user.glad_driver;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends BaseActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    private static final int REQUEST_LOCATION_PERMISSION = 2;
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        gotoScene(SCENE_NORMAL, FRAG_01);  //最初的Fragment為FRAG_01
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
            return;
        }else {
            setupMyLocation();

        }
        setupMyLocation();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        mGoogleApiClient.connect();
    }


    private void setupMyLocation() {
        //noinspection MissingPermission
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(
                new GoogleMap.OnMyLocationButtonClickListener(){

                    @Override
                    public boolean onMyLocationButtonClick() {
                        return false;
                    }
                }
        );
    }
    //使用完成授權選擇以後，Android 會呼叫這個方法
    //                  第一個參數: 請求授權代碼
    //                  第二個參數: 請求授權名稱
    //                   第三個參數: 使用者選擇授權的結果

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch(requestCode){
            case REQUEST_LOCATION_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //使用者允許權限

                    //noinspection MissingPermission
                    setupMyLocation();
                }else{
                    //使用者拒絕授權，停用MyLocation功能'
                    Toast.makeText(this, "沒有讀取位置授權", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }



    LocationRequest mLocationRequest;
    @Override
    public void onConnected(@Nullable Bundle bundle) {

        //noinspection MissingPermission
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        //確認是否定位，但是還無法確認後，重新定位
        //或是開啟定位後，關掉。無法再做一次確認
        if (mLastLocation == null){
            Toast.makeText(this, "偵測不到定位，請確認定位功能已開啟。", Toast.LENGTH_LONG).show();
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));    //開啟設定頁面
            return;
        }
        Log.i("RETURN","開啟定位功能");

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(10000);
        //noinspection MissingPermission
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        Log.i("連線","連線");
        //LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        //Log.i("取消連線","取消連線");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){

            @Override
            public void run() {
                //過兩秒後要做的事情
                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, MapsActivity.this);
                Log.d("tag","取消即時更新");

            }}, 5000);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        if(location ==null) {
            Toast.makeText(this, "cant get current location", Toast.LENGTH_LONG).show();
        }else{
            LatLng mLatLng =new LatLng(location.getLatitude(),location.getLongitude());
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(mLatLng, 20);
            mMap.moveCamera(update);
        }
    }



    //以下為fragment操作
    public static int SCENE_NORMAL = 001;
    public static int FRAG_01 = 101;
    public static int FRAG_02 = 102;

    int currentFragIndex = FRAG_01;
    public void gotoScene(int targetScene, int fragIndex)
    {
        if(targetScene == SCENE_NORMAL)
        {
            //設定不同動畫
            gotoFrag(fragIndex, false);
        }
    }

    public void gotoFrag(int fragIndex, boolean isAddToBackStack)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment frag = null;
        String tag = null;

        if(fragIndex == FRAG_01) //當 FRAG_01 時，傳入 ViewFragment1
        {
            tag = GetPassFrag.TAG;
            frag = fragmentManager.findFragmentByTag(tag);
            if (frag == null) {
                frag = new GetPassFrag();
            }
        }
        else if(fragIndex == FRAG_02) //當 FRAG_02 時，傳入 ViewFragment2
        {
            tag = GetLocFrag.TAG;
            frag = fragmentManager.findFragmentByTag(tag);
            if (frag == null) {
                frag = new GetLocFrag();
            }
        }

        currentFragIndex = fragIndex;
        transFragment(frag, tag, isAddToBackStack);
    }

    public void transFragment(Fragment frag, String tag, boolean isAddToBackStack)
    {
        if(frag == null)
        {return;}

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(tag != null)
        {
            fragmentTransaction.replace(R.id.fragment2, frag, tag); //指定 Fragment 要替代的介面
            if (isAddToBackStack)
            {
                fragmentTransaction.addToBackStack(tag);
            }
        }


        try
        {
            fragmentTransaction.commit();
        }
        catch (IllegalStateException e)
        {
            e.printStackTrace();
            try
            {
                fragmentTransaction.commitAllowingStateLoss();
            }
            catch (IllegalStateException ise)
            {
                e.printStackTrace();
            }
        }
    }


}
