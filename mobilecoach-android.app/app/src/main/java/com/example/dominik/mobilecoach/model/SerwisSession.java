package com.example.dominik.mobilecoach.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Dominik on 2015-10-01.
 */
public class SerwisSession extends SQLiteOpenHelper {

    Context context;
    private static final String DATABASE_NAME = "mobileCoach.db";
    private static final int VERSION = 1;

    private static final String TABLE_NAME = "TrainingPlan";
    private static final String TABLE_ACTIVITIES = "Activity";
    private static final String COLUMN_WEEK = "week";
    private static final String COLUMN_DAY = "day";
    private static final String COLUMN_ACCEPTED = "accepted";

    private static final String COLUMN_ACTIVITY = "activity";
    private final String COLUMN_TIME = "time";
    private final String COLUMN_PLAN_DAY = "idDay";

    private static String TABLE_SESSION = "sesion";
    private static String TABLE_TRACK = "track";
    private final String COLUMN_ID = "id";

    private final String COLUMN_WEIGHT = "weight";
    private final String COLUMN_CALORIES = "calories";
    private final String COLUMN_DISTANCE = "distance";
    private final String COLUMN_DATE = "date";

    private final String COLUMN_ID_SESSION = "idSesion";
    private final String COLUMN_LAT = "latitude";
    private final String COLUMN_LON = "longtidude";
    private final String COLUMN_SPEED = "speed";

    public SerwisSession(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, VERSION);
        this.context = context;
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRACK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SESSION);

        String query = "CREATE TABLE " + TABLE_SESSION + " ( " +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CALORIES + " INTEGER, " +
                COLUMN_SPEED + " REAL, " +
                COLUMN_DISTANCE + " REAL, " +
                COLUMN_WEIGHT + " REAL " +
                COLUMN_TIME + " INTEGER, " +
                COLUMN_DATE + " TEXT );";
        db.execSQL(query);

        query = "CREATE TABLE " + TABLE_TRACK + " ( " +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ID_SESSION + " INTEGER, " +
                COLUMN_LAT + " REAL, " +
                COLUMN_LON + " REAL, " +
                COLUMN_SPEED + " REAL, " +
                "FOREIGN KEY ( " + COLUMN_ID_SESSION + " ) REFERENCES " +
                TABLE_TRACK + " ( " + COLUMN_ID + " ));";
        db.execSQL(query);
    }

    public void addSession(TrainingSesion training, ArrayList<TrackTable> track) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put(COLUMN_CALORIES, training.getCalories());
        row.put(COLUMN_DISTANCE, training.getDistance());
        row.put(COLUMN_WEIGHT, training.getWeight());
        row.put(COLUMN_SPEED, training.getSpeed());
        row.put(COLUMN_DATE, training.getDate());
        row.put(COLUMN_TIME, training.getTime());
        long id = db.insert(TABLE_SESSION, null, row);

        if (id != -1) {
            for (int i = 0; i < track.size(); i++) {
                row = new ContentValues();
                row.put(COLUMN_ID_SESSION, id);
                row.put(COLUMN_LAT, track.get(i).getLatidude());
                row.put(COLUMN_LON, track.get(i).getLongitude());
                db.insert(TABLE_TRACK, null, row);
            }
        }

        db.close();
    }

    public ArrayList<TrainingSesion> getHistory() {

        ArrayList<TrainingSesion> list = new ArrayList<TrainingSesion>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SESSION, null);

        boolean spr = false;
        int indexId = cursor.getColumnIndex(COLUMN_ID);
        int indexCalories = cursor.getColumnIndex(COLUMN_CALORIES);
        int indexDistance = cursor.getColumnIndex(COLUMN_DISTANCE);
        int indexSpeed = cursor.getColumnIndex(COLUMN_SPEED);
        int indexWeigh = cursor.getColumnIndex(COLUMN_WEIGHT);
        int indexDate = cursor.getColumnIndex(COLUMN_DATE);
        int indexTime = cursor.getColumnIndex(COLUMN_TIME);

        spr = cursor.moveToFirst();
        while (spr) {

            list.add(new TrainingSesion(
                    cursor.getInt(indexId),
                    cursor.getFloat(indexWeigh),
                    cursor.getInt(indexCalories),
                    cursor.getFloat(indexDistance),
                    cursor.getFloat(indexSpeed),
                    cursor.getString(indexDate),
                    cursor.getInt(indexTime)
            ));
            spr = cursor.moveToNext();
        }

        cursor.close();
        db.close();
        return list;
    }

    public ArrayList<TrackTable> getTrace(int id) {

        ArrayList<TrackTable> track = new ArrayList<TrackTable>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TRACK + " WHERE " + COLUMN_ID_SESSION + "=?", new String[]{String.format("%d", id)});
        int indexLon = cursor.getColumnIndex(COLUMN_LON);
        int indexLat = cursor.getColumnIndex(COLUMN_LAT);
        int indexForeignKey = cursor.getColumnIndex(COLUMN_ID_SESSION);
        int indexid = cursor.getColumnIndex(COLUMN_ID);

        boolean spr = cursor.moveToFirst();
        while (spr) {

            track.add(new TrackTable(
                    cursor.getInt(indexid),
                    cursor.getInt(indexForeignKey),
                    cursor.getDouble(indexLon),
                    cursor.getDouble(indexLat)
            ));
            spr = cursor.moveToNext();
        }
        db.close();
        cursor.close();
        return track;
    }

    public TrainingSesion getSession(int id) {

        TrainingSesion trainingSesion = new TrainingSesion();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SESSION + " WHERE " + COLUMN_ID + "=?", new String[]{String.format("%d", id)});

        int indexId = cursor.getColumnIndex(COLUMN_ID);
        int indexCalories = cursor.getColumnIndex(COLUMN_CALORIES);
        int indexDistance = cursor.getColumnIndex(COLUMN_DISTANCE);
        int indexSpeed = cursor.getColumnIndex(COLUMN_SPEED);
        int indexWeigh = cursor.getColumnIndex(COLUMN_WEIGHT);
        int indexDate = cursor.getColumnIndex(COLUMN_DATE);
        int indexTime = cursor.getColumnIndex(COLUMN_TIME);

        cursor.moveToFirst();
        TrainingSesion sesion = new TrainingSesion(
                cursor.getInt(indexId),
                cursor.getFloat(indexWeigh),
                cursor.getInt(indexCalories),
                cursor.getFloat(indexDistance),
                cursor.getFloat(indexSpeed),
                cursor.getString(indexDate),
                cursor.getInt(indexTime));

        cursor.close();
        db.close();
        return sesion;
    }

    public void deleteSession(int id){

        SQLiteDatabase db = getWritableDatabase();
        db.rawQuery("DELETE FROM " + TABLE_SESSION + " WHERE " + id + " IN (SELECT " + COLUMN_ID_SESSION + "  FROM " + TABLE_TRACK + ");", null);
        db.delete(TABLE_TRACK, COLUMN_ID_SESSION + "=?", new String[]{ String.format("%d",id)});
        db.delete(TABLE_SESSION, COLUMN_ID + "=?", new String[]{String.format("%d", id)});
        db.close();
    }
}
