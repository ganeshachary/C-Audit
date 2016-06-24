package com.spottechnicians.caudit.ModuleSRM;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.spottechnicians.caudit.ModuleHK.HKQuestions;
import com.spottechnicians.caudit.R;
import com.spottechnicians.caudit.utils.UtilCT;

public class SRMQuestions extends AppCompatActivity {

    String questionEnglishArray[];
    int textViewIds[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_srmquestions);

        questionEnglishArray = getResources().getStringArray(R.array.SRM_Questions);


        textViewIds = HKQuestions.getTextViewIdsFromName("tvSRMQuestion", 17, this);


        UtilCT.setEnglishQuestionUsingActivity(questionEnglishArray, textViewIds, this);

    }

    public void onNext(View v) {
        startActivity(new Intent(this, PhotoOfSRM.class));

    }

}
