package sample.com.digiweathersample.data.services;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;


import java.io.IOException;
import java.util.List;
import java.util.Locale;

import sample.com.digiweathersample.R;
import sample.com.digiweathersample.contracts.MainWeatherContract;

/**
 * Created by LENOVO on 12/9/2017.
 */

public class LocationServices implements LocationListener {
    private LocationManager locationManager;
    private Context context;
    private String cityName;
    private MainWeatherContract.Model weatherModel;
    private int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 10;

    public LocationServices(Context context, MainWeatherContract.Model weatherModel) {
        this.weatherModel = weatherModel;
        this.context = context;
        requestLocation();
    }

    @Override
    public void onLocationChanged(Location location) {

            Geocoder geoCoder = new Geocoder(context, Locale.ENGLISH); //it is Geocoder

        try {
            List<Address> address = geoCoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

            weatherModel.getWeather(address.get(0).getLocality());

            if (location != null)
                locationManager.removeUpdates(this);

        } catch (IOException e) {
        } catch (NullPointerException e) {
        }

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


    public void requestLocation() {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAccuracy(Criteria.ACCURACY_FINE);


        String provider = locationManager.getBestProvider(criteria, false);

        if (ActivityCompat.checkSelfPermission((Activity) context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_FINE_LOCATION);

            return;
        }
        if (!locationManager.isProviderEnabled(provider)){
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setMessage(context.getResources().getString(R.string.gps_network_not_enabled));
            dialog.setPositiveButton(context.getResources().getString(R.string.open_location_settings), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    context.startActivity(myIntent);
                    //get gps
                }
            });
            dialog.setNegativeButton(context.getString(R.string.Cancel), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub

                }
            });
            dialog.show();
        }

        Location location = locationManager.getLastKnownLocation(provider);

        locationManager.requestLocationUpdates(provider, 0, 0, this);
    }


}

