package com.spottechnicians.caudit;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.spottechnicians.caudit.DatabaseHandler.DbHelper;
import com.spottechnicians.caudit.ModuleRecruitment.OfficialDetails;
import com.spottechnicians.caudit.models.Visit;
import com.spottechnicians.caudit.utils.GetLocationService;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        dbHelper=new DbHelper(this);
        notifySync();

        //After uploading to github

      //  Toast.makeText(this,s,Toast.LENGTH_LONG).show();

        Toast.makeText(this, "Latitude: " + GetLocationService.LATITUDE_FROM_SERVICE + ", Longitude: " +
                GetLocationService.LONGITUDE_FROM_SERVICE, Toast.LENGTH_LONG).show();


    }


    public void notifySync()
    {
       int count= dbHelper.getUnsyncedRecords();
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
        return super.onCreateOptionsMenu(menu);
    }


    public void openDailyVisitReport(View view)
    {
        Intent intent=new Intent(this,Daily_Audit.class);
        startActivity(intent);
    }

    public void openOtherReport(View v) {
        startActivity(new Intent(this, OfficialDetails.class));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getTitle().equals("Eng"))
        {
            daily_report_tv=(TextView)findViewById(R.id.daily_report_tv);
            daily_report_tv.setText(R.string.daily_visit_hindi);
            other_report_tv=(TextView)findViewById(R.id.other_report_tv);
            other_report_tv.setText(R.string.srm_report_hindi);
            audited_tv=(TextView)findViewById(R.id.audited_tv);
            audited_tv.setText(R.string.audited_hindi);
            unaudited_tv=(TextView)findViewById(R.id.unaudited_tv);
            unaudited_tv.setText(R.string.unaudited_hindi);
            sync_tv=(TextView)findViewById(R.id.sycn_tv);
            sync_tv.setText(R.string.sync_hindi);
            item.setTitle("Hindi");




        }
        else if(item.getTitle().equals("Hindi")) {
            daily_report_tv = (TextView) findViewById(R.id.daily_report_tv);
            daily_report_tv.setText(R.string.daily_visit);
            other_report_tv = (TextView) findViewById(R.id.other_report_tv);
            other_report_tv.setText(R.string.srm_report);
            audited_tv = (TextView) findViewById(R.id.audited_tv);
            audited_tv.setText(R.string.audited);
            unaudited_tv = (TextView) findViewById(R.id.unaudited_tv);
            unaudited_tv.setText(R.string.unaudited);
            sync_tv = (TextView) findViewById(R.id.sycn_tv);
            sync_tv.setText(R.string.sync);
            item.setTitle("Eng");

        }

        return super.onOptionsItemSelected(item);
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
                for(int i=0;i<visitReports.size();i++)
                {
                    UploadToServer uploadToServer=new UploadToServer(visitReports.get(i),i);
                    uploadToServer.execute();
                    Log.v("visitId",visitReports.get(i).getVisitId());
                    Log.v("visitId",visitReports.get(i).getCtPhoto1String());

                }
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


    public class UploadToServer extends AsyncTask<Void,Void,String> {
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
