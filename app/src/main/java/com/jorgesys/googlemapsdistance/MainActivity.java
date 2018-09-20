package com.jorgesys.googlemapsdistance;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private static final String TAG = "MainActivity";
    private static final int ONE_KM = 1000; //1 km

    private static final long MIN_TIME_BETWEEN_UPDATES = 1000 * 60 * 1; // 1 minute.
    private static final long MIN_DISTANCE_FOR_UPDATES = (long) 1.5; // 1.5 meters
    private LocationManager mLocationManager;
    private ArrayList<Address> listOfGeoPositions;
    private TextView myTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myTextView = findViewById(R.id.myTextView);
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 225);
            return;
        }else{

            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BETWEEN_UPDATES,
                    MIN_DISTANCE_FOR_UPDATES, this);
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BETWEEN_UPDATES,
                    MIN_DISTANCE_FOR_UPDATES, this);

            Location location = mLocationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            comparePrintInfo(location);

            return;
        }


    }


    @Override
    public void onLocationChanged(Location location) {
        Log.i(TAG, "onLocationChanged Latitude: " + location.getLatitude() + " Longitude: " + location.getLongitude());

        comparePrintInfo(location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Log.i(TAG, "onStatusChanged("  + s +")");
    }

    @Override
    public void onProviderEnabled(String s) {
        Log.i(TAG, "onProviderEnabled("  + s +")");
    }

    @Override
    public void onProviderDisabled(String s) {
        Log.i(TAG, "onProviderDisabled("  + s +")");
    }

    //This method uses distanceTo() that returns the approximate distance in meters between this location and the given location.
    private float getDistance(double deviceLatitude, double deviceLongitude, double rLatitude, double rLongitude){

        //Device Location
        Location locationDevice = new Location("Android Device Location.");
        locationDevice.setLatitude(deviceLatitude);
        locationDevice.setLongitude(deviceLongitude);
        //Location to compare
        Location locationValue = new Location("location value.");
        locationValue.setLatitude(rLatitude); //Latitud
        locationValue.setLongitude(rLongitude); //Longitud

        //distanceTo()
        //https://developer.android.com/reference/android/location/Location.html#distanceTo(android.location.Location)
        //Returns the approximate distance in meters between this location and the given location.
        return locationDevice.distanceTo(locationValue);
    }

    /*TODO: Jorgesys, * Very important to change this method to get your geolocation points to be compared with the position of your device*/
    private ArrayList<Address> getListOfPostitions(){

        listOfGeoPositions = new ArrayList<Address>();
        Address geoPosition;
        geoPosition = new Address(Locale.getDefault());
        geoPosition.setFeatureName("Palas Ice Skating Rink.");
        geoPosition.setLatitude(47.156116);
        geoPosition.setLongitude(27.5864219);
        listOfGeoPositions.add(geoPosition);
        geoPosition = new Address(Locale.getDefault());
        geoPosition.setFeatureName("Gradina publica IASI.");
        geoPosition.setLatitude(25.6723275);
        geoPosition.setLongitude(-100.3101152);
        listOfGeoPositions.add(geoPosition);
        geoPosition = new Address(Locale.getDefault());
        geoPosition.setFeatureName("Platz Bierhaus.");
        geoPosition.setLatitude(25.667943);
        geoPosition.setLongitude(-100.3103716);
        listOfGeoPositions.add(geoPosition);
        geoPosition = new Address(Locale.getDefault());
        geoPosition.setFeatureName("Palatul Cultura.");
        geoPosition.setLatitude(47.1557913);
        geoPosition.setLongitude(27.5861617);
        listOfGeoPositions.add(geoPosition);
        geoPosition = new Address(Locale.getDefault());
        geoPosition.setFeatureName("Equestrian statue of Stefan cel Mare.");
        geoPosition.setLatitude(47.1573927);
        geoPosition.setLongitude(27.5863307);
        listOfGeoPositions.add(geoPosition);

        return listOfGeoPositions;
    }


    private void comparePrintInfo(Location deviceLocation){

         /*TODO: Jorgesys, * Delete this block that overrides the device location. Used for testing purposes */
        deviceLocation.setLatitude(47.160177);
        deviceLocation.setLongitude(27.585999);
        /*-----------------------------------------*/

        //Get positions to compare with Device position.
        //The method getListOfPostitions() must have the query to get the locations to compare with the device location.
        listOfGeoPositions = getListOfPostitions();
        //Print info.
        String myData= "";
        myData += "<font color=#6ef442>Android</font> Device position:<br><b>Downtown IASI.</b><br>Latitude: " + deviceLocation.getLatitude() + " Longitude: " + deviceLocation.getLongitude() + "<br><br>";
        float distance;
        for(int i = 0 ;i<listOfGeoPositions.size();i++) {
            distance = getDistance(deviceLocation.getLatitude(), deviceLocation.getLongitude(), listOfGeoPositions.get(i).getLatitude(), listOfGeoPositions.get(i).getLongitude());
            Log.i(TAG, "Distance calculated: " + distance);
            if(distance < ONE_KM) {
                myData += "<b>" + listOfGeoPositions.get(i).getFeatureName() + "</b><br>latitude: " + listOfGeoPositions.get(i).getLatitude() + ", longitude: " + listOfGeoPositions.get(i).getLongitude() +"<br><font color=#0000FF>Distance in range : " + distance + " mts.</font><br>";
            }else{
                myData += "<b>" +listOfGeoPositions.get(i).getFeatureName() + "</b><br>latitude: " + listOfGeoPositions.get(i).getLatitude() + ", longitude: " + listOfGeoPositions.get(i).getLongitude() +"<br><font color=#FF0000>Distance out of range : " + distance + " mts.</font><br>";
            }
        }
        myTextView.setText(Html.fromHtml(myData));

    }

}
