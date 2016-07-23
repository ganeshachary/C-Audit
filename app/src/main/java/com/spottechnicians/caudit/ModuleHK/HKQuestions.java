package com.spottechnicians.caudit.ModuleHK;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.spottechnicians.caudit.Activities.Home;
import com.spottechnicians.caudit.Activities.Login;
import com.spottechnicians.caudit.R;
import com.spottechnicians.caudit.models.VisitSingleton;
import com.spottechnicians.caudit.utils.LocationFetch;
import com.spottechnicians.caudit.utils.UtilHK;
import com.spottechnicians.caudit.utils.Utility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HKQuestions extends AppCompatActivity {

    public String arrayE[], itemsSubQ[];
    VisitSingleton visit;
    String ansewers[] = {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "};
    String otherText[] = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
    int currentButtonPressed;
    int buttonType;
    String questionEnglishArray[];
    String hkQuestionHindiArray[];
    String questionHindiArray[];
    int textViewIds[];

    int month, day, year;

    LocationFetch locationFetch;


    String[] latlong;
    SharedPreferences sharedPreferences;
    private String backMessage;

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
        hkQuestionHindiArray = getResources().getStringArray(R.array.hk_questions_hindi);

        String lang = Utility.getLanguage(this);

        backMessage = getString(R.string.backMessageFromQuestion);
        Home.printToast(lang, this);

        if (lang != null && lang.equals("Hindi")) {
            questionEnglishArray = hkQuestionHindiArray;
            backMessage = getString(R.string.backMessageFromQuestionHindi);
        }

        textViewIds = UtilHK.getResourceQuestion();
        UtilHK.setEnglishQuestion(questionEnglishArray, textViewIds, this);
        assignPopupToNOButton();
        assignPopupToYesButton();
        getSupportActionBar().setTitle(visit.getAtmId() + " : HK");

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        if (visit.checkEmpty(ansewers)) {
            super.onBackPressed();
        } else {
            new AlertDialog.Builder(this)
                    .setMessage(backMessage)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                           /* Intent ii=new Intent(HKQuestions.this, Daily_Audit.class);
                            ii.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(ii);*/
                            HKQuestions.this.finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .create()
                    .show();
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

            allAns[i] = ansewers[i] + "" + otherText[i];

        }
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();

        // visit.setHk(ansewers);
        visit.setHk(allAns);
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

            if (!yesSubQuestion[i].equals("null") && !yesSubQuestion[i].equals("date")) {


                findViewById(btnYesArray[i]).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setButtonType(1);
                        setCurrentButtonPressed(UtilHK.getPositionOfYesButton(v.getId(), getButtonType()));
                        //Toast.makeText(getBaseContext(),getCurrentButtonPressed()+"", Toast.LENGTH_SHORT).show();
                     /*   ((Button) findViewById((UtilHK.getNoButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.black));
                        ((Button) findViewById((UtilHK.getYesButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.red));
*/
                        showPopup(getCurrentButtonPressed());
                    }
                });
            } else if (yesSubQuestion[i].equals("date"))//if yes button selected for which date is to be shown
            {
                findViewById(btnYesArray[i]).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setButtonType(1);
                        setCurrentButtonPressed(UtilHK.getPositionOfYesButton(v.getId(), getButtonType()));
                        // Toast.makeText(getBaseContext(), "This is normal button", Toast.LENGTH_SHORT).show();
                        // ((Button) findViewById((UtilHK.getYesButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.green));
                        //  ((Button) findViewById((UtilHK.getNoButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.black));

                        //ansewers[currentButtonPressed] = "yes";
                        otherText[currentButtonPressed] = "";
                        showDatePopup(getCurrentButtonPressed());
                        // Toast.makeText(getBaseContext(),ansewers[currentButtonPressed]+"", Toast.LENGTH_SHORT).show();

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


    //method will be used to show date dialog for date Selection
    private void showDatePopup(final int currentButtonPressed) {


        if (ansewers[currentButtonPressed].equals(" ") || ansewers[currentButtonPressed].substring(0, 2).equals("no")) {
            Calendar calendarCurrent = Calendar.getInstance();

            month = calendarCurrent.get(Calendar.MONTH);
            day = calendarCurrent.get(Calendar.DAY_OF_MONTH);
            year = calendarCurrent.get(Calendar.YEAR);

        } else {
            String lastDateString = ansewers[currentButtonPressed];
            DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");

            try {
                Date lastDate = df.parse(lastDateString);

                Calendar calendarSelected = Calendar.getInstance();

                calendarSelected.setTime(lastDate);

                month = calendarSelected.get(Calendar.MONTH);
                day = calendarSelected.get(Calendar.DAY_OF_MONTH);
                year = calendarSelected.get(Calendar.YEAR);


            } catch (ParseException e) {
                Log.e("Man HKQ: ", e.toString() + " Error coverting string to date");

                Log.e("Man HKQ:", "subString of " + ansewers[currentButtonPressed] + " is " + ansewers[currentButtonPressed].substring(0, 2));
            }


        }


        final DatePickerDialog datePicker;

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                Toast.makeText(getApplicationContext(), i + " " + i1 + " " + i2, Toast.LENGTH_LONG).show();
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, i);
                calendar.set(Calendar.MONTH, i1);
                calendar.set(Calendar.DAY_OF_MONTH, i2);

                Date pickedDate = calendar.getTime();

                // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");

                String strDateTime = sdf.format(pickedDate);


                ansewers[currentButtonPressed] = strDateTime;
                ((Button) findViewById((UtilHK.getYesButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.green));
                ((Button) findViewById((UtilHK.getNoButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.black));


            }

        };


        if (Build.VERSION.SDK_INT < 11) {
            datePicker = new DatePickerDialog(this, dateSetListener, year, month, day);
        } else {
            // specify the Holo Light Theme for this dialog on Honeycomb+
            datePicker = new DatePickerDialog(this, DatePickerDialog.THEME_HOLO_LIGHT, dateSetListener, year, month, day);
        }
        String dateTitle;
        if (currentButtonPressed == 14) {
            dateTitle = "Select date on which HK materials were placed on site";
        } else {
            dateTitle = "Select Date";
        }



        datePicker.getDatePicker().setMaxDate(System.currentTimeMillis());

        datePicker.setTitle(dateTitle);
        datePicker.show();

        datePicker.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {


                // ((Button) findViewById((UtilHK.getYesButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.black));
                datePicker.cancel();

            }
        });


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


    public void showPopup(final int buttonPressed) {

        Dialog dialog;

        String array[], arrayH[];

        if (getButtonType() == 1) {
            array = UtilHK.getYesSubQuestion();
            arrayE = UtilHK.getYesSubQuestion();//Every time option data will be stored from this because it will always be in english
            arrayH = UtilHK.getYesSubQuestionH();
        } else {
            array = UtilHK.getNoSubQuestion();
            arrayE = UtilHK.getNoSubQuestion();
            arrayH = UtilHK.getNoSubQuestionH();
        }

        if (Utility.getLanguage(HKQuestions.this) != null && Utility.getLanguage(HKQuestions.this).equals("Hindi")) {
            array = arrayH;
        }

        final String[] items = array[buttonPressed].split("-");

        itemsSubQ = arrayE[buttonPressed].split("-");

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
                            //   itemsSelected.add(items[selectedItemId]);

                            itemsSelected.add(itemsSubQ[selectedItemId]);


                        }/* else if (itemsSelected.contains(items[selectedItemId])) {
                            itemsSelected.remove(items[selectedItemId]);
                        }*/ else if (itemsSelected.contains(itemsSubQ[selectedItemId])) {

                            itemsSelected.remove(itemsSubQ[selectedItemId]);

                            //  checkedStates[currentButtonPressed][selectedItemId]=false;
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
                                ((Button) findViewById((UtilHK.getYesButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.red));
                                ((Button) findViewById((UtilHK.getNoButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.black));

                            } else {
                                oneAnswer = "no";
                                ((Button) findViewById((UtilHK.getYesButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.black));
                                ((Button) findViewById((UtilHK.getNoButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.red));

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
                            Home.printToast("No option Selected", HKQuestions.this);
                          /*  if (getButtonType() == 1) {
                                ((Button) findViewById((UtilHK.getYesButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.black));
                            } else {
                                ((Button) findViewById((UtilHK.getNoButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.black));
                            }*/
                            //ansewers[buttonPressed] = buttonPressed + 1 + "";

                        }
                    }

                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                       /* if (getButtonType() == 1) {
                            ((Button) findViewById((UtilHK.getYesButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.black));
                        } else {
                            ((Button) findViewById((UtilHK.getNoButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.black));
                        }*/

                        //ansewers[buttonPressed] = buttonPressed + 1 + "";
                        dialog.dismiss();


                    }
                });
        dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();


    }




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
            } else if (noSubQuestion[i].equals("no date")) //if no button selected for which no date is to be shown
            {
                findViewById(btnNoArray[i]).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Toast.makeText(getBaseContext(), "This is normal button", Toast.LENGTH_SHORT).show();
                        setButtonType(0);
                        setCurrentButtonPressed(UtilHK.getPositionOfYesButton(v.getId(), getButtonType()));
                        ((Button) findViewById((UtilHK.getYesButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.black));
                        ((Button) findViewById((UtilHK.getNoButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.red));

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
                      /*  ((Button) findViewById((UtilHK.getYesButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.black));
                        ((Button) findViewById((UtilHK.getNoButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.red));
*/
                        showPopup(getCurrentButtonPressed());
                        //((Button)v).setTextColor(getResources().getColor(R.color.red));


                    }
                });

            }


        }


    }

    public void showTextPopUp() {
        final AlertDialog dialog2;
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

        dialog2.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editView.getText().toString().trim().equals("")) {
                    otherText[currentButtonPressed] = (editView.getText().toString());

                    dialog2.dismiss();
                } else {
                    Utility.printToast("Enter some reason", HKQuestions.this);
                }
            }
        });


    }





}



