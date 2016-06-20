package com.spottechnicians.caudit;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
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

    ImageView iv1,iv2,iv3,iv4;

    LocationFetch locationFetch;

    String latlong[];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chk_report);

        Spinner visitId=(Spinner)findViewById(R.id.spVisitId);
        ListView listView=(ListView)findViewById(R.id.visitIdListChk);
        final TextView reportView=(TextView)findViewById(R.id.tvCTReportText);

        iv1=(ImageView)findViewById(R.id.ivReportCTPhoto1);

        iv2=(ImageView)findViewById(R.id.ivReportCTPhoto2);

        iv3=(ImageView)findViewById(R.id.ivReportCTPhoto3);

        iv4=(ImageView)findViewById(R.id.ivReportCTSignature);

        dbHelper=new DbHelper(this);

        locationFetch=new LocationFetch(this);

        //latlong=new String[2];

        //latlong=locationFetch.getLatitudeLongitude();

        if(latlong!=null)
        {

            if(latlong.length==1)
            {Toast.makeText(this,latlong[0],Toast.LENGTH_LONG).show();}
            else
            {
             Toast.makeText(this,latlong[0]+" "+latlong[1]+" "+latlong[2],Toast.LENGTH_LONG).show();
            }
        }
        else {Toast.makeText(this,"Check Settings",Toast.LENGTH_LONG).show();}

        visitIds=new ArrayList<>();


        visitArrayList=dbHelper.fetchCTReport();

        for(int i=0;i<visitArrayList.size();i++)
        {
            if(visitArrayList!=null) {
                visitIds.add(visitArrayList.get(i).getVisitId().toString());
            }



        }


        listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,visitIds));

        visitId.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,visitIds));


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String VisitId=((TextView)view).getText().toString();

                Toast.makeText(ChkReport.this,VisitId,Toast.LENGTH_LONG).show();

            } });

        visitId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String VisitId=((TextView)view).getText().toString();

                Selectedvisit=dbHelper.getVisitFromId(i+1);



                reportView.setText(Selectedvisit.getBankName()+"\n"+Selectedvisit.getDate()+" "+Selectedvisit.getTime()+"\n "+Selectedvisit.getCt()[0]
                                            +"\n"+Selectedvisit.getCt()[1]+"Lat: "+Selectedvisit.getLatitude()
                                    +" Long: "+Selectedvisit.getLongitude());



                iv1.setImageBitmap(Selectedvisit.getCtphoto1());

                iv2.setImageBitmap(Selectedvisit.getCtphoto2());

                iv3.setImageBitmap(Selectedvisit.getCtphoto3());

                iv4.setImageBitmap(BitmapFactory.decodeByteArray(Selectedvisit.getCtSignatureByteArray(),0,Selectedvisit.getCtSignatureByteArray().length));








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
