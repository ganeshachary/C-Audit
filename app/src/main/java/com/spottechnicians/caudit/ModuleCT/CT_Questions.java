package com.spottechnicians.caudit.ModuleCT;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.spottechnicians.caudit.Activities.Home;
import com.spottechnicians.caudit.Activities.Login;
import com.spottechnicians.caudit.R;
import com.spottechnicians.caudit.models.VisitSingleton;
import com.spottechnicians.caudit.utils.LocationFetch;
import com.spottechnicians.caudit.utils.UtilCT;
import com.spottechnicians.caudit.utils.Utility;

import java.util.ArrayList;
import java.util.List;

public class CT_Questions extends AppCompatActivity {
    public String arrayE[], itemsSubQ[];
    VisitSingleton visit;
    String ansewers[] = {"", "", "", "", "", "", "", "", "", "", "", ""};
    String otherText[]={"","","","","","","","","","","",""};
    String allAns[];
    //will be used to store checked states of dialog checkboxes
    boolean[][] checkedStates = new boolean[12][];
    int currentButtonPressed;
    int buttonType;
    String questionEnglishArray[];
    String ctQuestionHindiArray[];
    String questionHindiArray[];
    int textViewIds[];

    LocationFetch locationFetch;

    int CtAffectedButtons[] = {R.id.btnCTQuestionYes2, R.id.btnCTQuestionYes3, R.id.btnCTQuestionYes8, R.id.btnCTQuestionYes9,
            R.id.btnCTQuestionYes10, R.id.btnCTQuestionNo2, R.id.btnCTQuestionNo3, R.id.btnCTQuestionNo8, R.id.btnCTQuestionNo9,
            R.id.btnCTQuestionNo10};

