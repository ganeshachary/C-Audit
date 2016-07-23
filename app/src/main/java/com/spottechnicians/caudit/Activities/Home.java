package com.spottechnicians.caudit.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.spottechnicians.caudit.DatabaseHandler.DbHelper;
import com.spottechnicians.caudit.R;
import com.spottechnicians.caudit.models.Visit;
import com.spottechnicians.caudit.utils.GetLocationService;
import com.spottechnicians.caudit.utils.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class Home extends AppCompatActivity {
    TextView daily_report_tv,other_report_tv,audited_tv,unaudited_tv,sync_tv;

    DbHelper dbHelper;

    ArrayList<Visit> visitt;
    Menu menu;

    public static void printToast(String s, Context c) {
        Toast.makeText(c, s, Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        dbHelper=new DbHelper(this);
        notifySync();

        Utility.printToast("Test Toast", this);

        //   printHk();
        //   printCTHK();
        //After uploading to github

      //  Toast.makeText(this,s,Toast.LENGTH_LONG).show();

        Toast.makeText(this, "Latitude: " + GetLocationService.LATITUDE_FROM_SERVICE + ", Longitude: " +
                GetLocationService.LONGITUDE_FROM_SERVICE, Toast.LENGTH_LONG).show();


    }

    private void printCTHK() {//to check retreive data from CtHkReport table

        //  visitt=new ArrayList<>();
        ArrayList<Visit> visitCtHkReport = dbHelper.fetchCtHkReport();


        if (visitCtHkReport.size() > 0) {
            for (int i = 0; i < visitCtHkReport.size(); i++) {
                String anss = visitCtHkReport.get(i).getHk()[0] + " " + visitCtHkReport.get(i).getHk()[1] + " " + visitCtHkReport.get(i).getHk()[2] + " "
                        + visitCtHkReport.get(i).getHk()[3] + " " + visitCtHkReport.get(i).getHk()[4] + " " + visitCtHkReport.get(i).getHk()[5] + " " + visitCtHkReport.get(i).getHk()[6] + " "
                        + visitCtHkReport.get(i).getHk()[7] + " " + visitCtHkReport.get(i).getHk()[8] + " " + visitCtHkReport.get(i).getHk()[10] + " "
                        + visitCtHkReport.get(i).getHk()[11] + " " + visitCtHkReport.get(i).getHk()[12] + " " + visitCtHkReport.get(i).getHk()[13] + " "
                        + visitCtHkReport.get(i).getHk()[14] + " " + visitCtHkReport.get(i).getHk()[15] + " " + visitCtHkReport.get(i).getHk()[16];

                String ctAns = "";
                for (int j = 0; j < visitCtHkReport.get(i).getCt().length; j++) {
                    ctAns = ctAns + " " + visitCtHkReport.get(i).getCt()[j];
                }

                String aa = "Atm id: " + visitCtHkReport.get(i).getAtmId() + "Date time: " + visitCtHkReport.get(i).getDatetime() +
                        "Ct Answers " + ctAns +
                        "CareTaker name: " + visitCtHkReport.get(i).getCaretakeName() +
                        "CT no: " + visitCtHkReport.get(i).getCaretakerNumber() +
                        "Hk Answers " + anss +
                        "HouseKeeper name: " + visitCtHkReport.get(i).getHousekeeperName() +
                        "HK no: " + visitCtHkReport.get(i).getHousekeeperNumber() +
                        "lat long " + visitCtHkReport.get(i).getLatitude() + "," + visitCtHkReport.get(i).getLongitude();


                Log.e("HK report man", aa);
            }

        } else {
            Toast.makeText(this, "No report to sync", Toast.LENGTH_LONG).show();
        }


    }


    public void notifySync()
    {
       int count= dbHelper.getUnsyncedRecords();
        count = count + dbHelper.getUnsyncedRecordsFromHK();
        ((TextView)findViewById(R.id.tvNoficaiton)).setText(count+"");

    }

    public void aduditClicked(View view)
    {
        //Intent intent=new Intent(this,auited_tab.class);
        //startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=this.getMenuInflater();
        menuInflater .inflate(R.menu.menu_main,menu);
        this.menu = menu;
        updateOptionmenu();
        return super.onCreateOptionsMenu(menu);
    }

    private void updateOptionmenu() {

        MenuItem menuItem = menu.findItem(R.id.lang);
        if (Utility.getLanguage(this) != null && Utility.getLanguage(this).equals("Hindi")) {
            changeToHindi();
            menuItem.setTitle("Eng");
        }

    }


    public void openDailyVisitReport(View view)
    {
        Intent intent=new Intent(this,Daily_Audit.class);
        startActivity(intent);
    }

    public void openOtherReport(View v) {


        Toast toast = Toast.makeText(this, "Coming soon", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM, 0, 50);
        toast.show();
        //sartActivity(new Intent(this, OfficialDetails.class));
    }

    @Override
    public void onBackPressed() {
        //  super.onBackPressed();
        Intent intent = new Intent(this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().equals("Hindi"))
        {
            changeToHindi();
            item.setTitle("Eng");
        } else if (item.getTitle().equals("Eng")) {
            changeToEnglish();
            item.setTitle("Hindi");
        }

        return super.onOptionsItemSelected(item);
    }

    private void changeToEnglish() {
        daily_report_tv = (TextView) findViewById(R.id.daily_report_tv);
        daily_report_tv.setText(R.string.daily_visit);
        other_report_tv = (TextView) findViewById(R.id.other_report_tv);
        other_report_tv.setText(R.string.srm_report);
         /*   audited_tv = (TextView) findViewById(R.id.audited_tv);
            audited_tv.setText(R.string.audited);
            unaudited_tv = (TextView) findViewById(R.id.unaudited_tv);
            unaudited_tv.setText(R.string.unaudited);*/
        sync_tv = (TextView) findViewById(R.id.sycn_tv);
        sync_tv.setText(R.string.sync);

        Utility.setEng(this);

    }

    private void changeToHindi() {
        daily_report_tv = (TextView) findViewById(R.id.daily_report_tv);
        daily_report_tv.setText(R.string.daily_visit_hindi);
        other_report_tv = (TextView) findViewById(R.id.other_report_tv);
        other_report_tv.setText(R.string.srm_report_hindi);
          /*  audited_tv=(TextView)findViewById(R.id.audited_tv);
            audited_tv.setText(R.string.audited_hindi);
            unaudited_tv=(TextView)findViewById(R.id.unaudited_tv);
            unaudited_tv.setText(R.string.unaudited_hindi);*/
        sync_tv = (TextView) findViewById(R.id.sycn_tv);
        sync_tv.setText(R.string.sync_hindi);

        Utility.setHindi(this);
    }


    public int networkStatus() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getTypeName().equalsIgnoreCase("MOBILE")) {
                return 2;
            } else if (networkInfo.getTypeName().equalsIgnoreCase("WIFI")) {
                return 3;

            }

            return 1;
        } else {

            return 0;

        }
    }


    public void syncReport(View view)
    {
        if(networkStatus()!=0)
        {
            ArrayList<Visit> visitReports=dbHelper.fetchCTReport();
            if(visitReports.size()>0)
            {

                Visit[] visiting = new Visit[visitReports.size()];
                for (int j = 0; j < visitReports.size(); j++)
                {
                    visiting[j] = visitReports.get(j);
                }

                Log.e("CheckMan", "length is " + visiting.length);
                new UploadToServer2().execute(visiting);
               /* for(int i=0;i<visitReports.size();i++)
                {
                    UploadToServer uploadToServer=new UploadToServer(visitReports.get(i),i);
                    uploadToServer.execute();


                    Log.v("visitId",visitReports.get(i).getVisitId());
                    Log.v("visitId",visitReports.get(i).getCtPhoto1String());

                }
                */



            }
            else
            {
                Toast.makeText(this,"No report to sync",Toast.LENGTH_LONG).show();
            }

        }
        else {
            Toast.makeText(this,"Turn on the mobile data or Wifi",Toast.LENGTH_LONG).show();

        }


    }

    private void printHk() {//to check data retreive from HkReport Table

        visitt = new ArrayList<>();
        // ArrayList<Visit> visitHkReport=dbHelper.fetchHKReport();

        visitt = dbHelper.fetchHKReport();

        if (visitt.size() > 0) {
            for (int i = 0; i < visitt.size(); i++) {
                String anss = visitt.get(i).getHk()[0] + " " + visitt.get(i).getHk()[1] + " " + visitt.get(i).getHk()[2] + " "
                        + visitt.get(i).getHk()[3] + " " + visitt.get(i).getHk()[4] + " " + visitt.get(i).getHk()[5] + " " + visitt.get(i).getHk()[6] + " "
                        + visitt.get(i).getHk()[7] + " " + visitt.get(i).getHk()[8] + " " + visitt.get(i).getHk()[10] + " "
                        + visitt.get(i).getHk()[11] + " " + visitt.get(i).getHk()[12] + " " + visitt.get(i).getHk()[13] + " "
                        + visitt.get(i).getHk()[14] + " " + visitt.get(i).getHk()[15] + " " + visitt.get(i).getHk()[16];


                String aa = "Atm id: " + visitt.get(i).getAtmId() + "Date time: " + visitt.get(i).getDatetime() +
                        "HouseKeeper name: " + visitt.get(i).getHousekeeperName() +
                        "HK no: " + visitt.get(i).getHousekeeperNumber() +
                        "lat long " + visitt.get(i).getLatitude() + "," + visitt.get(i).getLongitude() +
                        "Answers " + anss;


                Log.e("HK report man", aa);
            }

        } else {
            Toast.makeText(this, "No report to sync", Toast.LENGTH_LONG).show();
        }

    }

    public class UploadToServer2 extends AsyncTask<Visit, Integer, String> {

        String visitid;
        int totalCount;
        private ProgressDialog pDialog, dDialog;
        private String jsonStream;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Home.this);
        }

        @Override
        protected String doInBackground(Visit... params) {
            String upload_url = "http://www.cleartask.in/caudit_weblink/WebServices/SaveCTVisitData.aspx";


            totalCount = params.length;
            //String upload_url = Resources.getSystem().getString(R.string.ct_webservice_link);
            for (int i = 0; i < params.length; i++) {

                Log.e("CheckMan", "length is " + params.length);
                JSONObject jsonObject;
                HttpURLConnection httpURLConnection = null;
                publishProgress(i + 1);
                URL url;
                try {
                    url = new URL(upload_url);
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream os = httpURLConnection.getOutputStream();
                    visitid = params[i].getVisitId();
                    Log.v("visitId", visitid);

                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    String data = URLEncoder.encode("visit_id", "UTF-8") + "=" + URLEncoder.encode(visitid, "UTF-8") + "&"
                            + URLEncoder.encode("atm_id", "UTF-8") + "=" + URLEncoder.encode(params[i].getAtmId(), "UTF-8") + "&"
                            + URLEncoder.encode("login_id", "UTF-8") + "=" + URLEncoder.encode(params[i].getAtmId(), "UTF-8") + "&"
                            + URLEncoder.encode("date_of_capture", "UTF-8") + "=" + URLEncoder.encode(params[i].getDatetime(), "UTF-8") + "&"
                            + URLEncoder.encode("caretaker_name", "UTF-8") + "=" + URLEncoder.encode(params[i].getCaretakeName(), "UTF-8") + "&"
                            + URLEncoder.encode("caretaker_number", "UTF-8") + "=" + URLEncoder.encode(params[i].getCaretakerNumber(), "UTF-8") + "&"

                            + URLEncoder.encode("caretaker_img", "UTF-8") + "=" + URLEncoder.encode(params[i].getCtPhoto1String(), "UTF-8") + "&"
                            + URLEncoder.encode("front_signage_img", "UTF-8") + "=" + URLEncoder.encode(params[i].getCtPhoto2String(), "UTF-8") + "&"
                            + URLEncoder.encode("registers_img", "UTF-8") + "=" + URLEncoder.encode(params[i].getCtPhoto3String(), "UTF-8") + "&"

                            + URLEncoder.encode("q1", "UTF-8") + "=" + URLEncoder.encode(params[i].getCt()[0], "UTF-8") + "&"
                            + URLEncoder.encode("q2", "UTF-8") + "=" + URLEncoder.encode(params[i].getCt()[1], "UTF-8") + "&"
                            + URLEncoder.encode("q3", "UTF-8") + "=" + URLEncoder.encode(params[i].getCt()[2], "UTF-8") + "&"
                            + URLEncoder.encode("q4", "UTF-8") + "=" + URLEncoder.encode(params[i].getCt()[3], "UTF-8") + "&"
                            + URLEncoder.encode("q5", "UTF-8") + "=" + URLEncoder.encode(params[i].getCt()[4], "UTF-8") + "&"
                            + URLEncoder.encode("q6", "UTF-8") + "=" + URLEncoder.encode(params[i].getCt()[5], "UTF-8") + "&"
                            + URLEncoder.encode("q7", "UTF-8") + "=" + URLEncoder.encode(params[i].getCt()[6], "UTF-8") + "&"
                            + URLEncoder.encode("q8", "UTF-8") + "=" + URLEncoder.encode(params[i].getCt()[7], "UTF-8") + "&"
                            + URLEncoder.encode("q9", "UTF-8") + "=" + URLEncoder.encode(params[i].getCt()[8], "UTF-8") + "&"
                            + URLEncoder.encode("q10", "UTF-8") + "=" + URLEncoder.encode(params[i].getCt()[9], "UTF-8") + "&"
                            + URLEncoder.encode("q11", "UTF-8") + "=" + URLEncoder.encode(params[i].getCt()[10], "UTF-8") + "&"
                            + URLEncoder.encode("q12", "UTF-8") + "=" + URLEncoder.encode(params[i].getCt()[11], "UTF-8");

                    Log.e("images", params[i].getCtPhoto1String());
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    if (bufferedWriter != null) {
                        bufferedWriter.close();
                    }
                    if (os != null) {
                        os.close();
                    }

                    InputStream io = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(io));
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((jsonStream = bufferedReader.readLine()) != null) {
                        stringBuilder.append(jsonStream);
                    }
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    if (io != null) {
                        io.close();
                    }
                    jsonStream = stringBuilder.toString().trim();
                    Log.e("jsondata", jsonStream);
                    // return jsonStream;
                    if (jsonStream != null) {
                        try {
                            jsonObject = new JSONObject(jsonStream);
                            if (!jsonObject.getBoolean("error")) {
                                dbHelper.deleteSyncedData(visitid);
                                //     Toast.makeText(Home.this, "loaded", Toast.LENGTH_LONG).show();
                                Log.e("jsondata", "Loaded");
                                //  return "loaded";
                            } else {
                                //Toast.makeText(Home.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                return jsonObject.getString("message");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else if (jsonStream == null) {
                        //   Toast.makeText(Home.this, "no json data return", Toast.LENGTH_LONG).show();
                        return "no json data return";
                    }


                    Log.e("sri", "Execution ended.");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    Log.e("checkMan", "returning from 1st null");
                    return "Some error occured,Try again!";
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("checkMan", "returning from 12st null");
                    return "Some error occured,Try again!!";
                }

            }
            return "Successfully synced";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            //   int round=Integer.parseInt(String.valueOf(values));
            //   pDialog=new ProgressDialog(Home.this);
            Log.e("in onProgressUpdate: ", "values " + values[0]);
            pDialog.setTitle("Uploading report " + values[0] + " of " + totalCount + " to server");
            pDialog.setMessage("Please wait...");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
            pDialog.show();
            notifySync();


        }
        //  setProgress();


        @Override
        protected void onPostExecute(String result) {


            notifySync();
            //  pDialog.dismiss();

            Toast.makeText(Home.this, "inpost " + result, Toast.LENGTH_LONG).show();
            Log.e("in onPostExecute: ", "The string returned is " + result);
            pDialog.dismiss();

           /* final AlertDialog.Builder alertDialog= new AlertDialog.Builder(Home.this);

            alertDialog.setTitle("Result");
            alertDialog.setMessage(result);
            alertDialog.setCancelable(false);
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.create().dismiss();
                }
            });
           alertDialog.show();*/

            new AlertDialog.Builder(Home.this)
                    .setMessage(result).setNeutralButton("OK", null).show();


        }
    }

    public class UploadToServer extends AsyncTask<Void, Void, String> {
        Visit visit;
        String visitid;
        int round = 0;
        private ProgressDialog pDialog;
        private String jsonStream;

        UploadToServer(Visit visit,int round)
        {
            this.visit=visit;
            this.round=round;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog=new ProgressDialog(Home.this);
            pDialog.setTitle("Loading report "+round+ " to server");
            pDialog.setMessage("Please wait...");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            String upload_url = "http://www.cleartask.in/caudit_weblink/WebServices/SaveCTVisitData.aspx";
            //String upload_url = Resources.getSystem().getString(R.string.ct_webservice_link);
            HttpURLConnection httpURLConnection=null;
            URL url;
            try {
                url=new URL(upload_url);
                httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os=httpURLConnection.getOutputStream();
                visitid = visit.getVisitId();
                Log.v("visitId",visitid);

                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                String data = URLEncoder.encode("visit_id", "UTF-8") + "=" + URLEncoder.encode(visitid, "UTF-8") + "&"
                        + URLEncoder.encode("atm_id", "UTF-8") + "=" + URLEncoder.encode(visit.getAtmId(), "UTF-8") + "&"
                        + URLEncoder.encode("login_id", "UTF-8") + "=" + URLEncoder.encode(visit.getAtmId(), "UTF-8") + "&"
                        + URLEncoder.encode("date_of_capture", "UTF-8") + "=" + URLEncoder.encode(visit.getDatetime(), "UTF-8") + "&"
                        + URLEncoder.encode("caretaker_name", "UTF-8") + "=" + URLEncoder.encode(visit.getCaretakeName(), "UTF-8") + "&"
                        + URLEncoder.encode("caretaker_number", "UTF-8") + "=" + URLEncoder.encode(visit.getCaretakerNumber(), "UTF-8") + "&"

                        + URLEncoder.encode("caretaker_img", "UTF-8") + "=" + URLEncoder.encode(visit.getCtPhoto1String(), "UTF-8") + "&"
                        + URLEncoder.encode("front_signage_img", "UTF-8") + "=" + URLEncoder.encode(visit.getCtPhoto2String(), "UTF-8") + "&"
                        + URLEncoder.encode("registers_img", "UTF-8") + "=" + URLEncoder.encode(visit.getCtPhoto3String(), "UTF-8") + "&"

                        + URLEncoder.encode("q1", "UTF-8") + "=" + URLEncoder.encode(visit.getCt()[0], "UTF-8") + "&"
                        + URLEncoder.encode("q2", "UTF-8") + "=" + URLEncoder.encode(visit.getCt()[1], "UTF-8") + "&"
                        + URLEncoder.encode("q3", "UTF-8") + "=" + URLEncoder.encode(visit.getCt()[2], "UTF-8") + "&"
                        + URLEncoder.encode("q4", "UTF-8") + "=" + URLEncoder.encode(visit.getCt()[3], "UTF-8") + "&"
                        + URLEncoder.encode("q5", "UTF-8") + "=" + URLEncoder.encode(visit.getCt()[4], "UTF-8") + "&"
                        + URLEncoder.encode("q6", "UTF-8") + "=" + URLEncoder.encode(visit.getCt()[5], "UTF-8") + "&"
                        + URLEncoder.encode("q7", "UTF-8") + "=" + URLEncoder.encode(visit.getCt()[6], "UTF-8") + "&"
                        + URLEncoder.encode("q8", "UTF-8") + "=" + URLEncoder.encode(visit.getCt()[7], "UTF-8") + "&"
                        + URLEncoder.encode("q9", "UTF-8") + "=" + URLEncoder.encode(visit.getCt()[8], "UTF-8") + "&"
                        + URLEncoder.encode("q10", "UTF-8") + "=" + URLEncoder.encode(visit.getCt()[9], "UTF-8") + "&"
                        + URLEncoder.encode("q11", "UTF-8") + "=" + URLEncoder.encode(visit.getCt()[10], "UTF-8") + "&"
                        + URLEncoder.encode("q12", "UTF-8") + "=" + URLEncoder.encode(visit.getCt()[11], "UTF-8");

                Log.e("images", visit.getCtPhoto1String());
                bufferedWriter.write(data);
                bufferedWriter.flush();
                if(bufferedWriter!=null)
                {
                    bufferedWriter.close();
                }
                if(os!=null)
                {
                    os.close();
                }

                InputStream io=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(io));
                StringBuilder stringBuilder=new StringBuilder();
                while((jsonStream=bufferedReader.readLine())!=null)
                {
                    stringBuilder.append(jsonStream);
                }
                if(bufferedReader!=null)
                {
                    bufferedReader.close();
                }
                if(io!=null)
                {
                    io.close();
                }
                jsonStream=stringBuilder.toString().trim();
                Log.v("jsondata",jsonStream);
                return jsonStream;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;        }

        @Override
        protected void onPostExecute(String result) {

            pDialog.dismiss();
            JSONObject jsonObject;
            if (result != null) {
                try {
                    jsonObject = new JSONObject(result);
                    if (!jsonObject.getBoolean("error")) {
                        dbHelper.deleteSyncedData(visitid);
                        Toast.makeText(Home.this, "loaded", Toast.LENGTH_LONG).show();
                        notifySync();
                    } else {
                        Toast.makeText(Home.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (result == null) {
                Toast.makeText(Home.this, "no json data return", Toast.LENGTH_LONG).show();
            }


            Log.w("sri", "Execution ended.");
        }
    }
}
