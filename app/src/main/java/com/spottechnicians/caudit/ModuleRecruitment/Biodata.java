package com.spottechnicians.caudit.ModuleRecruitment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.spottechnicians.caudit.R;

public class Biodata extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biodata);
    }

    public void onNextBioData(View view) {

        startActivity(new Intent(this, Declaration.class));

    }
}
