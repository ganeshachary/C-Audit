package com.spottechnicians.caudit.utils;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.spottechnicians.caudit.DatabaseHandler.DbHelper;
import com.spottechnicians.caudit.Home;
import com.spottechnicians.caudit.models.Atm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by onestech on 27/06/16.
 */
public class DummyData {

    public void addDummyData(DbHelper dbHelper, Activity activity) {
        List<Atm> atmList;
        Atm atmObjects;
        atmList = new ArrayList<>();

        atmObjects = new Atm();
        atmObjects.setAtmId("ATM101");
        atmObjects.setAddress("sion west opp to sies college");
        atmObjects.setBankName("ICICI");
        atmObjects.setCustomerName("HITACHI");
        atmObjects.setCity("MUMBAI");
        atmObjects.setState("MAHARASHTRA");
        atmObjects.setSiteId("s101");
        atmObjects.setType("ct");
        atmList.add(atmObjects);

        atmObjects = new Atm();
        atmObjects.setAtmId("ATM106");
        atmObjects.setAddress("dadar west opp to sies college");
        atmObjects.setBankName("AXIS");
        atmObjects.setCustomerName("DIEBOLD");
        atmObjects.setCity("PUNE");
        atmObjects.setState("MAHARASHTRA");
        atmObjects.setSiteId("s102");
        atmObjects.setType("ct");

        atmList.add(atmObjects);

        atmObjects = new Atm();
        atmObjects.setAtmId("ATM102");
        atmObjects.setAddress("dadar west opp to sies college");
        atmObjects.setBankName("AXIS");
        atmObjects.setCustomerName("DIEBOLD");
        atmObjects.setCity("PUNE");
        atmObjects.setState("MAHARASHTRA");
        atmObjects.setSiteId("s102");
        atmObjects.setType("hk");

        atmList.add(atmObjects);

        atmObjects = new Atm();
        atmObjects.setAtmId("ATM103");
        atmObjects.setAddress("sion west opp to sies college");
        atmObjects.setBankName("ICICI");
        atmObjects.setCustomerName("HITACHI");
        atmObjects.setCity("MUMBAI");
        atmObjects.setState("MAHARASHTRA");
        atmObjects.setSiteId("s101");
        atmObjects.setType("hk");

        atmList.add(atmObjects);

        dbHelper = new DbHelper(activity);
        if (dbHelper.insertATM(atmList)) {
            Toast.makeText(activity, "data inserted in db", Toast.LENGTH_SHORT).show();
            logIn(activity);
        }


    }

    public void logIn(Activity activity) {

        //start the home activity
        Intent intent = new Intent(activity, Home.class);
        activity.startActivity(intent);
    }

}