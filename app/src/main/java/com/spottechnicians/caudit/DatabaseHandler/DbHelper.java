package com.spottechnicians.caudit.DatabaseHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.spottechnicians.caudit.models.Atm;
import com.spottechnicians.caudit.models.AtmAudited;
import com.spottechnicians.caudit.models.Visit;
import com.spottechnicians.caudit.models.VisitSingleton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ganesh on 6/1/2016.
 */
public class DbHelper extends SQLiteOpenHelper
{

    //type of service

    public static final String TABLE_ATM="atm";
    public static final String COLUMN_ID="id";
    public static final String COLUMN_ID_ATM="id";
    public static final String COLUMN_SITE_ID_ATM="site_id";
    public static final String COLUMN_ATM_ID_ATM="atm_id";
    public static final String COLUMN_BANK_NAME_ATM="bank_name";
    public static final String COLUMN_CUSTOMER_NAME_ATM="customer_name";
    public static final String COLUMN_ADDRESS_ATM="address";
    public static final String COLUMN_CITY_ATM="city";
    public static final String COLUMN_STATE_ATM="state";
    public static final String COLUMN_TYPE_ATM="type";
//    for CTQuestions
    public static final String TABLE_CT_REPORT="CtReport";
    public static final String COLUMN_VISIT_ID="visit_id";
    public static final String COLUMN_ATM_ID="atm_id";
    public static final String COLUMN_USER_ID="user_id";
    public static final String COLUMN_SITE_ID="site_id";
    public static final String COLUMN_CITY="city";
    public static final String COLUMN_STATE="state";
    public static final String COLUMN_LOCATION="location";
    public static final String COLUMN_BANK_NAME="bank_name";
    public static final String COLUMN_CUSTOMER_NAME="customer_name";
    public static final String COLUMN_DATE_TIME_OF_VIST = "date_time_of_visit";
    public static final String COLUMN_DATE_OF_VISIT="date_of_visit";
    public static final String COLUMN_TIME_OF_VISIT="time_of_visit";
    public static final String COLUMN_SYNC_STATUS="sync_status";
    public static final String TABLE_ATM_AUDITED = "atm_audited";
    public static final String COLUMN_AUDITED_DATE = "audited_date";
    public static final String COLUMN_AUDITED_ATM_ID = "audited_atm_id";
    public static final String COLUMN_AUDITED_ATM_TYPE = "audited_type";
    public static final String COLUMN_CTQ1 = "CTQ1";
    public static final String COLUMN_CTQ2 = "CTQ2";
    public static final String COLUMN_CTQ3 = "CTQ3";




    // table to maintain audit records
    public static final String COLUMN_CTQ4 = "CTQ4";
    public static final String COLUMN_CTQ5 = "CTQ5";
    public static final String COLUMN_CTQ6 = "CTQ6";
    public static final String COLUMN_CTQ7 = "CTQ7";
/*
    public static final String COLUMN_CTQ1="Is CT present at site";
    public static final String COLUMN_CTQ2="Is CT wearing ID card";
    public static final String COLUMN_CTQ3="Is CT wearing proper uniform";
    public static final String COLUMN_CTQ4="CT telephone working";
    public static final String COLUMN_CTQ5="Is CT chair available at site";
    public static final String COLUMN_CTQ6="Is CT working for more than 12hrs at stretch";
    public static final String COLUMN_CTQ7="All mandatory registers and Logbook are maintained";
    public static final String COLUMN_CTQ8="Does CT know where to deposit cash and cheque";
    public static final String COLUMN_CTQ9="Does CT politely help customer with use of ATM";
    public static final String COLUMN_CTQ10="Does CT ensure only one person per ATM machine";
    public static final String COLUMN_CTQ11="Important numbers are displayed in the ATM";
    public static final String COLUMN_CTQ12="Is the ATM down due to any major problem";*/
public static final String COLUMN_CTQ8 = "CTQ8";
    public static final String COLUMN_CTQ9 = "CTQ9";
    public static final String COLUMN_CTQ10 = "CTQ10";
    public static final String COLUMN_CTQ11 = "CTQ11";
    public static final String COLUMN_CTQ12 = "CTQ12";
    public static final String COLUMN_CT_NAME="caretaker_name";
    public static final String COLUMN_CT_NO="caretaker_number";
    public static final String COLUMN_LATITUDE="latitude";
    public static final String COLUMN_LONGITUDE="longitude";
    public static final String COLUMN_CT_PHOTO1="CT_Photo1";
    public static final String COLUMN_CT_PHOTO2="CT_Photo2";
    public static final String COLUMN_CT_PHOTO3="CT_Photo3";
    public static final String COLUMN_CT_SIGNATURE="CT_Signature";
    public static final String TABLE_HK_REPORT = "HKreport";
    public static final String COLUMN_HKQ1 = "HKQ1";
    public static final String COLUMN_HKQ2 = "HKQ2";
    public static final String COLUMN_HKQ3 = "HKQ3";
    public static final String COLUMN_HKQ4 = "HKQ4";
    public static final String COLUMN_HKQ5 = "HKQ5";
    public static final String COLUMN_HKQ6 = "HKQ6";
    public static final String COLUMN_HKQ7 = "HKQ7";
    public static final String COLUMN_HKQ8 = "HKQ8";
    public static final String COLUMN_HKQ9 = "HKQ9";
    public static final String COLUMN_HKQ10 = "HKQ10";
    public static final String COLUMN_HKQ11 = "HKQ11";
    public static final String COLUMN_HKQ12 = "HKQ12";
    public static final String COLUMN_HKQ13 = "HKQ13";
    public static final String COLUMN_HKQ14 = "HKQ14";
    public static final String COLUMN_HKQ15 = "HKQ15";
    public static final String COLUMN_HKQ16 = "HKQ16";
    public static final String COLUMN_HKQ17 = "HKQ17";
    public static final String COLUMN_HK_NAME = "housekeeper_name";
    public static final String COLUMN_HK_NO = "houseleeper_number";
    public static final String COLUMN_HK_PHOTO1 = "HK_Photo1";
    public static final String COLUMN_HK_PHOTO2 = "HK_Photo2";
    public static final String COLUMN_HK_PHOTO3 = "HK_Photo3";
    public static final String COLUMN_HK_PHOTO4 = "HK_Photo4";
    public static final String COLUMN_HK_PHOTO5 = "HK_Photo5";
    public static final String COLUMN_HK_PHOTO6 = "HK_Photo6";
    public static final String COLUMN_HK_PHOTO7 = "HK_Photo7";
    public static final String COLUMN_HK_PHOTO8 = "HK_Photo8";
    public static final String COLUMN_HK_PHOTO9 = "HK_Photo9";
    public static final String COLUMN_HK_PHOTO10 = "HK_Photo10";
    public static final String COLUMN_HK_PHOTO11 = "HK_Photo11";
    public static final String TABLE_CTHK_REPORT = "CtHkReport";
    private static final String TYPE_CT = "ct";
    private static final String TYPE_HK = "hk";
    private static final String TYPE_SRM = "srm";
    private static final String TYPE_CT_HK = "ct_hk";
    private static final String TYPE_ALL = "all";
    private static final String DB_NAME = "CAudit";
    private static final int DB_VERSION = 3;
    Visit visit;
    Visit visitt;
    Visit CtHkVisit;


