package com.example.myapplication19;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Timer;
import java.util.TimerTask;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private LocationManager locationManager;
    private GoogleMap mMap;
    private Location mLocation;
    private Handler handler1;               // ハンドラー
    private Timer timer1;                   // タイマー
    private int count1;                     // カウント用
    private Location mLocation1,mLocation2,mLocation3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 1);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,1,this);


        //タイマーを新規生成
        timer1 = new Timer();
        //ハンドラーを新規生成
        handler1 = new Handler();

        //カウンターを初期化
        count1 = 0;

        //タイマーに直接スケジュール(1秒後に1秒間隔の処理を開始)を追加して実行
        timer1.schedule(new TimerTask() {
            @Override
            public void run() {

                //直接だとエラーになるのでハンドラーを経由して画面表示を変更する
                handler1.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mLocation != null) {
                            mLocation1 = mLocation2;
                            mLocation2 = mLocation3;
                            mLocation3 = mLocation;

                            if (mLocation1 != null && mLocation2 != null && mLocation3 != null) {
                                //mMap.addMarker(new MarkerOptions().position(new LatLng(mLocation.getLatitude(), mLocation.getLongitude())));
                            }
                        }
                    }
                });
                //カウントアップ
                count1 += 1;
            }
        }, 1000, 5000);


   

    }


    @Override
    public void onStatusChanged(String provider, int status,Bundle extras){

    }

    @Override
    public void onLocationChanged(Location location){
      //  mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(),location.getLongitude())));
        //Toast.makeText(MapsActivity.this,"場所が変わった",Toast.LENGTH_SHORT).show();
        mLocation = location;
    }

    @Override
    public void onProviderEnabled(String provider){

    }

    @Override
    public void onProviderDisabled(String provider){

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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng aomori = new LatLng(40.783084,140.781492);
     //   mMap.addMarker(new MarkerOptions().position(aomori).title("Marker in 青森大学"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(aomori,17));
    }
}