    String[] latlong;
    SharedPreferences sharedPreferences;
    private String backMessage; //jst to store whether Hindi of Englisg text to be shown on dialog

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ct__questions);

        //visit=new Visit();
        visit=VisitSingleton.getInstance();

        //sets time and date setting and update the atm date and time on list item click
        setVisitID();

        //setLatLong();

        questionEnglishArray=getResources().getStringArray(R.array.ct_question);
        ctQuestionHindiArray = getResources().getStringArray(R.array.ct_questions_hindi);

        String lang = Utility.getLanguage(this);

        Utility.printToast(lang, this);

        backMessage = getString(R.string.backMessageFromQuestion);

        if (lang != null && lang.equals("Hindi")) {
            backMessage = getString(R.string.backMessageFromQuestionHindi);
            questionEnglishArray = ctQuestionHindiArray;
        }

        textViewIds = UtilCT.getResourceQuestion();
        UtilCT.setEnglishQuestion(questionEnglishArray, textViewIds, this);
        assignPopupToNOButton();
        assignPopupToYesButton();

        getSupportActionBar().setTitle(visit.getAtmId() + " : CT");

    }

   /* private void setLatLong() {


        locationFetch=new LocationFetch(this);

        //latlong=new String[2];

        latlong=locationFetch.getLatitudeLongitude();

        if(latlong!=null)
        {

            if(latlong.length==1)
            {
                GetLocationService.showLocationSettings(this);

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
*/
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
                      /* Intent ii=new Intent(CT_Questions.this, Daily_Audit.class);
                       ii.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                       startActivity(ii);*/
                           CT_Questions.this.finish();
                       }
                   })
                   .setNegativeButton("No", null)
                   .create()
                   .show();
       }
   }
    public void setVisitID()
    {
        sharedPreferences=getSharedPreferences(Login.USER_ID_LOGIN_PREFERENCES, Context.MODE_PRIVATE);
        // SharedPreferences.Editor editor=sharedPreferences.edit();
        String userIDEnterd=sharedPreferences.getString(Login.UserIdEntered,null);
        visit.setVisitId(userIDEnterd + "_" + visit.getDatetime());
        visit.setUserId(userIDEnterd);
    }

    public void onNext(View view) {
        String result = "";

        String allAns[] = new String[ansewers.length];
        for (int i = 0; i < ansewers.length; i++) {
            //  result = result + ansewers[i] + otherText[i] + "/";

            // allAns[i] = ansewers[i] + "," + otherText[i];
            allAns[i] = ansewers[i] + "" + otherText[i];

            result = result + allAns[i] + "/";

        }
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();

        //  visit.setCt(ansewers);
        visit.setCt(allAns);
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
                        //  ((Button) findViewById((UtilCT.getNoButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.black));

                        //  ((Button) findViewById((UtilCT.getYesButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.red));

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
                        if (currentButtonPressed == 0) {
                            enableNoCtButtons();
                        }
                        ((Button) findViewById((UtilCT.getYesButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.green));
                        ((Button) findViewById((UtilCT.getNoButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.black));

                        //for storing checked checkbox info=>CBI
                       /* if(checkedStates[currentButtonPressed]!=null)
                        {
                            for(int i=0;i<checkedStates[currentButtonPressed].length;i++)
                        {checkedStates[currentButtonPressed][i]=false;}
                        }
*/
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

                        //CBI
                      /*  if(checkedStates[currentButtonPressed]!=null)
                        {for(int i=0;i<checkedStates[currentButtonPressed].length;i++)
                        {checkedStates[currentButtonPressed][i]=false;}}*/

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
                        /*((Button) findViewById((UtilCT.getYesButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.black));

                        ((Button) findViewById((UtilCT.getNoButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.red));
*/
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
        String arrayH[]; //this will hold subQ in hindi just to display

        if(getButtonType()==1)
        {
            array = UtilCT.getYesSubQuestion();
            arrayE = UtilCT.getYesSubQuestion();//Every time option data will be stored from this because it will always be in english
            arrayH = UtilCT.getYesSubQuestionH();

        }
        else
        {
            array = UtilCT.getNoSubQuestion();
            arrayE = UtilCT.getNoSubQuestion();
            arrayH = UtilCT.getNoSubQuestionH();
        }

        if (Utility.getLanguage(CT_Questions.this) != null && Utility.getLanguage(CT_Questions.this).equals("Hindi")) {
            array = arrayH;
        }
        final String[] items=array[buttonPressed].split("-");
        itemsSubQ = arrayE[buttonPressed].split("-");

        final List<String> itemsSelected = new ArrayList();
        //CBI
       /* if(checkedStates[currentButtonPressed]==null)
        {checkedStates[currentButtonPressed]=new boolean[items.length];}*/

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Reasons");
        if (buttonPressed == 1 && buttonType == 1) {
            builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // itemsSelected.add(items[i]);
                    itemsSelected.add(itemsSubQ[i]);
                }
            });

        } else {
            builder.setMultiChoiceItems(items, null,
                    new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int selectedItemId,
                                            boolean isSelected) {
                            if (isSelected) {
                                // Toast.makeText(getBaseContext(),items[selectedItemId].toString()+"",Toast.LENGTH_SHORT).show();
                                //   itemsSelected.add(items[selectedItemId]);
                                itemsSelected.add(itemsSubQ[selectedItemId]);

                                //  checkedStates[currentButtonPressed][selectedItemId]=true;


                            } /*else if (itemsSelected.contains(items[selectedItemId])) {

                                itemsSelected.remove(items[selectedItemId]);

                                //  checkedStates[currentButtonPressed][selectedItemId]=false;
                            }*/ else if (itemsSelected.contains(itemsSubQ[selectedItemId])) {

                                itemsSelected.remove(itemsSubQ[selectedItemId]);
                            }


                        }
                    });
        }
        builder.setPositiveButton("Done!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                String oneAnswer;
                if (getButtonType() != 1 && buttonPressed == 0 && itemsSelected.size() != 0) {
                    disableNoCtButtons();
                }

                //     Home.printToast(checkedStates[currentButtonPressed][0]+" "+checkedStates[currentButtonPressed][1]+" "+checkedStates[currentButtonPressed][2],CT_Questions.this);

                if (!(itemsSelected.size() == 0)) {
                    if (getButtonType() == 1) {
                        oneAnswer = "yes";

                        if (buttonPressed == 1 && itemsSelected.contains(items[1])) {
                            ((Button) findViewById((UtilCT.getYesButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.green));
                        } else {
                            ((Button) findViewById((UtilCT.getYesButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.red));
                        }
                        ((Button) findViewById((UtilCT.getNoButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.black));

                    } else {
                        oneAnswer = "no";


                        ((Button) findViewById((UtilCT.getYesButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.black));
                        ((Button) findViewById((UtilCT.getNoButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.red));

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
                    Home.printToast("No option Selected", CT_Questions.this);
                           /* if (getButtonType() == 1) {
                                ((Button) findViewById((UtilCT.getYesButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.black));
                            } else {
                                ((Button) findViewById((UtilCT.getNoButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.black));
                            }*/
                    //   ansewers[buttonPressed] = buttonPressed + 1 + "";

                }


            }

        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                      /*  if (getButtonType() == 1) {
                            ((Button) findViewById((UtilCT.getYesButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.black));
                        } else {
                            ((Button) findViewById((UtilCT.getNoButtonIdsArray()[currentButtonPressed]))).setTextColor(getResources().getColor(R.color.black));
                        }*/

                      /*  for(int i=0;i<checkedStates[currentButtonPressed].length;i++)
                        {checkedStates[currentButtonPressed][i]=false;}*/

                        //  ansewers[buttonPressed] = buttonPressed + 1 + "";
                        dialog.dismiss();


                    }
                });
        dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();

    }

    private void enableNoCtButtons() {
        for (int i = 0; i < CtAffectedButtons.length; i++) {

            findViewById(CtAffectedButtons[i]).setEnabled(true);
            ((Button) findViewById(CtAffectedButtons[i])).setTextColor(getResources().getColor(R.color.black));

        }
        for (int j = 0; j < ansewers.length; j++) {
            if (j == 1 || j == 2 || j == 7 || j == 8 || j == 9) {
                ansewers[j] = "";
                otherText[j] = "";
            }

        }
    }

    private void disableNoCtButtons() {
        //Button bb=(Button)findViewById(R.id.btnCTQuestionYes2);

        for (int i = 0; i < CtAffectedButtons.length; i++) {

            findViewById(CtAffectedButtons[i]).setEnabled(false);
            ((Button) findViewById(CtAffectedButtons[i])).setTextColor(getResources().getColor(R.color.grey));


        }

        for (int j = 0; j < ansewers.length; j++) {
            if (j == 1 || j == 2 || j == 7 || j == 8 || j == 9) {
                ansewers[j] = "NA";
                otherText[j] = "";
            }
        }


    }


    public void showTextPopUp()
        {
            final AlertDialog dialog2;
            final EditText editView=new EditText(this);
            AlertDialog.Builder builder2=new AlertDialog.Builder(this);
            builder2.setTitle("Enter the reason");
            builder2.setView(editView);
            builder2.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //   otherText[currentButtonPressed]=(editView.getText().toString());
                   // Toast.makeText(getBaseContext(),getOtherText(),Toast.LENGTH_SHORT).show();

                }
            });


            dialog2=builder2.create();
            dialog2.setCancelable(false);
            dialog2.show();
            dialog2.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!editView.getText().toString().trim().equals("")) {
                        otherText[currentButtonPressed] = (editView.getText().toString());

                        dialog2.dismiss();
                    } else {
                        Utility.printToast("Enter some reason", CT_Questions.this);
                    }
                }
            });
        }



   /* public void setEnglishQuestion(String questionArray[],int textViewIds[])
    {

        for(int i=0;i<textViewIds.length;i++)
        {
            ((TextView)findViewById(textViewIds[i])).setText(questionArray[i]);
        }

    }*/
}
