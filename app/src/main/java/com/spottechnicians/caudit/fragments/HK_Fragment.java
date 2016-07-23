package com.spottechnicians.caudit.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.spottechnicians.caudit.DatabaseHandler.DbHelper;
import com.spottechnicians.caudit.ModuleHK.HKQuestions;
import com.spottechnicians.caudit.R;
import com.spottechnicians.caudit.adapters.AtmList;
import com.spottechnicians.caudit.models.Atm;
import com.spottechnicians.caudit.models.VisitSingleton;
import com.spottechnicians.caudit.utils.GetLocationService;
import com.spottechnicians.caudit.utils.Utility;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HK_Fragment extends Fragment {
    ListView listViewHK;
    EditText etSearchBarHK;
    List<Atm> listOfAtms;
    AtmList atmListAdapter;
    VisitSingleton visit;
    TextView tvHKAudit;

    public HK_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_hk_, container, false);
        listViewHK=(ListView)rootView.findViewById(R.id.listviewHK);
        etSearchBarHK=(EditText)rootView.findViewById(R.id.etSearchBarHK);
        tvHKAudit = (TextView) rootView.findViewById(R.id.tvHkAudit);
        listOfAtms=new ArrayList<>();

        visit = VisitSingleton.getInstance();



       // listOfAtms=createDummyList();
        listOfAtms=getAtmsTypeHK();
        Utility.setAuditedRecord(listOfAtms, etSearchBarHK, tvHKAudit);
        atmListAdapter = new AtmList(getContext(), listOfAtms);
        listViewHK.setAdapter(atmListAdapter);
        etSearchBarHK.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                atmListAdapter.getFilter().filter(etSearchBarHK.getText());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        listViewHK.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                String stringAtmId = ((TextView) view.findViewById(R.id.tvAtmId)).getText().toString();
                String stringSiteId = view.findViewById(R.id.tvAtmId).getTag().toString();
                String location = ((TextView) view.findViewById(R.id.tvAddress)).getText().toString();
                String city = ((TextView) view.findViewById(R.id.tvCity)).getText().toString();
                String state = ((TextView) view.findViewById(R.id.tvState)).getText().toString();
                String bankname = ((TextView) view.findViewById(R.id.tvBankName)).getText().toString();
                String customer = ((TextView) view.findViewById(R.id.tvCustomerName)).getText().toString();

                visit.setSiteid(stringSiteId);
                visit.setAtmId(stringAtmId);
                visit.setCity(city);
                visit.setState(state);
                visit.setLocation(location);
                visit.setBankName(bankname);
                visit.setCustomerName(customer);

                if (GetLocationService.isLocationOn(getActivity())) {
                    //starting service again
                    getContext().startService(new Intent(getActivity(), GetLocationService.class));

                    Toast.makeText(getActivity(), "Latitude: " + GetLocationService.LATITUDE_FROM_SERVICE + ", Longitude: " +
                            GetLocationService.LONGITUDE_FROM_SERVICE + "On HK Ques", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getContext(), HKQuestions.class);
                    startActivity(intent);
                } else {
                    GetLocationService.showLocationSettings(getActivity());
                }

            }
        });



        return rootView;
    }

    public List<Atm> createDummyList()
    {
        List<Atm> listOfAtms1=new ArrayList<>();
        Atm atmObject;
        atmObject=new Atm();
        String atmid="ab89797";
        atmObject.setAtmId(atmid);
        atmObject.setBankName("yes");
        atmObject.setCustomerName("deibold");
        atmObject.setAddress("sion west sies");
        atmObject.setState("maharasthra");
        atmObject.setCity("mumbai");
        Log.d("listdata",atmid);
        listOfAtms1.add(atmObject);
        atmObject=new Atm();
        String atmid2="udg89797";
        atmObject.setAtmId(atmid2);
        atmObject.setBankName("icici");
        atmObject.setCustomerName("hitachi");
        atmObject.setAddress("matunga east");
        atmObject.setState("maharasthra");
        atmObject.setCity("pune");
        Log.d("listdata",atmid);
        listOfAtms1.add(atmObject);
        return listOfAtms1;
    }

    public List<Atm> getAtmsTypeHK()
    {
        List<Atm> listOfAtms1=new DbHelper(getContext()).fetchAtmsByType("hk");
        return listOfAtms1;
    }

}
