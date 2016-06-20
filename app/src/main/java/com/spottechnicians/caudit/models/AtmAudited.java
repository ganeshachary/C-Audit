package com.spottechnicians.caudit.models;

/**
 * Created by onestech on 20/06/16.
 */
public class AtmAudited{
    String atmid;
    String date;
    String type;
    public AtmAudited(String atmid,String date,String type)
    {
        this.atmid=atmid;
        this.date=date;
        this.type=type;
    }
}

