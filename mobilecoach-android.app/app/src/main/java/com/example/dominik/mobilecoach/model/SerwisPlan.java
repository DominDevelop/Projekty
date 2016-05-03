package com.example.dominik.mobilecoach.model;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.dominik.mobilecoach.activities.MainActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Dominik on 2015-10-10.
 */
public class SerwisPlan extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mobileCoach.db";
    private static final int VERSION = 1;

    // Plan Table
    private static final String TABLE_NAME = "TrainingPlan";
    private static final String TABLE_ACTIVITIES = "Activity";
    private static final String COLUMN_ID = "id";

    private static final String COLUMN_WEEK = "week";
    private static final String COLUMN_DAY = "day";
    private static final String COLUMN_ACCEPTED = "accepted";

    private static final String COLUMN_ACTIVITY = "activity";
    private final String COLUMN_TIME = "time";
    private final String COLUMN_PLAN_DAY = "idDay";

    // Session Tables
    private static String TABLE_SESSION = "sesion";
    private static String TABLE_TRACK = "track";

    private final String COLUMN_WEIGHT = "weight";
    private final String COLUMN_CALORIES = "calories";
    private final String COLUMN_DISTANCE = "distance";
    private final String COLUMN_SPEED = "speed";
    private final String COLUMN_DATE = "date";

    private final String COLUMN_ID_SESSION = "idSesion";
    private final String COLUMN_LAT = "latitude";
    private final String COLUMN_LON = "longtidude";

    public TrainingPlan[] trainingPlan;
    public Context context;

    public SerwisPlan(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, VERSION);
        this.context = context;
    }

    public static String getColumnActivity() {
        return COLUMN_ACTIVITY;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = " CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ACCEPTED + " INTEGER," +
                COLUMN_DAY + " INTEGER, " +
                COLUMN_WEEK + " INTEGER, " +
                COLUMN_DATE + " TEXT " + ");";
        db.execSQL(query);

        query = " CREATE TABLE " + TABLE_ACTIVITIES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PLAN_DAY + " INTEGER," +
                COLUMN_ACTIVITY + " TEXT, " +
                COLUMN_TIME + " INTEGER," +
                "FOREIGN KEY ( " + COLUMN_PLAN_DAY + " ) REFERENCES " +
                TABLE_NAME + " ( " + COLUMN_ID + " ));";
        db.execSQL(query);

        query = "CREATE TABLE " + TABLE_SESSION + " ( " +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CALORIES + " INTEGER, " +
                COLUMN_SPEED + " REAL, " +
                COLUMN_DISTANCE + " REAL, " +
                COLUMN_WEIGHT + " REAL, " +
                COLUMN_TIME + " INTEGER, " +
                COLUMN_DATE + " TEXT " + ");";
        db.execSQL(query);

        query = "CREATE TABLE " + TABLE_TRACK + " ( " +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ID_SESSION + " INTEGER, " +
                COLUMN_LAT + " REAL, " +
                COLUMN_LON + " REAL, " +
                "FOREIGN KEY ( " + COLUMN_ID_SESSION + " ) REFERENCES " +
                TABLE_TRACK + " ( " + COLUMN_ID + " ));";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVITIES);

        String query = " CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ACCEPTED + " INTEGER," +
                COLUMN_DAY + " INTEGER, " +
                COLUMN_WEEK + " INTEGER, " +
                COLUMN_DATE + " TEXT " + ");";
        db.execSQL(query);

        query = " CREATE TABLE " + TABLE_ACTIVITIES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PLAN_DAY + " INTEGER," +
                COLUMN_ACTIVITY + " TEXT, " +
                COLUMN_TIME + " INTEGER," +
                "FOREIGN KEY ( " + COLUMN_PLAN_DAY + " ) REFERENCES " +
                TABLE_NAME + " ( " + COLUMN_ID + " ));";
        db.execSQL(query);
    }

    public final String FILE_NAME = "DetailsPerson";
    public final String LEVEL_NAME = "level_plan";
    public void addPlan(int numberWeeks, int sportLevel) {

        SQLiteDatabase db = getWritableDatabase();
        onUpgrade(db, 0, 1);

        SharedPreferences.Editor editor = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit();
        editor.putInt(LEVEL_NAME,sportLevel).apply();

        int id = 1, numberToadd = 0;
        for (int week = 1; week <= numberWeeks; week++) {

            for (int day = 1; day <= 7; day++) {

                ContentValues row = new ContentValues();
                row.put(COLUMN_DAY, day);
                row.put(COLUMN_WEEK, week);
                row.put(COLUMN_ACCEPTED, false);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String date = simpleDateFormat.format(new Date());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                try {
                    c.setTime(sdf.parse(date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                c.add(Calendar.DATE, numberToadd);
                numberToadd++;
                date = sdf.format(c.getTime());
                row.put(COLUMN_DATE, date);
                db.insert(TABLE_NAME, null, row);

                switch (sportLevel) {
                    case 1:
                        beginerPlan(id, day);
                        break;
                    case 2:
                        intermediatePlan(id, day);
                        break;
                    case 3:
                        advancedPlan(id, day);
                        break;
                }
                id++;
            }
        }
        db.close();
    }

    public static final String BIEG = "Bieg";
    public static final String ROWER = "Rower";
    public static final String PLYWANIE = "Pływanie";
    public static final String REGENRACJA = "Regeneracja";

    public void beginerPlan(int id, int day) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues row;
        switch (day) {
            case 1:
                row = new ContentValues();
                row.put(COLUMN_ACTIVITY, BIEG);
                row.put(COLUMN_PLAN_DAY, id);
                row.put(COLUMN_TIME, 45);
                db.insert(TABLE_ACTIVITIES, null, row);
                break;
            case 2:
                row = new ContentValues();
                row.put(COLUMN_ACTIVITY, PLYWANIE);
                row.put(COLUMN_PLAN_DAY, id);
                row.put(COLUMN_TIME, 30);
                db.insert(TABLE_ACTIVITIES, null, row);
                break;
            case 3:
                row = new ContentValues();
                row.put(COLUMN_ACTIVITY, ROWER);
                row.put(COLUMN_PLAN_DAY, id);
                row.put(COLUMN_TIME, 60);
                db.insert(TABLE_ACTIVITIES, null, row);
                break;
            case 4:
                row = new ContentValues();
                row.put(COLUMN_ACTIVITY, BIEG);
                row.put(COLUMN_PLAN_DAY, id);
                row.put(COLUMN_TIME, 30);
                db.insert(TABLE_ACTIVITIES, null, row);
                break;
            case 5:
                row = new ContentValues();
                row.put(COLUMN_ACTIVITY, REGENRACJA);
                row.put(COLUMN_PLAN_DAY, id);
                row.put(COLUMN_TIME, 0);
                db.insert(TABLE_ACTIVITIES, null, row);
                break;
            case 6:
                row = new ContentValues();
                row.put(COLUMN_ACTIVITY, BIEG);
                row.put(COLUMN_PLAN_DAY, id);
                row.put(COLUMN_TIME, 60);
                db.insert(TABLE_ACTIVITIES, null, row);

                row = new ContentValues();
                row.put(COLUMN_ACTIVITY, ROWER);
                row.put(COLUMN_PLAN_DAY, id);
                row.put(COLUMN_TIME, 60);
                db.insert(TABLE_ACTIVITIES, null, row);

                row = new ContentValues();
                row.put(COLUMN_ACTIVITY, PLYWANIE);
                row.put(COLUMN_PLAN_DAY, id);
                row.put(COLUMN_TIME, 60);
                db.insert(TABLE_ACTIVITIES, null, row);
                break;
            case 7:
                row = new ContentValues();
                row.put(COLUMN_ACTIVITY, REGENRACJA);
                row.put(COLUMN_TIME, 0);
                row.put(COLUMN_PLAN_DAY, id);
                db.insert(TABLE_ACTIVITIES, null, row);
                break;
        }
    }

    public void intermediatePlan(int id, int day) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues row = new ContentValues();
        switch (day) {
            case 1:
                row.put(COLUMN_ACTIVITY, PLYWANIE);
                row.put(COLUMN_TIME, 60);
                row.put(COLUMN_PLAN_DAY, id);
                db.insert(TABLE_ACTIVITIES, null, row);
                break;
            case 2:
                row.put(COLUMN_ACTIVITY, ROWER);
                row.put(COLUMN_TIME, 75);
                row.put(COLUMN_PLAN_DAY, id);
                db.insert(TABLE_ACTIVITIES, null, row);
                break;
            case 3:
                row.put(COLUMN_ACTIVITY, PLYWANIE);
                row.put(COLUMN_TIME, 60);
                row.put(COLUMN_PLAN_DAY, id);
                db.insert(TABLE_ACTIVITIES, null, row);
                break;
            case 4:
                row.put(COLUMN_ACTIVITY, BIEG);
                row.put(COLUMN_TIME, 45);
                row.put(COLUMN_PLAN_DAY, id);
                db.insert(TABLE_ACTIVITIES, null, row);
                break;
            case 5:
                row.put(COLUMN_ACTIVITY, REGENRACJA);
                row.put(COLUMN_TIME, 0);
                row.put(COLUMN_PLAN_DAY, id);
                db.insert(TABLE_ACTIVITIES, null, row);
                break;
            case 6:
                row.put(COLUMN_ACTIVITY, PLYWANIE);
                row.put(COLUMN_TIME, 60);
                row.put(COLUMN_PLAN_DAY, id);
                db.insert(TABLE_ACTIVITIES, null, row);

                row = new ContentValues();
                row.put(COLUMN_ACTIVITY, ROWER);
                row.put(COLUMN_TIME, 60);
                row.put(COLUMN_PLAN_DAY, id);
                db.insert(TABLE_ACTIVITIES, null, row);

                row = new ContentValues();
                row.put(COLUMN_ACTIVITY, BIEG);
                row.put(COLUMN_TIME, 60);
                row.put(COLUMN_PLAN_DAY, id);
                db.insert(TABLE_ACTIVITIES, null, row);
                break;
            case 7:
                row.put(COLUMN_ACTIVITY, REGENRACJA);
                row.put(COLUMN_TIME, 0);
                row.put(COLUMN_PLAN_DAY, id);
                db.insert(TABLE_ACTIVITIES, null, row);
                break;
        }
    }

    public void advancedPlan(int id, int day) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues row = new ContentValues();
        switch (day) {
            case 1:
                row = new ContentValues();
                row.put(COLUMN_ACTIVITY, PLYWANIE);
                row.put(COLUMN_TIME, 60);
                row.put(COLUMN_PLAN_DAY, id);
                db.insert(TABLE_ACTIVITIES, null, row);

                row = new ContentValues();
                row.put(COLUMN_ACTIVITY, BIEG);
                row.put(COLUMN_TIME, 75);
                row.put(COLUMN_PLAN_DAY, id);
                db.insert(TABLE_ACTIVITIES, null, row);
                break;
            case 2:
                row.put(COLUMN_ACTIVITY, ROWER);
                row.put(COLUMN_TIME, 90);
                row.put(COLUMN_PLAN_DAY, id);
                db.insert(TABLE_ACTIVITIES, null, row);
                break;
            case 3:
                row = new ContentValues();
                row.put(COLUMN_ACTIVITY, PLYWANIE);
                row.put(COLUMN_TIME, 60);
                row.put(COLUMN_PLAN_DAY, id);
                db.insert(TABLE_ACTIVITIES, null, row);

                row = new ContentValues();
                row.put(COLUMN_ACTIVITY, BIEG);
                row.put(COLUMN_TIME, 30);
                row.put(COLUMN_PLAN_DAY, id);
                db.insert(TABLE_ACTIVITIES, null, row);
                break;
            case 4:
                row.put(COLUMN_ACTIVITY, ROWER);
                row.put(COLUMN_TIME, 75);
                row.put(COLUMN_PLAN_DAY, id);
                db.insert(TABLE_ACTIVITIES, null, row);
                break;
            case 5:
                row.put(COLUMN_ACTIVITY, PLYWANIE);
                row.put(COLUMN_TIME, 30);
                row.put(COLUMN_PLAN_DAY, id);
                db.insert(TABLE_ACTIVITIES, null, row);
                break;
            case 6:
                row = new ContentValues();
                row.put(COLUMN_ACTIVITY, PLYWANIE);
                row.put(COLUMN_TIME, 145);
                row.put(COLUMN_PLAN_DAY, id);
                db.insert(TABLE_ACTIVITIES, null, row);

                row = new ContentValues();
                row.put(COLUMN_ACTIVITY, ROWER);
                row.put(COLUMN_TIME, 240);
                row.put(COLUMN_PLAN_DAY, id);
                db.insert(TABLE_ACTIVITIES, null, row);

                row = new ContentValues();
                row.put(COLUMN_ACTIVITY, BIEG);
                row.put(COLUMN_TIME, 240);
                row.put(COLUMN_PLAN_DAY, id);
                db.insert(TABLE_ACTIVITIES, null, row);
                break;
            case 7:
                row.put(COLUMN_ACTIVITY, REGENRACJA);
                row.put(COLUMN_TIME, 0);
                row.put(COLUMN_PLAN_DAY, id);
                db.insert(TABLE_ACTIVITIES, null, row);
                break;
        }
    }

    public TrainingPlan[] getPlanTable() {

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_DATE, null);
        cursor.moveToFirst();

        int indexDate = cursor.getColumnIndex(COLUMN_DATE);
        int indexWeek = cursor.getColumnIndex(COLUMN_WEEK);
        int indexAccepted = cursor.getColumnIndex(COLUMN_ACCEPTED);
        int indexDay = cursor.getColumnIndex(COLUMN_DAY);
        int indexId = cursor.getColumnIndex(COLUMN_ID);

        TrainingPlan plan[] = new TrainingPlan[cursor.getCount()];
        int i = 0;
        boolean spr = true;
        while (spr) {

            plan[i] = new TrainingPlan(
                    cursor.getInt(indexId),
                    cursor.getInt(indexWeek),
                    cursor.getInt(indexDay),
                    cursor.getInt(indexAccepted) > 0,
                    cursor.getString(indexDate)
            );
            i++;
            spr = cursor.moveToNext();
        }

        cursor.close();
        db.close();
        trainingPlan = plan;
        return plan;
    }

    public void updateAcceptetColumn(int id) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ACCEPTED, true);
        try {
            db.update(TABLE_NAME, contentValues, "id=" + id, null);
        } catch (Exception e) {
            Toast.makeText(MainActivity.activity.getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        db.close();
    }

    public Cursor getActivities(int id) {

        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_ACTIVITIES + " WHERE " + COLUMN_PLAN_DAY + "=" + id, null);
    }

    public void checkPlan() throws ParseException {

        if (trainingPlan == null) {
            trainingPlan = this.getPlanTable();
        }

        ArrayList<TrainingPlan> listToAdd = new ArrayList<TrainingPlan>();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date dateNow = format.parse(format.format(new Date()));

        // TESTOWY //
        //  dateNow = format.parse("2016-01-31");
        /////////

        Date date;
        SQLiteDatabase db = getWritableDatabase();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date lastDay = sdf.parse(trainingPlan[trainingPlan.length - 1].getDate());
        String lastDate = format.format(lastDay);

        int week = trainingPlan[trainingPlan.length - 1].getWeek();
        int lastNumberDay = trainingPlan[trainingPlan.length - 1].getDay();
        String dateTxt = trainingPlan[trainingPlan.length - 1].getDate();
        int numberToadd = 1;

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ACTIVITIES, null);
        cursor.moveToFirst();
        String buf;
        boolean spr = false;
        int indexActivities = cursor.getColumnIndex(COLUMN_ACTIVITY);

        for (TrainingPlan row : trainingPlan) {

            date = format.parse(row.getDate());
            cursor = db.rawQuery("SELECT * FROM " + TABLE_ACTIVITIES + " WHERE idDay=" + row.getId(), null);
            cursor.moveToFirst();
            buf = cursor.getString(indexActivities);

            if (dateNow.compareTo(date) > 0 && !row.isAccepted() && !buf.contains("Regeneracja")) {
                Log.e("kliknieto", format.parse(format.format(new Date())) + " > " + row.getDate());

                ContentValues contentValues = new ContentValues();
                contentValues.put(COLUMN_ID, row.getId());
                contentValues.put(COLUMN_ACCEPTED, false);

                Calendar c = Calendar.getInstance();
                c.setTime(sdf.parse(lastDate));
                c.add(Calendar.DATE, numberToadd);
                numberToadd++;

                Log.e("DZIEN ", " day :" + lastNumberDay + "; week : " + week);
                if (lastNumberDay == 7 && spr == false) {
                    spr = true;
                    week++;
                    lastNumberDay = 1;
                }
                if (lastNumberDay > 7){
                    week++;
                    lastNumberDay = 1;
                }
                Log.e("DZIEN ", " day :" + lastNumberDay + "; week : " + week);
                contentValues.put(COLUMN_DAY, lastNumberDay++);
                contentValues.put(COLUMN_WEEK, week);
                String dat = sdf.format(c.getTime());
                contentValues.put(COLUMN_DATE, dat);
                db.update(TABLE_NAME, contentValues, "id=" + row.getId(), null);
            }
        }
    }
}
