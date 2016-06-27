package com.spottechnicians.caudit.ModuleHK;

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
import com.spottechnicians.caudit.utils.UtilHK;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HKQuestions extends AppCompatActivity {

    VisitSingleton visit;

    String ansewers[] = {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "};
    String otherText[] = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hkquestions);

        //visit=new Visit();
        visit = VisitSingleton.getInstance();

        setTimeDate();//sets time and date setting and update the atm date and time on list item click
        setVisitID();

        //setLatLong();


        questionEnglishArray = getResources().getStringArray(R.array.hk_question);
        textViewIds = UtilHK.getResourceQuestion();
        UtilHK.setEnglishQuestion(questionEnglishArray, textViewIds, this);
        assignPopupToNOButton();
        assignPopupToYesButton();

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

    public void setVisitID() {
        sharedPreferences = getSharedPreferences(Login.USER_ID_LOGIN_PREFERENCES, Context.MODE_PRIVATE);
        // SharedPreferences.Editor editor=sharedPreferences.edit();
        String userIDEnterd = sharedPreferences.getString(Login.UserIdEntered, null);
        visit.setVisitId(userIDEnterd + "_" + visit.getDatetime());
        visit.setUserId(userIDEnterd);
    }

    public void onNext(View view) {
        String result = "";

        String allAns[] = new String[ansewers.length];
        for (int i = 0; i < ansewers.length; i++) {
            result = result + ansewers[i] + otherText[i] + "/";

            allAns[i] = ansewers[i] + "," + otherText[i];

        }
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();

        visit.setHk(ansewers);
        if (visit.checkHKComplete()) {
            Toast.makeText(this, "complete", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, PhotoOfHK.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Answer all the question", Toast.LENGTH_LONG).show();
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


    public void assignPopupToYesButton() {

        final int btnYesArray[] = UtilHK.getYesButtonIdsArray();
        int btnNoArray[] = UtilHK.getNoButtonIdsArray();
        String yesSubQuestion[] = UtilHK.getYesSubQuestion();
        for (int i = 0; i < 17; i++) {

            if (!yesSubQuestion[i].equals("null")) {


                findViewById(btnYesArray[i]).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setButtonType(1);
                        setCurrentButtonPressed(UtilHK.getPositionOfYesButton(v.getId(), getButtonType()));
                        //Toast.makeText(getBaseContext(),getCurrentButtonPressed()+"", Toast.LENGTH_SHORT).show();
                        ((Button) findViewById((UtilHK.getNoButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.black));
                        ((Button) findViewById((UtilHK.getYesButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.red));

                        showPopup(getCurrentButtonPressed());
                    }
                });
            } else {

                findViewById(btnYesArray[i]).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setButtonType(1);
                        setCurrentButtonPressed(UtilHK.getPositionOfYesButton(v.getId(), getButtonType()));
                        // Toast.makeText(getBaseContext(), "This is normal button", Toast.LENGTH_SHORT).show();
                        ((Button) findViewById((UtilHK.getYesButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.green));
                        ((Button) findViewById((UtilHK.getNoButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.black));

                        ansewers[currentButtonPressed] = "yes";
                        otherText[currentButtonPressed] = "";
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


    public void assignPopupToNOButton() {
        // int btnYesArray[]=getYesButtonIdsArray();
        int btnNoArray[] = UtilHK.getNoButtonIdsArray();
        String noSubQuestion[] = UtilHK.getNoSubQuestion();
        for (int i = 0; i < 17; i++) {
            setCurrentButtonPressed(i);
            if (noSubQuestion[i].equals("null")) {

                findViewById(btnNoArray[i]).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Toast.makeText(getBaseContext(), "This is normal button", Toast.LENGTH_SHORT).show();
                        setButtonType(0);
                        setCurrentButtonPressed(UtilHK.getPositionOfYesButton(v.getId(), getButtonType()));
                        ((Button) findViewById((UtilHK.getYesButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.black));
                        ((Button) findViewById((UtilHK.getNoButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.green));

                        ansewers[currentButtonPressed] = "no";
                        otherText[currentButtonPressed] = "";
                        //Toast.makeText(getBaseContext(),ansewers[currentButtonPressed]+"", Toast.LENGTH_SHORT).show();

                    }
                });
            } else {
                findViewById(btnNoArray[i]).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setButtonType(0);
                        setCurrentButtonPressed(UtilHK.getPositionOfYesButton(v.getId(), getButtonType()));
                        //showTextPopUp();
                        ((Button) findViewById((UtilHK.getYesButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.black));
                        ((Button) findViewById((UtilHK.getNoButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.red));

                        showPopup(getCurrentButtonPressed());
                        //((Button)v).setTextColor(getResources().getColor(R.color.red));


                    }
                });

            }


        }


    }

    public void showPopup(final int buttonPressed) {

        Dialog dialog;

        String array[];
        if (getButtonType() == 1) {
            array = UtilHK.getYesSubQuestion();
        } else {
            array = UtilHK.getNoSubQuestion();
        }


        final String[] items = array[buttonPressed].split("-");
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

                        } else {
                            if (getButtonType() == 1) {
                                ((Button) findViewById((UtilHK.getYesButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.black));
                            } else {
                                ((Button) findViewById((UtilHK.getNoButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.black));
                            }
                            ansewers[buttonPressed] = buttonPressed + 1 + "";

                        }
                    }

                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (getButtonType() == 1) {
                            ((Button) findViewById((UtilHK.getYesButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.black));
                        } else {
                            ((Button) findViewById((UtilHK.getNoButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.black));
                        }
                        ansewers[buttonPressed] = buttonPressed + 1 + "";


                    }
                });
        dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();


    }


    public void showTextPopUp() {
        AlertDialog dialog2;
        final EditText editView = new EditText(this);
        AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
        builder2.setTitle("Enter the reason");
        builder2.setView(editView);
        builder2.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                otherText[currentButtonPressed] = (editView.getText().toString());
                // Toast.makeText(getBaseContext(),getOtherText(),Toast.LENGTH_SHORT).show();

            }
        });


        dialog2 = builder2.create();
        dialog2.setCancelable(false);
        dialog2.show();
    }





}



