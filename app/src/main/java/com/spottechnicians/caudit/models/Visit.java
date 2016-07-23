package com.spottechnicians.caudit.models;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

/**
 * Created by onestech on 04/06/16.
 */
public class Visit{


    Bitmap ctphoto1;
    Bitmap ctphoto2;
    Bitmap ctphoto3;
    Bitmap signature;
    HashMap<String, Bitmap> ctPicList;
    String[] hk, ct, srm;
    Bitmap hkphoto1;
    Bitmap hkphoto2;
    Bitmap hkphoto3;
    Bitmap hkphoto4;
    Bitmap hkphoto5;
    Bitmap hkphoto6;
    Bitmap hkphoto7;
    Bitmap hkphoto8;
    Bitmap hkphoto9;
    Bitmap hkphoto10;
    Bitmap hkphoto11;
    Bitmap hkphoto12;
    private String viewPhotos;
    private String visitId;
    private String atmId;
    private String userId;
    private String siteid;
    private String city;
    private String state;
    private String location;
    private String bankName;
    private String customerName;
    //private String date;
    //private String time;
    private String datetime;
    private String caretakeName;
    private String caretakerNumber;
    private String housekeeperName;
    private String housekeeperNumber;
    private String srmName;
    private String SrmNumber;
    private String latitude;
    private String longitude;


    public Visit(String visitId, String atmId, String userId, String siteid, String city, String state, String location, String bankName, String customerName, String datetime
            , String[] ct, String caretakeName, String caretakerNumber, String latitude, String longitude,
                 Bitmap ctphoto1, Bitmap ctphoto2, Bitmap ctphoto3) {


        this.visitId = visitId;
        this.atmId = atmId;
        this.userId = userId;
        this.siteid = siteid;
        this.city = city;
        this.state = state;
        this.location = location;
        this.bankName = bankName;
        this.customerName = customerName;
        // this.date = date;
        //this.time = time;
        this.datetime = datetime;
        this.ct = ct;
        this.caretakeName = caretakeName;
        this.caretakerNumber = caretakerNumber;
        this.latitude = latitude;
        this.longitude = longitude;
        this.ctphoto1 = ctphoto1;
        this.ctphoto2 = ctphoto2;
        this.ctphoto3 = ctphoto3;

    }

    public Visit(String visitId, String atmId, String userId, String location, String bankName, String customerName, String datetime, String caretakeName, String caretakerNumber, String latitude, String longitude) {


        this.visitId = visitId;
        this.atmId = atmId;
        this.userId = userId;
        this.location = location;
        this.bankName = bankName;
        this.customerName = customerName;
        this.ct = ct;
        this.caretakeName = caretakeName;
        this.caretakerNumber = caretakerNumber;
        this.latitude = latitude;
        this.longitude = longitude;
      /*  this.ctphoto1 = ctphoto1;
        this.ctphoto2 = ctphoto2;
        this.ctphoto3 = ctphoto3;
        this.signature = signature;*/
    }

    public Visit(String visitId, String atmId, String userId, String siteid, String city, String state, String location, String bankName, String customerName, String datetime
            , String[] hk, String housekeeperName, String housekeeperNumber, String latitude, String longitude,
                 Bitmap hkphoto1, Bitmap hkphoto2, Bitmap hkphoto3, Bitmap hkphoto4, Bitmap hkphoto5, Bitmap hkphoto6,
                 Bitmap hkphoto7, Bitmap hkphoto8, Bitmap hkphoto9, Bitmap hkphoto10, Bitmap hkphoto11)

    {
        this.visitId = visitId;
        this.atmId = atmId;
        this.userId = userId;
        this.siteid = siteid;
        this.city = city;
        this.state = state;
        this.location = location;
        this.bankName = bankName;
        this.customerName = customerName;
        this.datetime = datetime;
        this.hk = hk;
        this.housekeeperName = housekeeperName;
        this.housekeeperNumber = housekeeperNumber;
        this.latitude = latitude;
        this.longitude = longitude;
        this.hkphoto1 = hkphoto1;
        this.hkphoto2 = hkphoto2;
        this.hkphoto3 = hkphoto3;
        this.hkphoto4 = hkphoto4;
        this.hkphoto5 = hkphoto5;
        this.hkphoto6 = hkphoto6;
        this.hkphoto7 = hkphoto7;
        this.hkphoto8 = hkphoto8;
        this.hkphoto9 = hkphoto9;
        this.hkphoto10 = hkphoto10;
        this.hkphoto11 = hkphoto11;


    }

    public Visit(String visitId, String atmId, String userId, String siteid, String city, String state, String location,
                 String bankName, String customerName, String datetime, String[] ct, Bitmap ctphoto1, Bitmap ctphoto2, Bitmap ctphoto3,
                 String caretakeName, String caretakerNumber,
                 String[] hk, Bitmap hkphoto1, Bitmap hkphoto2, Bitmap hkphoto3, Bitmap hkphoto4, Bitmap hkphoto5,
                 Bitmap hkphoto6, Bitmap hkphoto7, Bitmap hkphoto8, Bitmap hkphoto9, Bitmap hkphoto10, Bitmap hkphoto11,
                 String housekeeperName, String housekeeperNumber,
                 String latitude, String longitude)

