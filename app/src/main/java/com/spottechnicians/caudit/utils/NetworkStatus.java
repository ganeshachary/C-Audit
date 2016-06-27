package com.spottechnicians.caudit.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;

/**
 * Created by onestech on 27/06/16.
 */
public class NetworkStatus {


    public static int networkStatus(Activity activity) {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getTypeName().equalsIgnoreCase("MOBILE")) {
                return 2;
            } else if (networkInfo.getTypeName().equalsIgnoreCase("WIFI")) {
                return 3;

            }

            return 1;
        } else {

            return 0;


        }


    }

    public static void showLocationSettings(final Activity activity) {


        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);

        alertDialog.setTitle("Location SETTINGS");

        alertDialog
                .setMessage("Location" + " is not enabled! Want to go to settings menu?");

        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);

                        activity.startActivity(intent);
                        //   activity.finish();
                        dialog.dismiss();
                    }
                });

        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        //   activity.finish();

                        dialog.cancel();
                    }
                });

        alertDialog.show();


    }
}
