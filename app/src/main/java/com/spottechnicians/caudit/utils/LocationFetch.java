package com.spottechnicians.caudit.utils;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Mangal on 6/15/2016.
 */
public class LocationFetch extends AppCompatActivity implements LocationListener {

    private LocationManager locationManager;
    private String Latitude,Longitude;

    private Location location;

    String[] loc;

    private static final long MIN_DISTANCE_FOR_UPDATE = 10;
    private static final long MIN_TIME_FOR_UPDATE = 1000 * 30;


    public LocationFetch(Context context){

        locationManager = (LocationManager) context
                .getSystemService(LOCATION_SERVICE);

    }

    public String[] getLatitudeLongitude()
    {
        loc=new String[3];

        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                ||locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
                ||locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)){


            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    MIN_TIME_FOR_UPDATE, MIN_DISTANCE_FOR_UPDATE, this);

            if(locationManager!=null) {
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                if (location != null) {
                    loc[0] = String.valueOf(location.getLatitude());
                    loc[1] = String.valueOf(location.getLongitude());
                    loc[2] = "Using GPSin";
                    return loc;
                } else

                {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                        if (location != null) {
                            loc[0] = String.valueOf(location.getLatitude());
                            loc[1] = String.valueOf(location.getLongitude());
                            loc[2] = "Using NETWORKin";
                            return loc;
                        } else{

/*
                            location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

                            if (location != null) {
                                loc[0] = String.valueOf(location.getLatitude());
                                loc[1] = String.valueOf(location.getLongitude());
                                loc[2] = "Using PASSIVE";
                                return loc;
                            } else return new String[]{"error1"};*/

                            return new String[] {"No GPS No Network"};


                        }


                }



            }
            else {

                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        MIN_TIME_FOR_UPDATE, MIN_DISTANCE_FOR_UPDATE, this);


                if(locationManager!=null) {
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    if (location != null) {
                        loc[0] = String.valueOf(location.getLatitude());
                        loc[1] = String.valueOf(location.getLongitude());
                        loc[2] = "Using NETWORK";
                        return loc;
                    } else return new String[]{"error2"};
                }

                else
                {
                    locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER,
                            MIN_TIME_FOR_UPDATE, MIN_DISTANCE_FOR_UPDATE, this);


                    if(locationManager!=null) {
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                        if (location != null) {
                            loc[0] = String.valueOf(location.getLatitude());
                            loc[1] = String.valueOf(location.getLongitude());
                            loc[2] = "Using PASSIVE";
                            return loc;
                        } else return new String[]{"error3"};
                    }

                }





            }





           // locationManager.getBestProvider(Criteria.ACCURACY_FINE,true);



        }


        else

            return null;


        return null;
    }


    @Override
    public void onLocationChanged(Location location) {

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
}