    {
        this.visitId = visitId;
        this.atmId = atmId;
        this.userId = userId;
        this.siteid = siteid;
        this.city = city;
        this.state = state;
        this.location = location;
        this.bankName = bankName;
        this.customerName = customerName;
        this.datetime = datetime;

        this.ct = ct;
        this.ctphoto1 = ctphoto1;
        this.ctphoto2 = ctphoto2;
        this.ctphoto3 = ctphoto3;
        this.caretakeName = caretakeName;
        this.caretakerNumber = caretakerNumber;

        this.hk = hk;
        this.hkphoto1 = hkphoto1;
        this.hkphoto2 = hkphoto2;
        this.hkphoto3 = hkphoto3;
        this.hkphoto4 = hkphoto4;
        this.hkphoto5 = hkphoto5;
        this.hkphoto6 = hkphoto6;
        this.hkphoto7 = hkphoto7;
        this.hkphoto8 = hkphoto8;
        this.hkphoto9 = hkphoto9;
        this.hkphoto10 = hkphoto10;
        this.hkphoto11 = hkphoto11;
        this.housekeeperName = housekeeperName;
        this.housekeeperNumber = housekeeperNumber;

        this.latitude = latitude;
        this.longitude = longitude;

    }
    public Visit() {
    }

    public Bitmap getHkphoto1() {
        return hkphoto1;
    }

    public Bitmap getHkphoto2() {
        return hkphoto2;
    }

    public Bitmap getHkphoto3() {
        return hkphoto3;
    }

    public Bitmap getHkphoto4() {
        return hkphoto4;
    }

    public Bitmap getHkphoto5() {
        return hkphoto5;
    }

    public Bitmap getHkphoto6() {
        return hkphoto6;
    }

    public Bitmap getHkphoto7() {
        return hkphoto7;
    }

    public Bitmap getHkphoto8() {
        return hkphoto8;
    }

    public Bitmap getHkphoto9() {
        return hkphoto9;
    }

    public Bitmap getHkphoto10() {
        return hkphoto10;
    }

    public Bitmap getHkphoto11() {
        return hkphoto11;
    }

    public Bitmap getHkphoto12() {
        return hkphoto12;
    }

    public String getSiteid() {
        return siteid;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public byte[] getCtphoto1ByteArray() {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ctphoto1.compress(Bitmap.CompressFormat.JPEG, 90, stream);
        return stream.toByteArray();

    }

    public byte[] getCtphoto2ByteArray() {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ctphoto2.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();

    }
    public byte[] getCtphoto3ByteArray() {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ctphoto3.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();

    }

    public byte[] getCtSignatureByteArray() {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        signature.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();

    }

    public String getCtPhoto1String()
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ctphoto1.compress(Bitmap.CompressFormat.JPEG, 90, stream);
        byte[] byteFormat = stream.toByteArray();

        String imgString = Base64.encodeToString(byteFormat, Base64.DEFAULT);

        return imgString;
    }
    public String getCtPhoto2String()
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ctphoto2.compress(Bitmap.CompressFormat.JPEG, 90, stream);
        byte[] byteFormat = stream.toByteArray();

        String imgString = Base64.encodeToString(byteFormat, Base64.DEFAULT);

        return imgString;
    }

    public String getCtPhoto3String()
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ctphoto3.compress(Bitmap.CompressFormat.JPEG, 90, stream);
        byte[] byteFormat = stream.toByteArray();

        String imgString = Base64.encodeToString(byteFormat, Base64.DEFAULT);

        return imgString;
    }

    public String getCtSignatureString()

    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        signature.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteFormat = stream.toByteArray();

        String imgString = Base64.encodeToString(byteFormat, Base64.DEFAULT);

        return imgString;
    }


    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
    public Bitmap getCtphoto1() {
        return ctphoto1;
    }

    public void setCtphoto1(Bitmap ctphoto1) {
        this.ctphoto1 = ctphoto1;
    }

    public Bitmap getCtphoto2() {
        return ctphoto2;
    }

    public void setCtphoto2(Bitmap ctphoto2) {
        this.ctphoto2 = ctphoto2;
    }

    public Bitmap getCtphoto3() {
        return ctphoto3;
    }

    public void setCtphoto3(Bitmap ctphoto3) {
        this.ctphoto3 = ctphoto3;
    }

    public Bitmap getSignature() {
        return signature;
    }

    public void setSignature(Bitmap signature) {
        this.signature = signature;
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

    public boolean checkHKComplete() {
        boolean status = true;
        for (int i = 0; i < ct.length; i++) {
            if (ct[i] == "") {
                status = false;
            }
        }
        return status;
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


    public String getCaretakeName() {
        return caretakeName;
    }

    public void setCaretakeName(String caretakeName) {
        this.caretakeName = caretakeName;
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




}
