package com.spottechnicians.caudit.ModuleHK;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.spottechnicians.caudit.R;
import com.spottechnicians.caudit.models.VisitSingleton;
import com.spottechnicians.caudit.utils.UtilCT;

public class HKQuestions extends AppCompatActivity {

    VisitSingleton visit;

    String ansewers[] = {"", "", "", "", "", "", "", "", "", "", "", ""};
    String otherText[] = {"", "", "", "", "", "", "", "", "", "", "", ""};
    int currentButtonPressed;
    int buttonType;
    String questionEnglishArray[];
    String questionHindiArray[];
    int textViewIds[];

    //this method can be used to create the array of Id's just by passing one of the ID and length
    public static int[] getTextViewIdsFromName(String textIdName, int length, Context context) {

        String textIds[] = new String[length];
        int[] textIdsInt = new int[length];

        for (int i = 0; i < length; i++) {
            textIds[i] = textIdName + (i + 1);

            textIdsInt[i] = context.getResources().getIdentifier(textIds[i].toString(), "id", context.getPackageName());
        }

        return textIdsInt;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hkquestions);

        questionEnglishArray = getResources().getStringArray(R.array.HK_Questions);

        textViewIds = getTextViewIdsFromName("tvHKQuestion", 17, this);

        UtilCT.setEnglishQuestionUsingActivity(questionEnglishArray, textViewIds, this);

    }

    public void onNext(View v) {
        startActivity(new Intent(this, PhotoOfHK.class));


    }

}



