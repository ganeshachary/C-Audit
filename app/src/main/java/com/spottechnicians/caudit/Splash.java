package com.spottechnicians.caudit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Splash extends AppCompatActivity {
    private static int SPLASH_TIME_OUT=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //kick off the async task
        Thread backgroundMainApplication = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                    Intent intent=new Intent(Splash.this, Login.class);
                    startActivity(intent);
                    finish();
                } catch (Exception ex) {
                }
            }
        };
        backgroundMainApplication.start();
    }
}
