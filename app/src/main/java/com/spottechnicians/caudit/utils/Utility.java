package com.spottechnicians.caudit.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.spottechnicians.caudit.models.Atm;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Mangal on 7/8/2016.
 */
public class Utility {

    public static final String LANGUAGE_PREFERENCES = "LanguagePref";
    public static final String CurrentLang = "CurrentLanguage";
    private static SharedPreferences sharedPreferenceslang;
    private HashMap<String, String> PicList = new HashMap<>();

    public static void setHindi(Context context) {

        sharedPreferenceslang = context.getSharedPreferences(LANGUAGE_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferenceslang.edit();

        editor.putString(CurrentLang, "Hindi");
        editor.commit();
    }

    public static void setEng(Context context) {

        sharedPreferenceslang = context.getSharedPreferences(LANGUAGE_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferenceslang.edit();

        editor.putString(CurrentLang, "Eng");
        editor.commit();
    }

    public static String getLanguage(Context context) {
        sharedPreferenceslang = context.getSharedPreferences(LANGUAGE_PREFERENCES, Context.MODE_PRIVATE);
        String lang = sharedPreferenceslang.getString(CurrentLang, null);
        return lang;
    }

    public static void setAuditedRecord(List<Atm> listOfAtms, EditText etSearchBar, TextView tvAudit) {
        if (listOfAtms.size() == 0) {
            etSearchBar.setVisibility(View.GONE);
            tvAudit.setVisibility(View.GONE);
        } else {
            int counter = 0;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String TodayDateTime = sdf.format(c.getTime());
            Date d1 = null;
            Date d2 = null;


            for (int i = 0; i < listOfAtms.size(); i++) {
                if (listOfAtms.get(i).getLastaudited() != null && listOfAtms.get(i).getLastaudited() != "not audited") {
                    try {
                        d1 = format.parse(listOfAtms.get(i).getLastaudited());
                        d2 = format.parse(TodayDateTime);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    long diff = d2.getTime() - d1.getTime();
                    int diffInDays = (int) diff / (1000 * 60 * 60 * 24);

                    if (diffInDays >= 0 && diffInDays < 31) {
                        counter++;
                    }
                }
            }
            tvAudit.setText("Audited: " + counter + " Out of " + listOfAtms.size());
        }
    }

    public static void displayPromptForEnablingDateTime(final Activity activity) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        final String action = Settings.ACTION_DATE_SETTINGS;
        final String message = "turn on date and time to AUTO ";

        builder.setMessage(message)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                activity.startActivity(new Intent(action));
                                //  activity.finish();
                                d.dismiss();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                //  activity.finish();
                                d.cancel();
                            }
                        });
        builder.create().show();
    }

    public static String setTimeDate(Activity activity) {
        try {
            int answer = android.provider.Settings.System.getInt(activity.getContentResolver(),
                    android.provider.Settings.Global.AUTO_TIME);
            int answer2 = android.provider.Settings.System.getInt(activity.getContentResolver(),
                    Settings.Global.AUTO_TIME_ZONE);
            //Toast.makeText(this,answer+"and"+answer2,Toast.LENGTH_LONG).show();
            if (answer == 0 || answer2 == 0) {
                //displayPromptForEnablingDateTime(activity);
                return null;

            }
            if (answer == 1 || answer2 == 1) {
                Calendar c = Calendar.getInstance();
                // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


                String strDateTime = sdf.format(c.getTime());
                //SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss a");
                //String Time = sdf2.format(c.getTime());

                return strDateTime;

            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean isDateAutoUpdateSet(Activity activity) {
        try {
            int answer = android.provider.Settings.System.getInt(activity.getContentResolver(),
                    android.provider.Settings.Global.AUTO_TIME);
            int answer2 = android.provider.Settings.System.getInt(activity.getContentResolver(),
                    Settings.Global.AUTO_TIME_ZONE);
            //displayPromptForEnablingDateTime(activity);
            return answer != 0 && answer2 != 0;

        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(activity, "Some error occured ,Try again!!", Toast.LENGTH_LONG).show();
            return false;
        }
        //Toast.makeText(this,answer+"and"+answer2,Toast.LENGTH_LONG).show();


    }

    public static void showDateTimeSettings(final Activity activity) {


        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        final String action = Settings.ACTION_DATE_SETTINGS;
        final String message = "turn on date and time to AUTO ";

        builder.setMessage(message)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                activity.startActivity(new Intent(action));
                                //  activity.finish();
                                d.dismiss();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                //  activity.finish();
                                d.cancel();
                            }
                        });
        builder.create().show();
    }

    public static void printToast(String s, Context c) {
        Toast t = Toast.makeText(c, s, Toast.LENGTH_LONG);
        t.setGravity(Gravity.BOTTOM, 0, 270);
        t.show();
    }

    public static Bitmap rotateImageIfRequired(Bitmap img, String selectedImagePath) throws IOException {

        ExifInterface ei = new ExifInterface(selectedImagePath);

        Log.e("ManB", "the Path is " + selectedImagePath);

        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:

                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }

    public static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);

        Log.e("ManB", "Orientation is " + degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();


        Log.e("ManB", "After orientation of " + degree + " " + rotatedImg.getWidth() + "*" + rotatedImg.getHeight());
        return rotatedImg;
    }

    public String getPicList(String key) {

        PicList.put("MP", "Madhya Pradesh");
        PicList.put("CT", "Chattisgarh");
        PicList.put("OR", "Odisha");
        PicList.put("BR", "Bihar");
        PicList.put("WB", "West Bengal");
        PicList.put("JH", "Jharkhand");
        PicList.put("AP", "Arunachal Pradesh");
        PicList.put("AS", "Assam");
        PicList.put("ML", "Meghalaya");
        PicList.put("MN", "Manipur");
        PicList.put("MZ", "Mizoram");
        PicList.put("NL", "Nagaland");
        PicList.put("TR", "Tripura");
        PicList.put("SK", "Sikkim");
        PicList.put("UP", "Uttar Pradesh");
        PicList.put("JK", "Jammu and Kashmir");
        PicList.put("HP", "Himachal Pradesh");
        PicList.put("UT", "Uttarakhand");
        PicList.put("PB", "Punjab");
        PicList.put("HR", "Haryana");
        PicList.put("DL", "Delhi");
        PicList.put("CH", "Chandigarah");
        PicList.put("AN", "Andaman and Nicobar");
        PicList.put("KA", "Karnataka");
        PicList.put("AP", "Andhra Pradesh");
        PicList.put("TN", "Tamilnadu");
        PicList.put("TL", "Telangana");
        PicList.put("KL", "Kerala");
        PicList.put("PY", "Puducherry");
        PicList.put("LD", "Lakshadweep Islands");
        PicList.put("RJ", "Rajasthan");
        PicList.put("MH", "Maharashtra");
        PicList.put("GJ", "Gujrat");
        PicList.put("GA", "Goa");
        PicList.put("DN", "Dadra and Nagar Haveli");
        PicList.put("DD", "Daman and Diu");

        return PicList.get(key);

    }


}
