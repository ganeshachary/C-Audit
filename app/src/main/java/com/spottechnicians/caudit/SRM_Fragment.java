package com.spottechnicians.caudit;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.spottechnicians.caudit.DatabaseHandler.DbHelper;
import com.spottechnicians.caudit.adapters.AtmList;
import com.spottechnicians.caudit.models.Atm;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SRM_Fragment extends Fragment {

    ListView listViewSRM;
    EditText etSearchBarSRM;
    List<Atm> listOfAtms;
    AtmList atmListAdapter;
    public SRM_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_srm_, container, false);
        listViewSRM=(ListView)rootView.findViewById(R.id.listviewSRM);
        etSearchBarSRM=(EditText)rootView.findViewById(R.id.etSearchBarSRM);
        listOfAtms=new ArrayList<>();


    listOfAtms=createDummyList();
    atmListAdapter=new AtmList(getContext(),listOfAtms);
    listViewSRM.setAdapter(atmListAdapter);
    etSearchBarSRM.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            atmListAdapter.getFilter().filter(etSearchBarSRM.getText());
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

    public List<Atm> getAtmsTypeCT()
    {
        List<Atm> listOfAtms1=new DbHelper(getContext()).fetchAtmsByType("ct/hk");
        return listOfAtms1;
    }


}
