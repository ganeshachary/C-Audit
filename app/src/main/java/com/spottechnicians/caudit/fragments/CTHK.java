package com.spottechnicians.caudit.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.spottechnicians.caudit.DatabaseHandler.DbHelper;
import com.spottechnicians.caudit.ModuleCT.CT_Questions;
import com.spottechnicians.caudit.R;
import com.spottechnicians.caudit.adapters.AtmList;
import com.spottechnicians.caudit.models.Atm;
import com.spottechnicians.caudit.models.VisitSingleton;
import com.spottechnicians.caudit.utils.GetLocationService;

import java.util.ArrayList;
import java.util.List;

public class CTHK extends Fragment {

    ListView listViewCTHK;
    EditText etSearchBarCTHK;
    List<Atm> listOfAtms;
    AtmList atmListAdapter;
    VisitSingleton visit;

    public CTHK() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_cthk, container, false);
        listViewCTHK = (ListView) rootView.findViewById(R.id.listviewCTHK);
        etSearchBarCTHK = (EditText) rootView.findViewById(R.id.etSearchBarCTHK);
        listOfAtms = new ArrayList<>();

        visit = VisitSingleton.getInstance();
        listOfAtms = getAtmsTypeHK();         //just populating Hk type ATM's for test will be replaced by CtHk later..
        atmListAdapter = new AtmList(getContext(), listOfAtms);
        listViewCTHK.setAdapter(atmListAdapter);


        listViewCTHK.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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

                visit.setSiteType("CTHK");


                if (GetLocationService.isLocationOn(getActivity())) {
                    //starting servic again
                    getContext().startService(new Intent(getActivity(), GetLocationService.class));
                    Toast.makeText(getActivity(), "Latitude: " + GetLocationService.LATITUDE_FROM_SERVICE + ", Longitude: " +
                            GetLocationService.LONGITUDE_FROM_SERVICE, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getContext(), CT_Questions.class);
                    startActivity(intent);
                } else {
                    GetLocationService.showLocationSettings(getActivity());
                }


            }
        });
        etSearchBarCTHK.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                atmListAdapter.getFilter().filter(etSearchBarCTHK.getText());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        return rootView;
    }

    public List<Atm> getAtmsTypeCT() {
        List<Atm> listOfAtms1 = new DbHelper(getContext()).fetchAtmsByType("ct");
        return listOfAtms1;
    }

    public List<Atm> getAtmsTypeHK() {
        List<Atm> listOfAtms1 = new DbHelper(getContext()).fetchAtmsByType("hk");
        return listOfAtms1;
    }


}