    public DbHelper(Context context) {

        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        try {
            String createDb = "CREATE TABLE " + TABLE_ATM + "(" + COLUMN_ID_ATM + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                    + COLUMN_SITE_ID_ATM + " TEXT ,"
                    + COLUMN_ATM_ID_ATM + " TEXT ,"
                    + COLUMN_BANK_NAME_ATM + " TEXT ,"
                    + COLUMN_CUSTOMER_NAME_ATM + " TEXT ,"
                    + COLUMN_ADDRESS_ATM + " TEXT ,"
                    + COLUMN_CITY_ATM + " TEXT ,"
                    + COLUMN_STATE_ATM + " TEXT ,"
                    + COLUMN_TYPE_ATM + " TEXT)";

            String createCTReport = "CREATE TABLE " + TABLE_CT_REPORT + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                    + COLUMN_VISIT_ID + " TEXT ,"
                    + COLUMN_ATM_ID + " TEXT ,"
                    + COLUMN_USER_ID + " TEXT ,"
                    + COLUMN_SITE_ID + " TEXT ,"
                    + COLUMN_CITY + " TEXT ,"
                    + COLUMN_STATE + " TEXT ,"
                    + COLUMN_LOCATION + " TEXT ,"
                    + COLUMN_BANK_NAME + " TEXT ,"
                    + COLUMN_CUSTOMER_NAME + " TEXT ,"
                    + COLUMN_DATE_TIME_OF_VIST + " DATETIME ,"
                    + COLUMN_CTQ1 + " TEXT ," + COLUMN_CTQ2 + " TEXT ," + COLUMN_CTQ3 + " TEXT ,"
                    + COLUMN_CTQ4 + " TEXT ," + COLUMN_CTQ5 + " TEXT ," + COLUMN_CTQ6 + " TEXT ,"
                    + COLUMN_CTQ7 + " TEXT ," + COLUMN_CTQ8 + " TEXT ," + COLUMN_CTQ9 + " TEXT ,"
                    + COLUMN_CTQ10 + " TEXT ," + COLUMN_CTQ11 + " TEXT ," + COLUMN_CTQ12 + " TEXT ,"
                    + COLUMN_CT_NAME + " TEXT ,"
                    + COLUMN_CT_NO + " TEXT ,"
                    + COLUMN_LATITUDE + " TEXT ,"
                    + COLUMN_LONGITUDE + " TEXT ,"
                    + COLUMN_CT_PHOTO1 + " BLOB ," + COLUMN_CT_PHOTO2 + " BLOB ," + COLUMN_CT_PHOTO3 + " BLOB ,"
                    + COLUMN_SYNC_STATUS + " TEXT ,"
                    + COLUMN_TYPE_ATM + " TEXT)";

            String createHKReport = "CREATE TABLE " + TABLE_HK_REPORT + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                    + COLUMN_VISIT_ID + " TEXT ,"
                    + COLUMN_ATM_ID + " TEXT ,"
                    + COLUMN_USER_ID + " TEXT ,"
                    + COLUMN_SITE_ID + " TEXT ,"
                    + COLUMN_CITY + " TEXT ,"
                    + COLUMN_STATE + " TEXT ,"
                    + COLUMN_LOCATION + " TEXT ,"
                    + COLUMN_BANK_NAME + " TEXT ,"
                    + COLUMN_CUSTOMER_NAME + " TEXT ,"
                    + COLUMN_DATE_TIME_OF_VIST + " DATETIME ,"
                    + COLUMN_HKQ1 + " TEXT ," + COLUMN_HKQ2 + " TEXT ," + COLUMN_HKQ3 + " TEXT ,"
                    + COLUMN_HKQ4 + " TEXT ," + COLUMN_HKQ5 + " TEXT ," + COLUMN_HKQ6 + " TEXT ,"
                    + COLUMN_HKQ7 + " TEXT ," + COLUMN_HKQ8 + " TEXT ," + COLUMN_HKQ9 + " TEXT ,"
                    + COLUMN_HKQ10 + " TEXT ," + COLUMN_HKQ11 + " TEXT ," + COLUMN_HKQ12 + " TEXT ,"
                    + COLUMN_HKQ13 + " TEXT ," + COLUMN_HKQ14 + " TEXT ," + COLUMN_HKQ15 + " TEXT ,"
                    + COLUMN_HKQ16 + " TEXT ," + COLUMN_HKQ17 + " TEXT ,"
                    + COLUMN_HK_NAME + " TEXT ,"
                    + COLUMN_HK_NO + " TEXT ,"
                    + COLUMN_LATITUDE + " TEXT ,"
                    + COLUMN_LONGITUDE + " TEXT ,"
                    + COLUMN_HK_PHOTO1 + " BLOB ," + COLUMN_HK_PHOTO2 + " BLOB ," + COLUMN_HK_PHOTO3 + " BLOB ,"
                    + COLUMN_HK_PHOTO4 + " BLOB ," + COLUMN_HK_PHOTO5 + " BLOB ," + COLUMN_HK_PHOTO6 + " BLOB ,"
                    + COLUMN_HK_PHOTO7 + " BLOB ," + COLUMN_HK_PHOTO8 + " BLOB ," + COLUMN_HK_PHOTO9 + " BLOB ,"
                    + COLUMN_HK_PHOTO10 + " BLOB ," + COLUMN_HK_PHOTO11 + " BLOB ,"
                    + COLUMN_SYNC_STATUS + " TEXT ,"
                    + COLUMN_TYPE_ATM + " TEXT)";


            String createCTHkReport = "CREATE TABLE " + TABLE_CTHK_REPORT + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                    + COLUMN_VISIT_ID + " TEXT ,"
                    + COLUMN_ATM_ID + " TEXT ,"
                    + COLUMN_USER_ID + " TEXT ,"
                    + COLUMN_SITE_ID + " TEXT ,"
                    + COLUMN_CITY + " TEXT ,"
                    + COLUMN_STATE + " TEXT ,"
                    + COLUMN_LOCATION + " TEXT ,"
                    + COLUMN_BANK_NAME + " TEXT ,"
                    + COLUMN_CUSTOMER_NAME + " TEXT ,"
                    + COLUMN_DATE_TIME_OF_VIST + " DATETIME ,"
                    + COLUMN_CTQ1 + " TEXT ," + COLUMN_CTQ2 + " TEXT ," + COLUMN_CTQ3 + " TEXT ,"
                    + COLUMN_CTQ4 + " TEXT ," + COLUMN_CTQ5 + " TEXT ," + COLUMN_CTQ6 + " TEXT ,"
                    + COLUMN_CTQ7 + " TEXT ," + COLUMN_CTQ8 + " TEXT ," + COLUMN_CTQ9 + " TEXT ,"
                    + COLUMN_CTQ10 + " TEXT ," + COLUMN_CTQ11 + " TEXT ," + COLUMN_CTQ12 + " TEXT ,"
                    + COLUMN_CT_PHOTO1 + " BLOB ," + COLUMN_CT_PHOTO2 + " BLOB ," + COLUMN_CT_PHOTO3 + " BLOB ,"
                    + COLUMN_CT_NAME + " TEXT ," + COLUMN_CT_NO + " TEXT ,"

                    + COLUMN_HKQ1 + " TEXT ," + COLUMN_HKQ2 + " TEXT ," + COLUMN_HKQ3 + " TEXT ,"
                    + COLUMN_HKQ4 + " TEXT ," + COLUMN_HKQ5 + " TEXT ," + COLUMN_HKQ6 + " TEXT ,"
                    + COLUMN_HKQ7 + " TEXT ," + COLUMN_HKQ8 + " TEXT ," + COLUMN_HKQ9 + " TEXT ,"
                    + COLUMN_HKQ10 + " TEXT ," + COLUMN_HKQ11 + " TEXT ," + COLUMN_HKQ12 + " TEXT ,"
                    + COLUMN_HKQ13 + " TEXT ," + COLUMN_HKQ14 + " TEXT ," + COLUMN_HKQ15 + " TEXT ,"
                    + COLUMN_HKQ16 + " TEXT ," + COLUMN_HKQ17 + " TEXT ,"
                    + COLUMN_HK_PHOTO1 + " BLOB ," + COLUMN_HK_PHOTO2 + " BLOB ," + COLUMN_HK_PHOTO3 + " BLOB ,"
                    + COLUMN_HK_PHOTO4 + " BLOB ," + COLUMN_HK_PHOTO5 + " BLOB ," + COLUMN_HK_PHOTO6 + " BLOB ,"
                    + COLUMN_HK_PHOTO7 + " BLOB ," + COLUMN_HK_PHOTO8 + " BLOB ," + COLUMN_HK_PHOTO9 + " BLOB ,"
                    + COLUMN_HK_PHOTO10 + " BLOB ," + COLUMN_HK_PHOTO11 + " BLOB ,"

                    + COLUMN_HK_NAME + " TEXT ," + COLUMN_HK_NO + " TEXT ,"
                    + COLUMN_LATITUDE + " TEXT ," + COLUMN_LONGITUDE + " TEXT ,"

                    + COLUMN_SYNC_STATUS + " TEXT ,"
                    + COLUMN_TYPE_ATM + " TEXT)";


            String createAtmAuditRecord = "CREATE TABLE " + TABLE_ATM_AUDITED + "(" + COLUMN_ID_ATM + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                    + COLUMN_AUDITED_ATM_ID + " TEXT, "
                    + COLUMN_AUDITED_ATM_TYPE + " TEXT, "
                    + COLUMN_AUDITED_DATE + " DATETIME )";

            sqLiteDatabase.execSQL(createDb);
            sqLiteDatabase.execSQL(createCTReport);
            sqLiteDatabase.execSQL(createHKReport);
            sqLiteDatabase.execSQL(createAtmAuditRecord);
            sqLiteDatabase.execSQL(createCTHkReport);

            Log.e("MangalB", "Table ATM create successfully");

        } catch (SQLException e) {
            Log.e("MangalB", e.toString());
        }


    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ATM);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CT_REPORT);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_HK_REPORT);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ATM_AUDITED);

        onCreate(sqLiteDatabase);

    }





    public List<Atm> fetchAtms()
{
    List<Atm> atmList=new ArrayList<Atm>();
    String selectQuery="SELECT * FROM "+TABLE_ATM;
    SQLiteDatabase databaseRead=this.getReadableDatabase();
    Cursor cursor=databaseRead.rawQuery(selectQuery,null);
    if(cursor.moveToFirst())
    {
        do{

            atmList.add(new Atm(cursor.getString(cursor.getColumnIndex(COLUMN_SITE_ID_ATM)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_ATM_ID_ATM)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_BANK_NAME_ATM)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_CUSTOMER_NAME_ATM)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS_ATM)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_CITY_ATM)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_STATE_ATM))
                    ,cursor.getString(cursor.getColumnIndex(COLUMN_TYPE_ATM)),
                    getAuditDateRecords(cursor.getString(cursor.getColumnIndex(COLUMN_ATM_ID_ATM)))
            ));

        }while(cursor.moveToNext());
    }
    return atmList;
}

    public List<Atm> fetchAtmsByType(String type)
    {
        List<Atm> atmList=new ArrayList<Atm>();
        String selectQuery="SELECT * FROM "+TABLE_ATM+ " WHERE "+COLUMN_TYPE_ATM+" = '"+type+"'";
        SQLiteDatabase databaseRead=this.getReadableDatabase();
        Cursor cursor=databaseRead.rawQuery(selectQuery,null);
        if(cursor.moveToFirst())
        {
            do{

                atmList.add(new Atm(cursor.getString(cursor.getColumnIndex(COLUMN_SITE_ID_ATM)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_ATM_ID_ATM)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_BANK_NAME_ATM)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_CUSTOMER_NAME_ATM)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS_ATM)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_CITY_ATM)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_STATE_ATM))
                        ,cursor.getString(cursor.getColumnIndex(COLUMN_TYPE_ATM)),
                        getAuditDateRecords(cursor.getString(cursor.getColumnIndex(COLUMN_ATM_ID_ATM)))));
            }while(cursor.moveToNext());
        }
        return atmList;
    }


    public  boolean insertATM(List<Atm> atmList)
    {
        SQLiteDatabase database=this.getWritableDatabase();
        long errorCode;
        boolean insertStatus=true;
        database.delete(TABLE_ATM, null, null);

        for(int i=0;i<atmList.size();i++)
        {
            ContentValues contentValues=new ContentValues();
            contentValues.put(COLUMN_SITE_ID_ATM,atmList.get(i).getSiteId());
            contentValues.put(COLUMN_ATM_ID_ATM,atmList.get(i).getAtmId());
            contentValues.put(COLUMN_BANK_NAME_ATM,atmList.get(i).getBankName());
            contentValues.put(COLUMN_CUSTOMER_NAME_ATM,atmList.get(i).getCustomerName());
            contentValues.put(COLUMN_ADDRESS_ATM,atmList.get(i).getAddress());
            contentValues.put(COLUMN_CITY_ATM,atmList.get(i).getCity());
            contentValues.put(COLUMN_STATE_ATM,atmList.get(i).getState());
            contentValues.put(COLUMN_TYPE_ATM,atmList.get(i).getType());
            errorCode= database.insert(TABLE_ATM,null,contentValues);
            if(errorCode==-1)
            {
                insertStatus=false;
            }
        }



        return insertStatus;
    }







    public ArrayList<Visit> fetchCTReport()
    {
        ArrayList<Visit> visitList=new ArrayList<Visit>();
        String selectQuery="SELECT * FROM "+TABLE_CT_REPORT+" WHERE "+COLUMN_SYNC_STATUS+" = 'unsynced'";
        SQLiteDatabase databaseRead=this.getReadableDatabase();
        Cursor cursor=databaseRead.rawQuery(selectQuery,null);
        if(cursor.moveToFirst())
        {
            do{


                visitList.add(new Visit(cursor.getString(cursor.getColumnIndex(COLUMN_VISIT_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_ATM_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_SITE_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_CITY)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_STATE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_BANK_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_CUSTOMER_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_DATE_TIME_OF_VIST)),
                        new String[] {
                                cursor.getString(cursor.getColumnIndex(COLUMN_CTQ1)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_CTQ2)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_CTQ3)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_CTQ4)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_CTQ5)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_CTQ6)),

                                cursor.getString(cursor.getColumnIndex(COLUMN_CTQ7)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_CTQ8)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_CTQ9)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_CTQ10)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_CTQ11)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_CTQ12))},

                        cursor.getString(cursor.getColumnIndex(COLUMN_CT_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_CT_NO)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_LATITUDE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_LONGITUDE)),
                        getBitMapFromByte(cursor.getBlob(cursor.getColumnIndex(COLUMN_CT_PHOTO1))),
                        getBitMapFromByte(cursor.getBlob(cursor.getColumnIndex(COLUMN_CT_PHOTO2))),
                        getBitMapFromByte(cursor.getBlob(cursor.getColumnIndex(COLUMN_CT_PHOTO3)))
                        ));



                /* visitList.add(new Visit(cursor.getString(cursor.getColumnIndex(COLUMN_VISIT_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_ATM_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_BANK_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_CUSTOMER_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_DATE_OF_VISIT))
                        ,cursor.getString(cursor.getColumnIndex(COLUMN_TIME_OF_VISIT)),

                        cursor.getString(cursor.getColumnIndex(COLUMN_CT_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_CT_NO)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_LATITUDE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_LONGITUDE))
                       *//* getBitMapFromByte(cursor.getBlob(cursor.getColumnIndex(COLUMN_CT_PHOTO1))),
                        getBitMapFromByte(cursor.getBlob(cursor.getColumnIndex(COLUMN_CT_PHOTO2))),
                        getBitMapFromByte(cursor.getBlob(cursor.getColumnIndex(COLUMN_CT_PHOTO3))),
                        getBitMapFromByte(cursor.getBlob(cursor.getColumnIndex(COLUMN_CT_SIGNATURE)))*//*
                ));*/



            }while(cursor.moveToNext());
        }
        return visitList;
    }


    public ArrayList<Visit> fetchHKReport() {

        ArrayList<Visit> visitList = new ArrayList<Visit>();
        String selectQuery = "SELECT * FROM " + TABLE_HK_REPORT + " WHERE " + COLUMN_SYNC_STATUS + " = 'unsynced'";
        SQLiteDatabase databaseRead = this.getReadableDatabase();
        Cursor cursor = databaseRead.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {


                visitList.add(new Visit(cursor.getString(cursor.getColumnIndex(COLUMN_VISIT_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_ATM_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_SITE_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_CITY)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_STATE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_BANK_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_CUSTOMER_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_DATE_TIME_OF_VIST)),
                        new String[]{
                                cursor.getString(cursor.getColumnIndex(COLUMN_HKQ1)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_HKQ2)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_HKQ3)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_HKQ4)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_HKQ5)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_HKQ6)),

                                cursor.getString(cursor.getColumnIndex(COLUMN_HKQ7)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_HKQ8)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_HKQ9)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_HKQ10)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_HKQ11)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_HKQ12)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_HKQ13)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_HKQ14)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_HKQ15)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_HKQ16)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_HKQ17))},

                        cursor.getString(cursor.getColumnIndex(COLUMN_HK_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_HK_NO)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_LATITUDE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_LONGITUDE)),
                        getBitMapFromByte(cursor.getBlob(cursor.getColumnIndex(COLUMN_HK_PHOTO1))),
                        getBitMapFromByte(cursor.getBlob(cursor.getColumnIndex(COLUMN_HK_PHOTO2))),
                        getBitMapFromByte(cursor.getBlob(cursor.getColumnIndex(COLUMN_HK_PHOTO3))),
                        getBitMapFromByte(cursor.getBlob(cursor.getColumnIndex(COLUMN_HK_PHOTO4))),
                        getBitMapFromByte(cursor.getBlob(cursor.getColumnIndex(COLUMN_HK_PHOTO5))),
                        getBitMapFromByte(cursor.getBlob(cursor.getColumnIndex(COLUMN_HK_PHOTO6))),
                        getBitMapFromByte(cursor.getBlob(cursor.getColumnIndex(COLUMN_HK_PHOTO7))),
                        getBitMapFromByte(cursor.getBlob(cursor.getColumnIndex(COLUMN_HK_PHOTO8))),
                        getBitMapFromByte(cursor.getBlob(cursor.getColumnIndex(COLUMN_HK_PHOTO9))),
                        getBitMapFromByte(cursor.getBlob(cursor.getColumnIndex(COLUMN_HK_PHOTO10))),
                        getBitMapFromByte(cursor.getBlob(cursor.getColumnIndex(COLUMN_HK_PHOTO11)))
                ));

                Log.e("in fetchHkReport :", cursor.getString(cursor.getColumnIndex(COLUMN_HK_NAME)) + "\n" + cursor.getString(cursor.getColumnIndex(COLUMN_HK_NO)));


            } while (cursor.moveToNext());
        }
        return visitList;


    }

    public ArrayList<Visit> fetchCtHkReport() {

        ArrayList<Visit> visitList = new ArrayList<Visit>();
        String selectQuery = "SELECT * FROM " + TABLE_CTHK_REPORT + " WHERE " + COLUMN_SYNC_STATUS + " = 'unsynced'";
        SQLiteDatabase databaseRead = this.getReadableDatabase();
        Cursor cursor = databaseRead.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {


                visitList.add(new Visit(cursor.getString(cursor.getColumnIndex(COLUMN_VISIT_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_ATM_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_SITE_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_CITY)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_STATE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_BANK_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_CUSTOMER_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_DATE_TIME_OF_VIST)),

                        new String[]{
                                cursor.getString(cursor.getColumnIndex(COLUMN_CTQ1)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_CTQ2)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_CTQ3)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_CTQ4)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_CTQ5)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_CTQ6)),

                                cursor.getString(cursor.getColumnIndex(COLUMN_CTQ7)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_CTQ8)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_CTQ9)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_CTQ10)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_CTQ11)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_CTQ12))},

                        getBitMapFromByte(cursor.getBlob(cursor.getColumnIndex(COLUMN_CT_PHOTO1))),
                        getBitMapFromByte(cursor.getBlob(cursor.getColumnIndex(COLUMN_CT_PHOTO2))),
                        getBitMapFromByte(cursor.getBlob(cursor.getColumnIndex(COLUMN_CT_PHOTO3))),
                        cursor.getString(cursor.getColumnIndex(COLUMN_CT_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_CT_NO)),


                        new String[]{
                                cursor.getString(cursor.getColumnIndex(COLUMN_HKQ1)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_HKQ2)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_HKQ3)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_HKQ4)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_HKQ5)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_HKQ6)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_HKQ7)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_HKQ8)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_HKQ9)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_HKQ10)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_HKQ11)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_HKQ12)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_HKQ13)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_HKQ14)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_HKQ15)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_HKQ16)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_HKQ17))},

                        getBitMapFromByte(cursor.getBlob(cursor.getColumnIndex(COLUMN_HK_PHOTO1))),
                        getBitMapFromByte(cursor.getBlob(cursor.getColumnIndex(COLUMN_HK_PHOTO2))),
                        getBitMapFromByte(cursor.getBlob(cursor.getColumnIndex(COLUMN_HK_PHOTO3))),
                        getBitMapFromByte(cursor.getBlob(cursor.getColumnIndex(COLUMN_HK_PHOTO4))),
                        getBitMapFromByte(cursor.getBlob(cursor.getColumnIndex(COLUMN_HK_PHOTO5))),
                        getBitMapFromByte(cursor.getBlob(cursor.getColumnIndex(COLUMN_HK_PHOTO6))),
                        getBitMapFromByte(cursor.getBlob(cursor.getColumnIndex(COLUMN_HK_PHOTO7))),
                        getBitMapFromByte(cursor.getBlob(cursor.getColumnIndex(COLUMN_HK_PHOTO8))),
                        getBitMapFromByte(cursor.getBlob(cursor.getColumnIndex(COLUMN_HK_PHOTO9))),
                        getBitMapFromByte(cursor.getBlob(cursor.getColumnIndex(COLUMN_HK_PHOTO10))),
                        getBitMapFromByte(cursor.getBlob(cursor.getColumnIndex(COLUMN_HK_PHOTO11))),

                        cursor.getString(cursor.getColumnIndex(COLUMN_HK_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_HK_NO)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_LATITUDE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_LONGITUDE))

                ));

                //   Log.e("in fetchCT-HkReport :",cursor.getString(cursor.getColumnIndex(COLUMN_HK_NAME))+"\n"+cursor.getString(cursor.getColumnIndex(COLUMN_HK_NO)));


            } while (cursor.moveToNext());
        }
        return visitList;


    }


    private Bitmap getBitMapFromByte(byte[] blob) {

        // If HK optional photos are not taken then blob will be null
        if (blob != null) {
            return BitmapFactory.decodeByteArray(blob, 0, blob.length);
        } else
            return null;

    }


    public  boolean insertCTReport(VisitSingleton visitList)
    {
        SQLiteDatabase database=this.getWritableDatabase();
        long errorCode;
        boolean insertStatus=true;
        ContentValues contentValues=new ContentValues();


        contentValues.put(COLUMN_VISIT_ID, visitList.getVisitId().replace(" ", "_").replace("-", "_").replace(":", "_"));
            contentValues.put(COLUMN_ATM_ID,visitList.getAtmId());
            contentValues.put(COLUMN_USER_ID,visitList.getUserId());
            contentValues.put(COLUMN_SITE_ID,visitList.getSiteId());
            contentValues.put(COLUMN_CITY,visitList.getCity());
            contentValues.put(COLUMN_STATE,visitList.getState());
            contentValues.put(COLUMN_LOCATION,visitList.getLocation());
            contentValues.put(COLUMN_BANK_NAME,visitList.getBankName());
            contentValues.put(COLUMN_CUSTOMER_NAME,visitList.getCustomerName());
        contentValues.put(COLUMN_DATE_TIME_OF_VIST, visitList.getDatetime());

            String[] ctReport=visitList.getCt();


            contentValues.put(COLUMN_CTQ1,ctReport[0]);
            contentValues.put(COLUMN_CTQ2,ctReport[1]);
            contentValues.put(COLUMN_CTQ3,ctReport[2]);
            contentValues.put(COLUMN_CTQ4,ctReport[3]);
            contentValues.put(COLUMN_CTQ5,ctReport[4]);
            contentValues.put(COLUMN_CTQ6,ctReport[5]);
            contentValues.put(COLUMN_CTQ7,ctReport[6]);
            contentValues.put(COLUMN_CTQ8,ctReport[7]);
            contentValues.put(COLUMN_CTQ9,ctReport[8]);
            contentValues.put(COLUMN_CTQ10,ctReport[9]);
            contentValues.put(COLUMN_CTQ11,ctReport[10]);
            contentValues.put(COLUMN_CTQ12,ctReport[11]);


            contentValues.put(COLUMN_CT_NAME,visitList.getCaretakerName());
            contentValues.put(COLUMN_CT_NO,visitList.getCaretakerNumber());
            contentValues.put(COLUMN_LATITUDE,visitList.getLatitude());
            contentValues.put(COLUMN_LONGITUDE,visitList.getLongitude());
        contentValues.put(COLUMN_CT_PHOTO1, visitList.getCtphotoByteArray("ctimg0"));
        contentValues.put(COLUMN_CT_PHOTO2, visitList.getCtphotoByteArray("ctimg1"));
        contentValues.put(COLUMN_CT_PHOTO3, visitList.getCtphotoByteArray("ctimg2"));
            contentValues.put(COLUMN_SYNC_STATUS,"unsynced");


            errorCode= database.insert(TABLE_CT_REPORT,null,contentValues);
            if(errorCode==-1)
            {
                insertStatus=false;
            }
            else
            {
                errorCode = insertAuditedAtm(visitList.getDatetime(), visitList.getAtmId(), TYPE_CT);
                if(errorCode==-1)
                {
                    insertStatus=false;
                }
            }






        return insertStatus;
    }


    public boolean insertHKReport(VisitSingleton visitList) {
        SQLiteDatabase database = this.getWritableDatabase();
        long errorCode;
        boolean insertStatus = true;
        ContentValues contentValues = new ContentValues();


        contentValues.put(COLUMN_VISIT_ID, visitList.getVisitId().replace(" ", "_").replace("-", "_").replace(":", "_"));
        contentValues.put(COLUMN_ATM_ID, visitList.getAtmId());
        contentValues.put(COLUMN_USER_ID, visitList.getUserId());
        contentValues.put(COLUMN_SITE_ID, visitList.getSiteId());
        contentValues.put(COLUMN_CITY, visitList.getCity());
        contentValues.put(COLUMN_STATE, visitList.getState());
        contentValues.put(COLUMN_LOCATION, visitList.getLocation());
        contentValues.put(COLUMN_BANK_NAME, visitList.getBankName());
        contentValues.put(COLUMN_CUSTOMER_NAME, visitList.getCustomerName());
        contentValues.put(COLUMN_DATE_TIME_OF_VIST, visitList.getDatetime());

        String[] hkReport = visitList.getHk();


        contentValues.put(COLUMN_HKQ1, hkReport[0]);
        contentValues.put(COLUMN_HKQ2, hkReport[1]);
        contentValues.put(COLUMN_HKQ3, hkReport[2]);
        contentValues.put(COLUMN_HKQ4, hkReport[3]);
        contentValues.put(COLUMN_HKQ5, hkReport[4]);
        contentValues.put(COLUMN_HKQ6, hkReport[5]);
        contentValues.put(COLUMN_HKQ7, hkReport[6]);
        contentValues.put(COLUMN_HKQ8, hkReport[7]);
        contentValues.put(COLUMN_HKQ9, hkReport[8]);
        contentValues.put(COLUMN_HKQ10, hkReport[9]);
        contentValues.put(COLUMN_HKQ11, hkReport[10]);
        contentValues.put(COLUMN_HKQ12, hkReport[11]);
        contentValues.put(COLUMN_HKQ13, hkReport[12]);
        contentValues.put(COLUMN_HKQ14, hkReport[13]);
        contentValues.put(COLUMN_HKQ15, hkReport[14]);
        contentValues.put(COLUMN_HKQ16, hkReport[15]);
        contentValues.put(COLUMN_HKQ17, hkReport[16]);


        contentValues.put(COLUMN_HK_NAME, visitList.getHousekeeperName());
        contentValues.put(COLUMN_HK_NO, visitList.getHousekeeperNumber());
        contentValues.put(COLUMN_LATITUDE, visitList.getLatitude());
        contentValues.put(COLUMN_LONGITUDE, visitList.getLongitude());
        contentValues.put(COLUMN_HK_PHOTO1, visitList.getHkphotoByteArray("hkimg0"));
        contentValues.put(COLUMN_HK_PHOTO2, visitList.getHkphotoByteArray("hkimg1"));
        contentValues.put(COLUMN_HK_PHOTO3, visitList.getHkphotoByteArray("hkimg2"));
        contentValues.put(COLUMN_HK_PHOTO4, visitList.getHkphotoByteArray("hkimg3"));
        contentValues.put(COLUMN_HK_PHOTO5, visitList.getHkphotoByteArray("hkimg4"));
        contentValues.put(COLUMN_HK_PHOTO6, visitList.getHkphotoByteArray("hkimg5"));
        contentValues.put(COLUMN_HK_PHOTO7, visitList.getHkphotoByteArray("hkimg6"));
        contentValues.put(COLUMN_HK_PHOTO8, visitList.getHkphotoByteArray("hkimg7"));
        contentValues.put(COLUMN_HK_PHOTO9, visitList.getHkphotoByteArray("hkimg8"));
        contentValues.put(COLUMN_HK_PHOTO10, visitList.getHkphotoByteArray("hkimg9"));
        contentValues.put(COLUMN_HK_PHOTO11, visitList.getHkphotoByteArray("hkimg10"));
        contentValues.put(COLUMN_SYNC_STATUS, "unsynced");


        errorCode = database.insert(TABLE_HK_REPORT, null, contentValues);
        if (errorCode == -1) {
            insertStatus = false;
        } else {
            errorCode = insertAuditedAtm(visitList.getDatetime(), visitList.getAtmId(), TYPE_CT);
            if (errorCode == -1) {
                insertStatus = false;
            }
        }


        return insertStatus;
    }


    public boolean insertCTHKReport(VisitSingleton visitList) {
        SQLiteDatabase database = this.getWritableDatabase();
        long errorCode;
        boolean insertStatus = true;
        ContentValues contentValues = new ContentValues();


        contentValues.put(COLUMN_VISIT_ID, visitList.getVisitId().replace(" ", "_").replace("-", "_").replace(":", "_"));
        contentValues.put(COLUMN_ATM_ID, visitList.getAtmId());
        contentValues.put(COLUMN_USER_ID, visitList.getUserId());
        contentValues.put(COLUMN_SITE_ID, visitList.getSiteId());
        contentValues.put(COLUMN_CITY, visitList.getCity());
        contentValues.put(COLUMN_STATE, visitList.getState());
        contentValues.put(COLUMN_LOCATION, visitList.getLocation());
        contentValues.put(COLUMN_BANK_NAME, visitList.getBankName());
        contentValues.put(COLUMN_CUSTOMER_NAME, visitList.getCustomerName());
        contentValues.put(COLUMN_DATE_TIME_OF_VIST, visitList.getDatetime());

        String[] ctReport = visitList.getCt();

        contentValues.put(COLUMN_CTQ1, ctReport[0]);
        contentValues.put(COLUMN_CTQ2, ctReport[1]);
        contentValues.put(COLUMN_CTQ3, ctReport[2]);
        contentValues.put(COLUMN_CTQ4, ctReport[3]);
        contentValues.put(COLUMN_CTQ5, ctReport[4]);
        contentValues.put(COLUMN_CTQ6, ctReport[5]);
        contentValues.put(COLUMN_CTQ7, ctReport[6]);
        contentValues.put(COLUMN_CTQ8, ctReport[7]);
        contentValues.put(COLUMN_CTQ9, ctReport[8]);
        contentValues.put(COLUMN_CTQ10, ctReport[9]);
        contentValues.put(COLUMN_CTQ11, ctReport[10]);
        contentValues.put(COLUMN_CTQ12, ctReport[11]);
        contentValues.put(COLUMN_CT_PHOTO1, visitList.getCtphotoByteArray("ctimg0"));
        contentValues.put(COLUMN_CT_PHOTO2, visitList.getCtphotoByteArray("ctimg1"));
        contentValues.put(COLUMN_CT_PHOTO3, visitList.getCtphotoByteArray("ctimg2"));
        contentValues.put(COLUMN_CT_NAME, visitList.getCaretakerName());
        contentValues.put(COLUMN_CT_NO, visitList.getCaretakerNumber());


        String[] hkReport = visitList.getHk();

        contentValues.put(COLUMN_HKQ1, hkReport[0]);
        contentValues.put(COLUMN_HKQ2, hkReport[1]);
        contentValues.put(COLUMN_HKQ3, hkReport[2]);
        contentValues.put(COLUMN_HKQ4, hkReport[3]);
        contentValues.put(COLUMN_HKQ5, hkReport[4]);
        contentValues.put(COLUMN_HKQ6, hkReport[5]);
        contentValues.put(COLUMN_HKQ7, hkReport[6]);
        contentValues.put(COLUMN_HKQ8, hkReport[7]);
        contentValues.put(COLUMN_HKQ9, hkReport[8]);
        contentValues.put(COLUMN_HKQ10, hkReport[9]);
        contentValues.put(COLUMN_HKQ11, hkReport[10]);
        contentValues.put(COLUMN_HKQ12, hkReport[11]);
        contentValues.put(COLUMN_HKQ13, hkReport[12]);
        contentValues.put(COLUMN_HKQ14, hkReport[13]);
        contentValues.put(COLUMN_HKQ15, hkReport[14]);
        contentValues.put(COLUMN_HKQ16, hkReport[15]);
        contentValues.put(COLUMN_HKQ17, hkReport[16]);
        contentValues.put(COLUMN_HK_PHOTO1, visitList.getHkphotoByteArray("hkimg0"));
        contentValues.put(COLUMN_HK_PHOTO2, visitList.getHkphotoByteArray("hkimg1"));
        contentValues.put(COLUMN_HK_PHOTO3, visitList.getHkphotoByteArray("hkimg2"));
        contentValues.put(COLUMN_HK_PHOTO4, visitList.getHkphotoByteArray("hkimg3"));
        contentValues.put(COLUMN_HK_PHOTO5, visitList.getHkphotoByteArray("hkimg4"));
        contentValues.put(COLUMN_HK_PHOTO6, visitList.getHkphotoByteArray("hkimg5"));
        contentValues.put(COLUMN_HK_PHOTO7, visitList.getHkphotoByteArray("hkimg6"));
        contentValues.put(COLUMN_HK_PHOTO8, visitList.getHkphotoByteArray("hkimg7"));
        contentValues.put(COLUMN_HK_PHOTO9, visitList.getHkphotoByteArray("hkimg8"));
        contentValues.put(COLUMN_HK_PHOTO10, visitList.getHkphotoByteArray("hkimg9"));
        contentValues.put(COLUMN_HK_PHOTO11, visitList.getHkphotoByteArray("hkimg10"));
        contentValues.put(COLUMN_HK_NAME, visitList.getHousekeeperName());
        contentValues.put(COLUMN_HK_NO, visitList.getHousekeeperNumber());

        contentValues.put(COLUMN_LATITUDE, visitList.getLatitude());
        contentValues.put(COLUMN_LONGITUDE, visitList.getLongitude());

        contentValues.put(COLUMN_SYNC_STATUS, "unsynced");


        errorCode = database.insert(TABLE_CTHK_REPORT, null, contentValues);
        if (errorCode == -1) {
            insertStatus = false;
        } else {
            errorCode = insertAuditedAtm(visitList.getDatetime(), visitList.getAtmId(), TYPE_CT);
            if (errorCode == -1) {
                insertStatus = false;
            }
        }


        return insertStatus;
    }




    public List<AtmAudited> getAuditDateRecords()
    {
        String selectQuery="SELECT * FROM "+TABLE_ATM_AUDITED;
        SQLiteDatabase databaseRead=this.getReadableDatabase();
        Cursor cursor=databaseRead.rawQuery(selectQuery,null);
        List<AtmAudited> atmAuditedList=new ArrayList<AtmAudited>();
        if(cursor.moveToFirst())
        {
            do{


                if (cursor.getString(cursor.getColumnIndex(COLUMN_AUDITED_DATE)) != "") {
                    atmAuditedList.add(new AtmAudited(cursor.getString(cursor.getColumnIndex(COLUMN_AUDITED_ATM_ID)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_AUDITED_DATE)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_AUDITED_ATM_TYPE)))
                    );

                } else {
                    atmAuditedList.add(new AtmAudited(cursor.getString(cursor.getColumnIndex(COLUMN_AUDITED_ATM_ID)),
                            "Not audited",
                            cursor.getString(cursor.getColumnIndex(COLUMN_AUDITED_ATM_TYPE)))
                    );

                }

            }while(cursor.moveToNext());
        }
        return atmAuditedList;
    }

    public String  getAuditDateRecords(String atmid)
    {

        String selectQuery="SELECT * FROM "+TABLE_ATM_AUDITED+" WHERE "+COLUMN_AUDITED_ATM_ID +" = '"+atmid+"'";
        SQLiteDatabase databaseRead=this.getReadableDatabase();
        Cursor cursor=databaseRead.rawQuery(selectQuery,null);
        SQLiteDatabase databaseRead2 = this.getReadableDatabase();
        String lastupdateddate="not audited";
        String days = "0";
        if(cursor.moveToFirst())
        {
            do{

                if (cursor.getString(cursor.getColumnIndex(COLUMN_AUDITED_DATE)) != "" && cursor.getString(cursor.getColumnIndex(COLUMN_AUDITED_DATE)) != null) {
                    lastupdateddate = cursor.getString(cursor.getColumnIndex(COLUMN_AUDITED_DATE));

                }

            }while(cursor.moveToNext());
        }

        return lastupdateddate;
    }






    public long  insertAuditedAtm(String date,String atmid,String type)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COLUMN_AUDITED_ATM_ID,atmid);
        contentValues.put(COLUMN_AUDITED_ATM_TYPE,type);
        contentValues.put(COLUMN_AUDITED_DATE,date);

        long errocode= sqLiteDatabase.insert(TABLE_ATM_AUDITED,null,contentValues);
        return errocode;
    }




    public int getUnsyncedRecords()
    {
        String selectQuery="SELECT * FROM "+TABLE_CT_REPORT+" WHERE "+COLUMN_SYNC_STATUS+" = 'unsynced'";
        SQLiteDatabase databaseRead=this.getReadableDatabase();
        Cursor cursor=databaseRead.rawQuery(selectQuery,null);
        return cursor.getCount();
    }

    public void updateSyncedData(String atmid)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_SYNC_STATUS,"synced");
        SQLiteDatabase databaseUpdate=this.getReadableDatabase();
        databaseUpdate.update(TABLE_CT_REPORT,contentValues,COLUMN_VISIT_ID+" = "+atmid,null);

    }

    public void deleteSyncedData(String atmid)
    {

        SQLiteDatabase databaseUpdate=this.getReadableDatabase();
        String whereClause = COLUMN_VISIT_ID + "=?";
        String[] whereArgs = new String[] { String.valueOf(atmid) };
        databaseUpdate.delete(TABLE_CT_REPORT,whereClause,whereArgs);

    }

    public int getUnsyncedRecordsFromHK() {
        String selectQuery = "SELECT * FROM " + TABLE_HK_REPORT + " WHERE " + COLUMN_SYNC_STATUS + " = 'unsynced'";
        SQLiteDatabase databaseRead = this.getReadableDatabase();
        Cursor cursor = databaseRead.rawQuery(selectQuery, null);
        return cursor.getCount();
    }

    public void deleteSyncedDataFromHK(String atmid) {

        SQLiteDatabase databaseUpdate = this.getReadableDatabase();
        String whereClause = COLUMN_VISIT_ID + "=?";
        String[] whereArgs = new String[]{String.valueOf(atmid)};
        databaseUpdate.delete(TABLE_HK_REPORT, whereClause, whereArgs);

    }
    

    public Visit getVisitFromId(int i) {

        String selectQuery="SELECT * FROM "+TABLE_CT_REPORT+ " WHERE "+COLUMN_ID+" = "+i;
        SQLiteDatabase databaseRead=this.getReadableDatabase();
        Cursor cursor=databaseRead.rawQuery(selectQuery,null);
        if(cursor.moveToFirst())
        {


             visitt= new Visit(cursor.getString(cursor.getColumnIndex(COLUMN_VISIT_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_ATM_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_SITE_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_CITY)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_STATE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_BANK_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_CUSTOMER_NAME)),
                     cursor.getString(cursor.getColumnIndex(COLUMN_DATE_TIME_OF_VIST)),
                        new String[] {
                                cursor.getString(cursor.getColumnIndex(COLUMN_CTQ1)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_CTQ2)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_CTQ3)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_CTQ4)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_CTQ5)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_CTQ6)),

                                cursor.getString(cursor.getColumnIndex(COLUMN_CTQ7)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_CTQ8)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_CTQ9)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_CTQ10)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_CTQ11)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_CTQ12))},

                        cursor.getString(cursor.getColumnIndex(COLUMN_CT_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_CT_NO)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_LATITUDE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_LONGITUDE)),
                        getBitMapFromByte(cursor.getBlob(cursor.getColumnIndex(COLUMN_CT_PHOTO1))),
                        getBitMapFromByte(cursor.getBlob(cursor.getColumnIndex(COLUMN_CT_PHOTO2))),
                     getBitMapFromByte(cursor.getBlob(cursor.getColumnIndex(COLUMN_CT_PHOTO3)))
             );




        }
        return visitt;


    }


    public Visit getCTHkReportFromVisitId(String visitId) {


        String selectQuery = "SELECT * FROM " + TABLE_CTHK_REPORT + " WHERE " + COLUMN_VISIT_ID + " = " + "'" + visitId + "'";
        SQLiteDatabase databaseRead = this.getReadableDatabase();
        Cursor cursor = databaseRead.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {


            CtHkVisit = new Visit(cursor.getString(cursor.getColumnIndex(COLUMN_VISIT_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_ATM_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_SITE_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_CITY)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_STATE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_BANK_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_CUSTOMER_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_DATE_TIME_OF_VIST)),

                    new String[]{
                            cursor.getString(cursor.getColumnIndex(COLUMN_CTQ1)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_CTQ2)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_CTQ3)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_CTQ4)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_CTQ5)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_CTQ6)),

                            cursor.getString(cursor.getColumnIndex(COLUMN_CTQ7)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_CTQ8)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_CTQ9)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_CTQ10)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_CTQ11)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_CTQ12))},

                    getBitMapFromByte(cursor.getBlob(cursor.getColumnIndex(COLUMN_CT_PHOTO1))),
                    getBitMapFromByte(cursor.getBlob(cursor.getColumnIndex(COLUMN_CT_PHOTO2))),
                    getBitMapFromByte(cursor.getBlob(cursor.getColumnIndex(COLUMN_CT_PHOTO3))),
                    cursor.getString(cursor.getColumnIndex(COLUMN_CT_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_CT_NO)),


                    new String[]{
                            cursor.getString(cursor.getColumnIndex(COLUMN_HKQ1)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_HKQ2)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_HKQ3)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_HKQ4)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_HKQ5)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_HKQ6)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_HKQ7)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_HKQ8)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_HKQ9)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_HKQ10)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_HKQ11)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_HKQ12)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_HKQ13)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_HKQ14)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_HKQ15)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_HKQ16)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_HKQ17))},

                    getBitMapFromByte(cursor.getBlob(cursor.getColumnIndex(COLUMN_HK_PHOTO1))),
                    getBitMapFromByte(cursor.getBlob(cursor.getColumnIndex(COLUMN_HK_PHOTO2))),
                    getBitMapFromByte(cursor.getBlob(cursor.getColumnIndex(COLUMN_HK_PHOTO3))),
                    getBitMapFromByte(cursor.getBlob(cursor.getColumnIndex(COLUMN_HK_PHOTO4))),
                    getBitMapFromByte(cursor.getBlob(cursor.getColumnIndex(COLUMN_HK_PHOTO5))),
                    getBitMapFromByte(cursor.getBlob(cursor.getColumnIndex(COLUMN_HK_PHOTO6))),
                    getBitMapFromByte(cursor.getBlob(cursor.getColumnIndex(COLUMN_HK_PHOTO7))),
                    getBitMapFromByte(cursor.getBlob(cursor.getColumnIndex(COLUMN_HK_PHOTO8))),
                    getBitMapFromByte(cursor.getBlob(cursor.getColumnIndex(COLUMN_HK_PHOTO9))),
                    getBitMapFromByte(cursor.getBlob(cursor.getColumnIndex(COLUMN_HK_PHOTO10))),
                    getBitMapFromByte(cursor.getBlob(cursor.getColumnIndex(COLUMN_HK_PHOTO11))),

                    cursor.getString(cursor.getColumnIndex(COLUMN_HK_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_HK_NO)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_LATITUDE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_LONGITUDE)));

            //   Log.e("in fetchCT-HkReport :",cursor.getString(cursor.getColumnIndex(COLUMN_HK_NAME))+"\n"+cursor.getString(cursor.getColumnIndex(COLUMN_HK_NO)));


        }
        return CtHkVisit;

    }


    public Visit getCTReportFromVisitId(String visitId) {

        Visit v = null;

        String selectQuery = "SELECT * FROM " + TABLE_CT_REPORT + " WHERE " + COLUMN_VISIT_ID + " = " + "'" + visitId + "'";
        SQLiteDatabase databaseRead = this.getReadableDatabase();
        Cursor cursor = databaseRead.rawQuery(selectQuery, null);
        Log.e("ManB", "cursor length " + cursor.getCount());
        if (cursor.moveToFirst()) {


            v = new Visit(cursor.getString(cursor.getColumnIndex(COLUMN_VISIT_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_ATM_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_SITE_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_CITY)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_STATE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_BANK_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_CUSTOMER_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_DATE_TIME_OF_VIST)),
                    new String[]{
                            cursor.getString(cursor.getColumnIndex(COLUMN_CTQ1)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_CTQ2)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_CTQ3)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_CTQ4)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_CTQ5)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_CTQ6)),

                            cursor.getString(cursor.getColumnIndex(COLUMN_CTQ7)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_CTQ8)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_CTQ9)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_CTQ10)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_CTQ11)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_CTQ12))},

                    cursor.getString(cursor.getColumnIndex(COLUMN_CT_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_CT_NO)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_LATITUDE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_LONGITUDE)),
                    getBitMapFromByte(cursor.getBlob(cursor.getColumnIndex(COLUMN_CT_PHOTO1))),
                    getBitMapFromByte(cursor.getBlob(cursor.getColumnIndex(COLUMN_CT_PHOTO2))),
                    getBitMapFromByte(cursor.getBlob(cursor.getColumnIndex(COLUMN_CT_PHOTO3)))
            );

            Log.e("ManB CT-Single Report :", cursor.getString(cursor.getColumnIndex(COLUMN_CT_NAME)) + "\n" + cursor.getString(cursor.getColumnIndex(COLUMN_CT_NO)));
        }
        return v;

    }


}
