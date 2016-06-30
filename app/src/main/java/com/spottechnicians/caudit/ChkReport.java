package com.spottechnicians.caudit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.spottechnicians.caudit.DatabaseHandler.DbHelper;
import com.spottechnicians.caudit.models.Visit;
import com.spottechnicians.caudit.utils.LocationFetch;

import java.util.ArrayList;

public class ChkReport extends AppCompatActivity {

    DbHelper dbHelper;

    Visit Selectedvisit;

    ArrayList<Visit> visitArrayList;

    ArrayList<String> visitIds;

    ImageView iv1, iv2, iv3, iv4, iv5, iv6, iv7, iv8, iv9, iv10, iv11, iv12, iv13, iv14;

    LocationFetch locationFetch;

    String latlong[];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chk_report);

        Spinner visitId=(Spinner)findViewById(R.id.spVisitId);
        //  ListView listView=(ListView)findViewById(R.id.visitIdListChk);
        final TextView reportView=(TextView)findViewById(R.id.tvCTReportText);

        iv1=(ImageView)findViewById(R.id.ivReportCTPhoto1);

        iv2=(ImageView)findViewById(R.id.ivReportCTPhoto2);

        iv3=(ImageView)findViewById(R.id.ivReportCTPhoto3);

        iv4 = (ImageView) findViewById(R.id.ivReportCTHkPhoto1);
        iv5 = (ImageView) findViewById(R.id.ivReportCTHkPhoto2);
        iv6 = (ImageView) findViewById(R.id.ivReportCTHkPhoto3);
        iv7 = (ImageView) findViewById(R.id.ivReportCTHkPhoto4);
        iv8 = (ImageView) findViewById(R.id.ivReportCTHkPhoto5);
        iv9 = (ImageView) findViewById(R.id.ivReportCTHkPhoto6);
        iv10 = (ImageView) findViewById(R.id.ivReportCTHkPhoto7);
        iv11 = (ImageView) findViewById(R.id.ivReportCTHkPhoto8);
        iv12 = (ImageView) findViewById(R.id.ivReportCTHkPhoto9);
        iv13 = (ImageView) findViewById(R.id.ivReportCTHkPhoto10);
        iv14 = (ImageView) findViewById(R.id.ivReportCTHkPhoto11);


        dbHelper = new DbHelper(this);

        visitIds=new ArrayList<>();


        visitArrayList = dbHelper.fetchCtHkReport();

        for(int i=0;i<visitArrayList.size();i++)
        {
            if(visitArrayList!=null) {
                visitIds.add(visitArrayList.get(i).getVisitId().toString());
            }



        }


        //  listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,visitIds));

        visitId.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,visitIds));


      /*  listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String VisitId=((TextView)view).getText().toString();

                Toast.makeText(ChkReport.this,VisitId,Toast.LENGTH_LONG).show();

            } });*/

        visitId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String VisitId=((TextView)view).getText().toString();

                //   Selectedvisit=dbHelper.getVisitFromId(i+1);

                Selectedvisit = dbHelper.getCTHkReportFromVisitId(VisitId);


                reportView.setText(Selectedvisit.getBankName() + "\n" + Selectedvisit.getDatetime() + "\n " + Selectedvisit.getCt()[0]
                                            +"\n"+Selectedvisit.getCt()[1]+"Lat: "+Selectedvisit.getLatitude()
                                    +" Long: "+Selectedvisit.getLongitude());



                iv1.setImageBitmap(Selectedvisit.getCtphoto1());

                iv2.setImageBitmap(Selectedvisit.getCtphoto2());

                iv3.setImageBitmap(Selectedvisit.getCtphoto3());

                //   iv4.setImageBitmap(BitmapFactory.decodeByteArray(Selectedvisit.getCtSignatureByteArray(),0,Selectedvisit.getCtSignatureByteArray().length));

                iv4.setImageBitmap(Selectedvisit.getHkphoto1());
                iv5.setImageBitmap(Selectedvisit.getHkphoto2());
                iv6.setImageBitmap(Selectedvisit.getHkphoto3());
                iv7.setImageBitmap(Selectedvisit.getHkphoto4());
                iv8.setImageBitmap(Selectedvisit.getHkphoto5());
                iv9.setImageBitmap(Selectedvisit.getHkphoto6());
                iv10.setImageBitmap(Selectedvisit.getHkphoto7());
                iv11.setImageBitmap(Selectedvisit.getHkphoto8());
                iv12.setImageBitmap(Selectedvisit.getHkphoto9());
                iv13.setImageBitmap(Selectedvisit.getHkphoto10());
                iv14.setImageBitmap(Selectedvisit.getHkphoto11());


                Toast.makeText(ChkReport.this,VisitId,Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });





    }

    public  void start(View v)
    {

        startActivity(new Intent(this,Login.class));


    }
}
