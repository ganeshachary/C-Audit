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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CT_Fragment extends Fragment {
    ListView listViewCT;
    AtmList atmListAdapter;
    List<Atm> listOfAtms;
    EditText etSearchBar;
    TextView tvCTAudit;
    VisitSingleton visit;
    public CT_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView=inflater.inflate(R.layout.fragment_ct_, container, false);
        listViewCT=(ListView)rootView.findViewById(R.id.listviewCT);

        listViewCT.setEmptyView(rootView.findViewById(R.id.tvEmpty));

        etSearchBar=(EditText)rootView.findViewById(R.id.etSearchBarCT);

        tvCTAudit = (TextView) rootView.findViewById(R.id.tvCtAudit);
        visit=VisitSingleton.getInstance();
        listOfAtms=new ArrayList<>();


        // listOfAtms=createDummyList();
        listOfAtms=getAtmsTypeCT();
        if (listOfAtms.size() == 0) {
            etSearchBar.setVisibility(View.GONE);
            tvCTAudit.setVisibility(View.GONE);
        } else {
            int counter = 0;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String TodayDateTime = sdf.format(c.getTime());
            Date d1 = null;
            Date d2 = null;


            for (int i = 0; i < listOfAtms.size(); i++) {
                if (listOfAtms.get(i).getLastaudited() != null && listOfAtms.get(i).getLastaudited() != "not audited") {
                    try {
                        d1 = format.parse(listOfAtms.get(i).getLastaudited());
                        d2 = format.parse(TodayDateTime);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    long diff = d2.getTime() - d1.getTime();
                    int diffInDays = (int) diff / (1000 * 60 * 60 * 24);

                    if (diffInDays >= 0 && diffInDays < 31) {
                        counter++;
                    }
                }
            }
            tvCTAudit.setText("Audited: " + counter + " Out of " + listOfAtms.size());
        }
        atmListAdapter=new AtmList(getContext(),listOfAtms);
        listViewCT.setAdapter(atmListAdapter);
        listViewCT.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String stringAtmId = ((TextView) view.findViewById(R.id.tvAtmId)).getText().toString();
                String stringSiteId = view.findViewById(R.id.tvAtmId).getTag().toString();
                String location=((TextView) view.findViewById(R.id.tvAddress)).getText().toString();
                String city=((TextView) view.findViewById(R.id.tvCity)).getText().toString();
                String state=((TextView) view.findViewById(R.id.tvState)).getText().toString();
                String bankname=((TextView) view.findViewById(R.id.tvBankName)).getText().toString();
                String customer=((TextView) view.findViewById(R.id.tvCustomerName)).getText().toString();

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

                    Toast.makeText(getActivity(), "new Started  Latitude: " + GetLocationService.LATITUDE_FROM_SERVICE + ", Longitude: " +
                            GetLocationService.LONGITUDE_FROM_SERVICE, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getContext(), CT_Questions.class);
                    startActivity(intent);
                } else {
                    GetLocationService.showLocationSettings(getActivity());
                }



            }
        });
        etSearchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    atmListAdapter.getFilter().filter(etSearchBar.getText());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return rootView;
    }

    public List<Atm> createDummyList()
    {
        List<Atm> listOfAtms1=new ArrayList<>();
       /* Atm atmObject;
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
        listOfAtms1.add(atmObject);  */
        return listOfAtms1;
    }

    public List<Atm> getAtmsTypeCT()
    {
       List<Atm> listOfAtms1=new DbHelper(getContext()).fetchAtmsByType("ct");
        return listOfAtms1;
    }

}
