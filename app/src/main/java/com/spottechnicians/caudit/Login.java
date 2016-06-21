package com.spottechnicians.caudit;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.spottechnicians.caudit.DatabaseHandler.DbHelper;
import com.spottechnicians.caudit.ModuleCT.CT_Questions;
import com.spottechnicians.caudit.models.Atm;
import com.spottechnicians.caudit.utils.GetLocationService;

import org.json.JSONArray;
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
import java.util.List;

public class Login extends AppCompatActivity {
    public static final String USER_ID_LOGIN_PREFERENCES = "UserIdLoginPref";
    public static final String UserIdEntered = "UserId";
    public static final String PasswordEntered = "Password";
    public static SharedPreferences sharedPreferences;
    List<Atm> atmList;
    EditText etPassword, etUserid;
    DbHelper dbHelper;
    String password,userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUserid=(EditText)findViewById(R.id.etUserid);
        etPassword=(EditText)findViewById(R.id.etpassword);
        setUsernamePassword();



    }

    public void validateLogin(View view)
    {



        userid=etUserid.getText().toString();
        password=etPassword.getText().toString();
        if (!checkOfflineLogin())
        {
            storeCredenditials();

            if (networkStatus() != 0 && GetLocationService.isLocationOn(this))
            {

                startService(new Intent(this, GetLocationService.class));
                ValidateLoginDeatails validateLoginDeatails=new ValidateLoginDeatails();
                validateLoginDeatails.execute(userid,password);


            } else if (!GetLocationService.isLocationOn(this))
            {

                CT_Questions.showLocationSettings(this);

            } else {
                Toast.makeText(this, "Turn on the mobile data or wifi", Toast.LENGTH_LONG).show();
            }

        } else
        {
            if (GetLocationService.isLocationOn(this)) {
                startService(new Intent(this, GetLocationService.class));
                logIn();
                Toast.makeText(this, "Latitude: " + GetLocationService.LATITUDE_FROM_SERVICE + ", Longitude: " +
                        GetLocationService.LONGITUDE_FROM_SERVICE, Toast.LENGTH_LONG).show();

            } else {
                CT_Questions.showLocationSettings(this);
            }


        }






    }


    public int networkStatus()
    {
        ConnectivityManager cm= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo=cm.getActiveNetworkInfo();

        if(networkInfo!=null&&networkInfo.isConnected())
        {
            if(networkInfo.getTypeName().equalsIgnoreCase("MOBILE"))
            {
                return 2;
            }

            else if(networkInfo.getTypeName().equalsIgnoreCase("WIFI"))
            {
                return 3;

            }

           return 1;
        }
        else {

            return 0;

        }


    }
    public void setUsernamePassword()
    {
        sharedPreferences=getSharedPreferences(USER_ID_LOGIN_PREFERENCES,MODE_PRIVATE);
        String storedUserid=sharedPreferences.getString(UserIdEntered,null);
        String storedPassword=sharedPreferences.getString(PasswordEntered,null);
        if(storedUserid!=null||storedPassword!=null)
        {
            etUserid.setText(storedUserid);
            etPassword.setText(storedPassword);
        }
    }

    public boolean checkOfflineLogin()
    {
        boolean status=true;
        sharedPreferences=getSharedPreferences(USER_ID_LOGIN_PREFERENCES,MODE_PRIVATE);
        String storedUserid=sharedPreferences.getString(UserIdEntered,null);
        String storedPassword=sharedPreferences.getString(PasswordEntered,null);
        if(storedUserid==null||storedPassword==null)
        {
           return false;
        }
        else
        {
            if(!storedUserid.equals(userid) || !storedPassword.equals(password))
            {
                Log.v("login",storedUserid);
                Log.v("login",storedPassword);
                Log.v("login",userid);
                Log.v("login",password);

                Toast.makeText(this,"username or password is wrong",Toast.LENGTH_SHORT).show();
            }

        }
        return status;

    }


    public void storeCredenditials()
    {
        sharedPreferences=getSharedPreferences(USER_ID_LOGIN_PREFERENCES,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(UserIdEntered,userid);
        editor.putString(PasswordEntered,password);
        editor.commit();
    }

    public void logIn()
    {

        //start the home activity
        Intent intent=new Intent(Login.this,Home.class);
        startActivity(intent);
    }





    public void jsonParse(String jsonString)
    {
        Atm atmObjects;
        atmList=new ArrayList<>();
        Boolean errorStatus;
        String message;

        try {
            JSONObject jsonObject=new JSONObject(jsonString);
            errorStatus=jsonObject.getBoolean("error");
            message=jsonObject.getString("message");
            if(!errorStatus)
            {
                JSONArray jsonArray=jsonObject.getJSONArray("atms");
                for(int i=0;i<jsonArray.length();i++)
                {
                    JSONObject jsonAtm=jsonArray.getJSONObject(i);
                    atmObjects=new Atm();
                    atmObjects.setAtmId(jsonAtm.getString("atmid"));
                    atmObjects.setAddress(jsonAtm.getString("location"));
                    atmObjects.setBankName(jsonAtm.getString("bankname"));
                    atmObjects.setCustomerName(jsonAtm.getString("customername"));
                    atmObjects.setCity(jsonAtm.getString("city"));
                    atmObjects.setState(jsonAtm.getString("state"));
                    atmObjects.setSiteId(jsonAtm.getString("siteid"));
                    atmObjects.setType(jsonAtm.getString("sitetype"));

                    atmList.add(atmObjects);
                }
                dbHelper=new DbHelper(this);
                if(dbHelper.insertATM(atmList))
                {
                    Toast.makeText(getBaseContext(),"data inserted in db",Toast.LENGTH_SHORT).show();
                    logIn();
                }
                else
                {
                    Toast.makeText(getBaseContext(),"error in insertion",Toast.LENGTH_SHORT).show();
                }

            }
            else
            {
                Toast.makeText(getBaseContext(),message.toString(),Toast.LENGTH_SHORT).show();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    class ValidateLoginDeatails extends AsyncTask<String,Void,String>
    {
        String localUserid,localPassword,jsonStream;
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog=new ProgressDialog(Login.this);
            pDialog.setTitle("Fetching data from .NET Webservice...");
            pDialog.setMessage("Please wait...");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {

            localUserid=params[0];
            localPassword=params[1];
            String loginURL="http://www.cleartask.in/caudit2/webservicedatabase.aspx";

            HttpURLConnection httpURLConnection=null;
            URL url;
            try {
                url=new URL(loginURL);
                httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os=httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                String data= URLEncoder.encode("username", "UTF-8")+"="+URLEncoder.encode(localUserid,"UTF-8")+ "&"
                        +URLEncoder.encode("password", "UTF-8")+"="+URLEncoder.encode(localPassword,"UTF-8");
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

            return null;
        }

        @Override
        protected void onPostExecute(String jsonString) {
            pDialog.dismiss();

            if(jsonString==null||jsonString=="")
            {
               Toast.makeText(getBaseContext(),"Network call failed",Toast.LENGTH_SHORT).show();
            }
            else
            {
               jsonParse(jsonString);
            }


        }
    }
}
