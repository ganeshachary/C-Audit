package com.spottechnicians.caudit.ModuleRecruitment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.spottechnicians.caudit.R;
import com.spottechnicians.caudit.utils.UtilCT;

public class OfficialDetails extends AppCompatActivity {

    String questionEnglishArray[];
    int textViewIds[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_official_details);

        questionEnglishArray = getResources().getStringArray(R.array.Recruitment);
        // textViewIds = HKQuestions.getTextViewIdsFromName("recruitment", 7, this);
        UtilCT.setEnglishQuestionUsingActivity(questionEnglishArray, textViewIds, this);

    }

    public void onNextOfficialDetails(View view) {
        startActivity(new Intent(this, Address.class));
    }
}
