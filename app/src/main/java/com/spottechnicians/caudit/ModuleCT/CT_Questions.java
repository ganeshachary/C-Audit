package com.spottechnicians.caudit.ModuleCT;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.spottechnicians.caudit.Login;
import com.spottechnicians.caudit.R;
import com.spottechnicians.caudit.models.VisitSingleton;
import com.spottechnicians.caudit.utils.LocationFetch;
import com.spottechnicians.caudit.utils.UtilCT;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CT_Questions extends AppCompatActivity {
    //Visit visit;
    VisitSingleton visit;

    String ansewers[] = {"", "", "", "", "", "", "", "", "", "", "", ""};
    String otherText[]={"","","","","","","","","","","",""};
    int currentButtonPressed;
    int buttonType;
    String questionEnglishArray[];
    String questionHindiArray[];
    int textViewIds[];

    LocationFetch locationFetch;


    String[] latlong;



    SharedPreferences sharedPreferences;

    public static void displayPromptForEnablingDateTime(final Activity activity) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        final String action = Settings.ACTION_DATE_SETTINGS;
        final String message = "turn on date and time to AUTO ";

        builder.setMessage(message)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                activity.startActivity(new Intent(action));
                                activity.finish();
                                d.dismiss();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                activity.finish();
                                d.cancel();
                            }
                        });
        builder.create().show();
    }

    //made this method static so can be called easily while checking location availability
    public static void showLocationSettings(final Activity activity) {


        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);

        alertDialog.setTitle("Location SETTINGS");

        alertDialog
                .setMessage("Location" + " is not enabled! Want to go to settings menu?");

        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ct__questions);

        //visit=new Visit();
        visit=VisitSingleton.getInstance();

        setTimeDate();//sets time and date setting and update the atm date and time on list item click
        setVisitID();

        //setLatLong();


        questionEnglishArray=getResources().getStringArray(R.array.ct_question);
        textViewIds = UtilCT.getResourceQuestion();
        UtilCT.setEnglishQuestion(questionEnglishArray, textViewIds, this);
        assignPopupToNOButton();
        assignPopupToYesButton();

    }

    private void setLatLong() {


        locationFetch=new LocationFetch(this);

        //latlong=new String[2];

        latlong=locationFetch.getLatitudeLongitude();

        if(latlong!=null)
        {

            if(latlong.length==1)
            {
                showLocationSettings(this);

                Toast.makeText(this,latlong[0],Toast.LENGTH_LONG).show();
            }
            else
            {
                visit.setLatitude(latlong[0]);

                visit.setLongitude(latlong[1]);

                Toast.makeText(this,latlong[0]+" "+latlong[1]+" "+latlong[2],Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(this,"Check Settings",Toast.LENGTH_LONG).show();
        }



    }

    public void setTimeDate() {
        try {
            int answer = android.provider.Settings.System.getInt(getContentResolver(),
                    android.provider.Settings.Global.AUTO_TIME);
            int answer2 = android.provider.Settings.System.getInt(getContentResolver(),
                    Settings.Global.AUTO_TIME_ZONE);
            //Toast.makeText(this,answer+"and"+answer2,Toast.LENGTH_LONG).show();
            if (answer == 0 || answer2 == 0) {
                displayPromptForEnablingDateTime(this);

            }
            if (answer == 1 || answer2 == 1) {
                Calendar c = Calendar.getInstance();
               // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


                String strDateTime = sdf.format(c.getTime());
                //SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss a");
                //String Time = sdf2.format(c.getTime());
                visit.setDatetime(strDateTime);

                Toast.makeText(this, strDateTime, Toast.LENGTH_LONG).show();

            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void setVisitID()
    {
        sharedPreferences=getSharedPreferences(Login.USER_ID_LOGIN_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        String userIDEnterd=sharedPreferences.getString(Login.UserIdEntered,null);
        visit.setVisitId(userIDEnterd + "_" + visit.getDatetime());
        visit.setUserId(userIDEnterd);
        //visit.setLatitude("0");
        // visit.setLongitude("0");
    }

    public void onNext(View view) {
        String result = "";

        String allAns[] = new String[ansewers.length];
        for (int i = 0; i < ansewers.length; i++) {
            result = result + ansewers[i] + otherText[i] + "/";

            allAns[i] = ansewers[i] + "," + otherText[i];

        }
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();

        visit.setCt(ansewers);
        if (visit.checkCTComplete()) {
            Toast.makeText(this, "complete", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, Photo_Of_CT.class);
            startActivity(intent);
        } else {
             Toast.makeText(this,"Answer all the question",Toast.LENGTH_LONG).show();
        }
    }






    public int getCurrentButtonPressed() {

        return currentButtonPressed;
    }

    public void setCurrentButtonPressed(int currentButtonPressed) {

        this.currentButtonPressed = currentButtonPressed;
    }

    public int getButtonType() {

        return buttonType;
    }

    public void setButtonType(int buttonType) {

        this.buttonType = buttonType;
    }


    public void assignPopupToYesButton()
    {

        final int btnYesArray[] = UtilCT.getYesButtonIdsArray();
        int btnNoArray[] = UtilCT.getNoButtonIdsArray();
        for(int i=0;i<12;i++)
        {

            if (btnYesArray[i] == btnYesArray[1] || btnYesArray[i] == btnYesArray[5] || btnYesArray[i] == btnYesArray[11]) {//


                findViewById(btnYesArray[i]).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setButtonType(1);
                        setCurrentButtonPressed(UtilCT.getPositionOfYesButton(v.getId(), getButtonType()));
                        //Toast.makeText(getBaseContext(),getCurrentButtonPressed()+"", Toast.LENGTH_SHORT).show();
                        ((Button) findViewById((UtilCT.getNoButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.black));
                        ((Button) findViewById((UtilCT.getYesButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.red));

                        showPopup(getCurrentButtonPressed());
                    }
                });
            }
            else
            {

                findViewById(btnYesArray[i]).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setButtonType(1);
                        setCurrentButtonPressed(UtilCT.getPositionOfYesButton(v.getId(), getButtonType()));
                       // Toast.makeText(getBaseContext(), "This is normal button", Toast.LENGTH_SHORT).show();
                        ((Button) findViewById((UtilCT.getYesButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.green));
                        ((Button) findViewById((UtilCT.getNoButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.black));

                        ansewers[currentButtonPressed]="yes";
                        otherText[currentButtonPressed]="";
                        // Toast.makeText(getBaseContext(),ansewers[currentButtonPressed]+"", Toast.LENGTH_SHORT).show();

                    }
                });
            }


        }

    }

   /* public int getPositionOfYesButton(long id)
    {;
        int array[];
        if(getButtonType()==1)
        {
            array=UtilCT.getYesButtonIdsArray();
        }
        else {
             array=UtilCT.getNoButtonIdsArray();
        }

        int position=0;
        for(int i=0;i<array.length;i++)
        {
            if(array[i]==id)
            {
                position=i;
                return position;
            }
        }
        return position;
    }
*/


    public void assignPopupToNOButton()
    {
       // int btnYesArray[]=getYesButtonIdsArray();
        int btnNoArray[] = UtilCT.getNoButtonIdsArray();
        for(int i=0;i<12;i++)
        {
            setCurrentButtonPressed(i);
            if (btnNoArray[i] == btnNoArray[5] || btnNoArray[i] == btnNoArray[11])
            {

                findViewById(btnNoArray[i]).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Toast.makeText(getBaseContext(), "This is normal button", Toast.LENGTH_SHORT).show();
                        setButtonType(0);
                        setCurrentButtonPressed(UtilCT.getPositionOfYesButton(v.getId(), getButtonType()));
                        ((Button) findViewById((UtilCT.getYesButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.black));
                        ((Button) findViewById((UtilCT.getNoButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.green));

                        ansewers[currentButtonPressed]="no";
                        otherText[currentButtonPressed]="";
                        //Toast.makeText(getBaseContext(),ansewers[currentButtonPressed]+"", Toast.LENGTH_SHORT).show();

                    }
                });
            }
            else
            {
                findViewById(btnNoArray[i]).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setButtonType(0);
                        setCurrentButtonPressed(UtilCT.getPositionOfYesButton(v.getId(), getButtonType()));
                            //showTextPopUp();
                        ((Button) findViewById((UtilCT.getYesButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.black));
                        ((Button) findViewById((UtilCT.getNoButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.red));

                        showPopup(getCurrentButtonPressed());
                        //((Button)v).setTextColor(getResources().getColor(R.color.red));



                    }
                });

            }


        }


    }

    public void showPopup(final int buttonPressed)
    {

        Dialog dialog;

         String array[];
        if(getButtonType()==1)
        {
            array = UtilCT.getYesSubQuestion();
        }
        else
        {
            array = UtilCT.getNoSubQuestion();
        }


        final String[] items=array[buttonPressed].split("-");
        final List<String> itemsSelected = new ArrayList();
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Reasons");
        builder.setMultiChoiceItems(items, null,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedItemId,
                                        boolean isSelected) {
                        if (isSelected) {
                            // Toast.makeText(getBaseContext(),items[selectedItemId].toString()+"",Toast.LENGTH_SHORT).show();
                            itemsSelected.add(items[selectedItemId]);

                        } else if (itemsSelected.contains(items[selectedItemId])) {
                            itemsSelected.remove(items[selectedItemId]);
                        }


                    }
                })
                .setPositiveButton("Done!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String oneAnswer;

                        if (!(itemsSelected.size() == 0)) {
                            if (getButtonType() == 1) {
                                oneAnswer = "yes";
                            } else {
                                oneAnswer = "no";
                            }


                            if (itemsSelected.contains("Other")) {
                                //Toast.makeText(getBaseContext(),"other is selected", Toast.LENGTH_SHORT).show();
                                showTextPopUp();
                                for (int i = 0; i < itemsSelected.size(); i++) {
                                    oneAnswer = oneAnswer + "," + itemsSelected.get(i);
                                }
                                ansewers[buttonPressed] = oneAnswer;
                                Toast.makeText(getBaseContext(), ansewers[buttonPressed] + "", Toast.LENGTH_SHORT).show();


                            } else {

                                for (int i = 0; i < itemsSelected.size(); i++) {
                                    oneAnswer = oneAnswer + "," + itemsSelected.get(i);
                                }
                                ansewers[buttonPressed] = oneAnswer;
                                Toast.makeText(getBaseContext(), ansewers[buttonPressed] + "", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else
                        {
                            if (getButtonType() == 1) {
                                ((Button) findViewById((UtilCT.getYesButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.black));
                            } else {
                                ((Button) findViewById((UtilCT.getNoButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.black));
                            }
                            ansewers[buttonPressed] = buttonPressed + 1 + "";

                        }
                    }

                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (getButtonType() == 1) {
                            ((Button) findViewById((UtilCT.getYesButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.black));
                        } else {
                            ((Button) findViewById((UtilCT.getNoButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.black));
                        }
                        ansewers[buttonPressed] = buttonPressed + 1 + "";


                    }
                });
        dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();


    }



        public void showTextPopUp()
        {
            AlertDialog dialog2;
            final EditText editView=new EditText(this);
            AlertDialog.Builder builder2=new AlertDialog.Builder(this);
            builder2.setTitle("Enter the reason");
            builder2.setView(editView);
            builder2.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    otherText[currentButtonPressed]=(editView.getText().toString());
                   // Toast.makeText(getBaseContext(),getOtherText(),Toast.LENGTH_SHORT).show();

                }
            });


            dialog2=builder2.create();
            dialog2.setCancelable(false);
            dialog2.show();
        }



   /* public void setEnglishQuestion(String questionArray[],int textViewIds[])
    {

        for(int i=0;i<textViewIds.length;i++)
        {
            ((TextView)findViewById(textViewIds[i])).setText(questionArray[i]);
        }

    }*/
}
