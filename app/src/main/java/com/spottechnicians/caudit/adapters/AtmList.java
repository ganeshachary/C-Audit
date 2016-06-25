package com.spottechnicians.caudit.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.spottechnicians.caudit.R;
import com.spottechnicians.caudit.models.Atm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Ganesh on 6/2/2016.
 */
public class AtmList extends BaseAdapter implements Filterable{
    Context context;
    List<Atm> listOfAtms;
    List<Atm> listOfFilteredAtms;
    ValueFilter valueFilter;

  //  private  static LayoutInflater inflater=null;

     public AtmList(Context context, List<Atm> listOfAtms)
     {
         this.context=context;
         this.listOfAtms=listOfAtms;
         this.listOfFilteredAtms=listOfAtms;


     }
    @Override
    public int getCount() {
        return listOfAtms.size();
    }

    @Override
    public Object getItem(int position) {

        return position;
    }

    @Override
    public long getItemId(int position) {

        return listOfAtms.indexOf(getItem(position));
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View rootView=convertView;
        if(rootView==null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rootView = inflater.inflate(R.layout.atm_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.atmID = (TextView) rootView.findViewById(R.id.tvAtmId);
            viewHolder.bankName = (TextView) rootView.findViewById(R.id.tvBankName);
            viewHolder.cutomerName = (TextView) rootView.findViewById(R.id.tvCustomerName);
            viewHolder.address = (TextView) rootView.findViewById(R.id.tvAddress);
            viewHolder.city = (TextView) rootView.findViewById(R.id.tvCity);
            viewHolder.state = (TextView) rootView.findViewById(R.id.tvState);
            viewHolder.date = (TextView) rootView.findViewById(R.id.tvAuditedDate);
            viewHolder.days = (TextView) rootView.findViewById(R.id.tvAuditedDays);
            Log.d("listdata2", listOfAtms.get(position).getAtmId());

            rootView.setTag(viewHolder);
        }

            viewHolder=(ViewHolder)rootView.getTag();


            viewHolder.atmID.setText(listOfAtms.get(position).getAtmId());
            viewHolder.atmID.setTag(listOfAtms.get(position).getSiteId());
            viewHolder.bankName.setText(listOfAtms.get(position).getBankName());
            viewHolder.cutomerName.setText(listOfAtms.get(position).getCustomerName());
            viewHolder.address.setText(listOfAtms.get(position).getAddress());
            viewHolder.city.setText(listOfAtms.get(position).getCity());
            viewHolder.state.setText(listOfAtms.get(position).getState());
            viewHolder.date.setText(listOfAtms.get(position).getLastaudited());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        String TodayDateTime = sdf.format(c.getTime());
        Date d1 = null;
        Date d2 = null;

        try {
            d1 = format.parse(listOfAtms.get(position).getLastaudited());
            d2 = format.parse(TodayDateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long diff = d2.getTime() - d1.getTime();
        int diffInDays = (int) diff / (1000 * 60 * 60 * 24);

        viewHolder.days.setText(diffInDays + " days Before");
            return  rootView;



    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    static class ViewHolder
    {
        TextView atmID,bankName,cutomerName,address,city,state,date,days;

    }

    class ValueFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results=new FilterResults();
            if(constraint!=null && constraint.length()>0)
            {
                List<Atm> filterList=new ArrayList<Atm>();
                for(int i=0;i<listOfFilteredAtms.size();i++)
                {
                    if((listOfFilteredAtms.get(i).getAtmId().toLowerCase().contains(constraint.toString().toLowerCase()))||
                            (listOfFilteredAtms.get(i).getAddress().toLowerCase().contains(constraint.toString().toLowerCase()))||
                            (listOfFilteredAtms.get(i).getState().toLowerCase().contains(constraint.toString().toLowerCase()))
                            ||(listOfFilteredAtms.get(i).getCity().toLowerCase().contains(constraint.toString().toLowerCase()))
                            ||(listOfFilteredAtms.get(i).getBankName().toLowerCase().contains(constraint.toString().toLowerCase()))
                            ||(listOfFilteredAtms.get(i).getCustomerName().toLowerCase().contains(constraint.toString().toLowerCase())))
                    {
                        Atm atmObject=new Atm();
                        atmObject.setAtmId(listOfFilteredAtms.get(i).getAtmId());
                        atmObject.setSiteId(listOfFilteredAtms.get(i).getSiteId());
                        atmObject.setBankName(listOfFilteredAtms.get(i).getBankName());
                        atmObject.setCustomerName(listOfFilteredAtms.get(i).getCustomerName());
                        atmObject.setAddress(listOfFilteredAtms.get(i).getAddress());
                        atmObject.setState(listOfFilteredAtms.get(i).getState());
                        atmObject.setCity(listOfFilteredAtms.get(i).getCity());
                        atmObject.setLastaudited(listOfFilteredAtms.get(i).getLastaudited());
                        filterList.add(atmObject);

                    }
                }
                results.count=filterList.size();
                results.values=filterList;

            }
            else
            {
                results.count=listOfFilteredAtms.size();
                results.values=listOfFilteredAtms;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listOfAtms=(List<Atm>)results.values;
            notifyDataSetChanged();
        }
    }






}
