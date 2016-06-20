package com.spottechnicians.caudit.models;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

/**
 * Created by onestech on 18/06/16.
 */
public class VisitSingleton {



    private HashMap<String, Bitmap> PicList=new HashMap<>();
    private String[] hk, ct, srm;
    private  String viewPhotos;
    private  String visitId;
    private  String atmId;
    private  String userId;
    private  String siteid;
    private  String city;
    private  String state;
    private  String location;
    private  String bankName;
    private  String customerName;
    private  String date;
    private  String time;
    private  String caretakerName;
    private  String caretakerNumber;
    private  String housekeeperName;
    private  String housekeeperNumber;
    private  String srmName;
    private  String SrmNumber;
    private  String latitude;
    private  String longitude;



    /*

        private  static  VisitSingleton visitSingleton=new VisitSingleton();
        private VisitSingleton() {
        }
    */
    private  static  VisitSingleton visitSingleton=null;
    private VisitSingleton() {
    }

    public static VisitSingleton getInstance()
    {
        if(visitSingleton==null)
        {
            visitSingleton=new VisitSingleton();
        }
        return visitSingleton;
    }

    public boolean checkCTComplete()
    {
        boolean status=true;
        for(int i=0;i<ct.length;i++)
        {
            if(ct[i]=="") {
                status = false;
            }
        }
        return status;
    }


    public String getSiteId() {
        return siteid;
    }

    public void setSiteid(String siteid) {
        this.siteid = siteid;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public HashMap<String, Bitmap> getPicList() {
        return PicList;
    }
    public Bitmap getPicListByKey(String key) {
        return PicList.get(key);
    }

    public void setPicList(String key,Bitmap bitmap) {
        PicList.put(key,bitmap);
    }

    public byte[] getCtphotoByteArray(String key) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        getPicListByKey(key).compress(Bitmap.CompressFormat.JPEG, 80, stream);
        return stream.toByteArray();

    }

    public String[] getHk() {
        return hk;
    }

    public void setHk(String[] hk) {
        this.hk = hk;
    }

    public String[] getCt() {
        return ct;
    }

    public void setCt(String[] ct) {
        this.ct = ct;
    }

    public String[] getSrm() {
        return srm;
    }

    public void setSrm(String[] srm) {
        this.srm = srm;
    }

    public String getViewPhotos() {
        return viewPhotos;
    }

    public void setViewPhotos(String viewPhotos) {
        this.viewPhotos = viewPhotos;
    }

    public String getVisitId() {
        return visitId;
    }

    public void setVisitId(String visitId) {
        this.visitId = visitId;
    }

    public String getAtmId() {
        return atmId;
    }

    public void setAtmId(String atmId) {
        this.atmId = atmId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCaretakerName() {
        return caretakerName;
    }

    public void setCaretakerName(String caretakerName) {
        this.caretakerName = caretakerName;
    }

    public String getCaretakerNumber() {
        return caretakerNumber;
    }

    public void setCaretakerNumber(String caretakerNumber) {
        this.caretakerNumber = caretakerNumber;
    }

    public String getHousekeeperName() {
        return housekeeperName;
    }

    public void setHousekeeperName(String housekeeperName) {
        this.housekeeperName = housekeeperName;
    }

    public String getHousekeeperNumber() {
        return housekeeperNumber;
    }

    public void setHousekeeperNumber(String housekeeperNumber) {
        this.housekeeperNumber = housekeeperNumber;
    }

    public String getSrmName() {
        return srmName;
    }

    public void setSrmName(String srmName) {
        this.srmName = srmName;
    }

    public String getSrmNumber() {
        return SrmNumber;
    }

    public void setSrmNumber(String srmNumber) {
        SrmNumber = srmNumber;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public static VisitSingleton getVisitSingleton() {
        return visitSingleton;
    }

    public static void setVisitSingleton(VisitSingleton visitSingleton) {
        VisitSingleton.visitSingleton = visitSingleton;
    }
}
