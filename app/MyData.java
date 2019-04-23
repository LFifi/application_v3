package com.example.ledzi.application_v3.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MyData extends SQLiteOpenHelper {


    private static final String LOG = "MyData";
    private static final int DATABASE_VERSION = 26;
    private static final String DATABASE_NAME = "my.db";

    // Table Names
    private static final String TABLE_ACTIVITY = "Activity";
    private static final String TABLE_STEPS = "Steps";



    // Common column names
    private static final String KEY_ID = "id";

    private static final String KEY_DISTANSE = "distanse";
    private static final String KEY_SPEED = "speed";
    private static final String KEY_KCAL = "kcal";
    private static final String KEY_DURATION = "duration";
    private static final String KEY_DATE_D = "dateD";
    private static final String KEY_DATE_M = "dateM";
    private static final String KEY_DATE_Y = "dateY";
    private static final String KEY_TIME_H = "timeM";
    private static final String KEY_TIME_M = "timeH";
    private static final String KEY_AKT = "akt";
    private static final String KEY_COUNT_STEPS = "countSteps";
    private static final String KEY_LAST_METER_STEPS = "lastMeterSteps";
    private SQLiteDatabase db;


    String CREATE_TABLE_ACTIVITY = "CREATE TABLE " + TABLE_ACTIVITY + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"

            + KEY_DISTANSE + " DOUBLE NOT NULL,"
            + KEY_SPEED + " DOUBLE NOT NULL,"
            + KEY_KCAL + " DOUBLE NOT NULL,"
            + KEY_DURATION + " STRING NOT NULL,"
            + KEY_DATE_D + " INTIGER NOT NULL,"
            + KEY_DATE_M + " INTIGER NOT NULL,"
            + KEY_DATE_Y + " INTIGER NOT NULL,"
            + KEY_TIME_M + " INTIGER NOT NULL,"
            + KEY_TIME_H + " INTIGER NOT NULL,"
      +KEY_AKT + " INTEGER NOT NULL"
            +")";

    String CREATE_TABLE_STEPS = "CREATE TABLE " + TABLE_STEPS + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_COUNT_STEPS + " INTIGER NOT NULL,"
            + KEY_LAST_METER_STEPS + " INTIGER NOT NULL,"
            + KEY_DATE_D + " INTIGER NOT NULL,"
            + KEY_DATE_M + " INTIGER NOT NULL,"
            + KEY_DATE_Y + " INTIGER NOT NULL"
            +")";
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_ACTIVITY);
        db.execSQL(CREATE_TABLE_STEPS);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVITY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STEPS);
        onCreate(db);
    }

    public MyData(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }
    public void addDayStep(int countSteps, int day, int month, int year){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_COUNT_STEPS, 0);
        values.put(KEY_LAST_METER_STEPS, countSteps);
        values.put(KEY_DATE_D, day);
        values.put(KEY_DATE_M, month);
        values.put(KEY_DATE_Y, year);
        db.insert(TABLE_STEPS,null, values);
    }

    public int isDay(int day, int month, int year) {
        SQLiteDatabase db = getReadableDatabase();
        String sql="SELECT * FROM "+TABLE_STEPS+" WHERE "+ KEY_DATE_D +"=? AND "+ KEY_DATE_M +"=? AND "+ KEY_DATE_Y +"=?";
        Cursor cursor= db.rawQuery(sql,new String[]{day+"",month+"",year+""});
        if(cursor.moveToFirst()) {
            int x=cursor.getInt(0);
            return  x;
        }
        return -1;
    }

    public int getcountfromday(int day, int month, int year)
    {
        SQLiteDatabase db = getReadableDatabase();
        String sql="SELECT * FROM "+TABLE_STEPS+" WHERE "+ KEY_DATE_D +"=? AND "+ KEY_DATE_M +"=? AND "+ KEY_DATE_Y +"=?";
        Cursor cursor= db.rawQuery(sql,new String[]{day+"",month+"",year+""});
        if(cursor.moveToFirst()) {
            int x=cursor.getInt(1);
            return  x;
        }
        return 0;
    }


    public Cursor getDayWhereid(int id)
    {
        SQLiteDatabase db = getReadableDatabase();
        String sql="SELECT * FROM "+TABLE_STEPS+" WHERE "+ KEY_ID+"=?";
        Cursor cursor= db.rawQuery(sql,new String[]{id+""});
        return cursor;
    }

    public void updateDay(int id, int count, int meterStep)
    {
        SQLiteDatabase db = getReadableDatabase();

            String where = KEY_ID + "=" + id;
            ContentValues updateValues = new ContentValues();
            updateValues.put(KEY_COUNT_STEPS, count);
            updateValues.put(KEY_LAST_METER_STEPS, meterStep);
            db.update(TABLE_STEPS,
                    updateValues,
                    where,
                    null);
    }


    public void addActivity(PhysicalActivity pa){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_DISTANSE, pa.getDistanse());
        values.put(KEY_SPEED, pa.getSpeed());
        values.put(KEY_KCAL, pa.getKacl());
        values.put(KEY_DURATION, pa.getDuration());
        values.put(KEY_DATE_D, pa.getDateD());
        values.put(KEY_DATE_M, pa.getDateM());
        values.put(KEY_DATE_Y, pa.getDateR());
        values.put(KEY_TIME_M, pa.getTimeM());
        values.put(KEY_TIME_H, pa.getTimeH());
        values.put(KEY_AKT, pa.getTypeActivity());

        db.insert(TABLE_ACTIVITY,null, values);
    }



    public PhysicalActivity getWhereId(int id){
        String[] kolumny={KEY_ID, KEY_DISTANSE, KEY_SPEED, KEY_KCAL, KEY_DURATION, KEY_DATE_D, KEY_DATE_M, KEY_DATE_Y,
                KEY_TIME_M, KEY_TIME_H,KEY_AKT};
        String selection =KEY_ID+" =?";
        String[] selectionArgs={""+id};
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor =db.query(TABLE_ACTIVITY,kolumny,selection ,selectionArgs,null,null,null);
        if(cursor!=null)
        {cursor.moveToFirst();
                PhysicalActivity pa = new PhysicalActivity();
                pa.setIdInData(cursor.getInt(0));
                pa.setDistanse(cursor.getDouble(1));
                pa.setSpeed(cursor.getDouble(2));
                pa.setKacl(cursor.getDouble(3));
                pa.setDuration(cursor.getString(4));
                pa.setDateD(cursor.getInt(5));
                pa.setDateM(cursor.getInt(6));
                pa.setDateR(cursor.getInt(7));
                pa.setTimeM(cursor.getInt(8));
                pa.setTimeH(cursor.getInt(9));
                pa.setTypeActivity(cursor.getInt(10));
            return pa;
        }
        PhysicalActivity af = new PhysicalActivity();
        return af;
    }

    public int rankingTOP(int typeActivity, int id){
        String[] columns={KEY_ID, KEY_DISTANSE, KEY_SPEED, KEY_KCAL, KEY_DURATION, KEY_DATE_D, KEY_DATE_M, KEY_DATE_Y, KEY_TIME_M, KEY_TIME_H,KEY_AKT};
        String selection =KEY_AKT+" =?";
        String[] selectionArgs={""+typeActivity};
        String orderBy = KEY_DISTANSE +" DESC";
        int count=1;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor =db.query(TABLE_ACTIVITY,columns,selection ,selectionArgs,null,null,orderBy);
        if(cursor.moveToFirst())
        {
            do{
                if(id==cursor.getInt(0))
                    return count ;
                count++;
            }while (cursor.moveToNext());
        }
        return 0;
    }


    public void delete(int id){
        SQLiteDatabase db = getWritableDatabase();
        String where = KEY_ID + "=" + id;
        db.delete(TABLE_ACTIVITY, where, null);
    }


    public List<PhysicalActivity> getAllAsList(int typeActivity, boolean orderByDate, boolean orderByTrip, boolean orderByDESC)
    {
        List<PhysicalActivity> physicalActivityList = new ArrayList<>();

        String[] columns={KEY_ID, KEY_DISTANSE, KEY_SPEED, KEY_KCAL, KEY_DURATION, KEY_DATE_D, KEY_DATE_M, KEY_DATE_Y, KEY_TIME_M, KEY_TIME_H,KEY_AKT};
        String selection =KEY_AKT+" =?";
        String[] selectionArgs={""+typeActivity};
        if(typeActivity==0){
            selection=null;
            selectionArgs=null;
        }
        String orderBy="";
        if(orderByTrip){
            orderBy = KEY_DISTANSE;
            if (orderByDESC) orderBy = orderBy + " DESC";
            else orderBy = orderBy + " ASC";
        }
        else if (orderByDate && orderByDESC) orderBy = KEY_DATE_Y +" DESC,"+ KEY_DATE_M +" DESC,"+ KEY_DATE_D +" DESC" ;
        else if (orderByDate && !orderByDESC)orderBy = KEY_DATE_Y +" ASC,"+ KEY_DATE_M +" ASC,"+ KEY_DATE_D +" ASC" ;
        else orderBy=null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor =db.query(TABLE_ACTIVITY,columns,selection ,selectionArgs,null,null,orderBy);
        if(cursor.moveToFirst())
        {
            do{
                PhysicalActivity pa = new PhysicalActivity();
                pa.setIdInData(cursor.getInt(0));
                pa.setDistanse(cursor.getDouble(1));
                pa.setSpeed(cursor.getDouble(2));
                pa.setKacl(cursor.getDouble(3));
                pa.setDuration(cursor.getString(4));
                pa.setDateD(cursor.getInt(5));
                pa.setDateM(cursor.getInt(6));
                pa.setDateR(cursor.getInt(7));
                pa.setTimeM(cursor.getInt(8));
                pa.setTimeH(cursor.getInt(9));
                pa.setTypeActivity(cursor.getInt(10));
                physicalActivityList.add(pa);

            }while (cursor.moveToNext());
        }
        return physicalActivityList;

    }



    public List<PhysicalActivity> getAllSelectionDate(int typeActivity, int day, int month, int year)
    {
        List<PhysicalActivity> physicalActivityList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor;
        if(typeActivity==0){
            String sql="SELECT * FROM "+ TABLE_ACTIVITY +" WHERE "+ KEY_DATE_D +"=? AND "+ KEY_DATE_M +"=? AND "+ KEY_DATE_Y +"=?";

            cursor= db.rawQuery(sql,new String[]{day+"",month+"",year+""});
        }
        else
        {
            String sql="SELECT * FROM "+ TABLE_ACTIVITY +" WHERE "+ KEY_AKT+"=? AND "+ KEY_DATE_D +"=? AND "+ KEY_DATE_M +"=? AND "+ KEY_DATE_Y +"=?";

            cursor= db.rawQuery(sql,new String[]{typeActivity+"",day+"",month+"",year+""});
        }

        if(cursor.moveToFirst())
        {
            do{
                PhysicalActivity pa = new PhysicalActivity();
                pa.setIdInData(cursor.getInt(0));
                pa.setDistanse(cursor.getDouble(1));
                pa.setSpeed(cursor.getDouble(2));
                pa.setKacl(cursor.getDouble(3));
                pa.setDuration(cursor.getString(4));
                pa.setDateD(cursor.getInt(5));
                pa.setDateM(cursor.getInt(6));
                pa.setDateR(cursor.getInt(7));
                pa.setTimeM(cursor.getInt(8));
                pa.setTimeH(cursor.getInt(9));
                pa.setTypeActivity(cursor.getInt(10));

                physicalActivityList.add(pa);

            }while (cursor.moveToNext());
        }
        return physicalActivityList;

    }



}

