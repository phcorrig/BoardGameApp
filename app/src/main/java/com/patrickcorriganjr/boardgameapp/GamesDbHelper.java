package com.patrickcorriganjr.boardgameapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

/**
 * Created by Bag Boy Rebel on 6/13/2015.
 */
public class GamesDbHelper extends SQLiteOpenHelper {



    public GamesDbHelper(Context context){
        super(context, DBContract.DATABASE_NAME, null, DBContract.DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBContract.GamesTable.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DBContract.GamesTable.DELETE_TABLE);
        onCreate(db);
    }

    public void deleteTable(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(DBContract.GamesTable.DELETE_TABLE);
    }

    public long insertEntry(String name, String picturePath, String minPlayers, String maxPlayers, String idealPlayers, String category, String playTime, String rating, String timesPlayed, String purchaseDate, String difficulty){
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBContract.GamesTable.COLUMN_NAME_NAME, name);
        values.put(DBContract.GamesTable.COLUMN_NAME_PICTURE, picturePath);
        values.put(DBContract.GamesTable.COLUMN_NAME_MIN_PLAYERS, minPlayers);
        values.put(DBContract.GamesTable.COLUMN_NAME_MAX_PLAYERS, maxPlayers);
        values.put(DBContract.GamesTable.COLUMN_NAME_IDEAL_PLAYERS, idealPlayers);
        values.put(DBContract.GamesTable.COLUMN_NAME_CATEGORY, category);
        values.put(DBContract.GamesTable.COLUMN_NAME_PLAY_TIME, playTime);
        values.put(DBContract.GamesTable.COLUMN_NAME_RATING, rating);
        values.put(DBContract.GamesTable.COLUMN_NAME_TIMES_PLAYED, timesPlayed);
        values.put(DBContract.GamesTable.COLUMN_NAME_PURCHASE_DATE, purchaseDate);
        values.put(DBContract.GamesTable.COLUMN_NAME_DIFFICULTY, difficulty);

        return db.insert(DBContract.GamesTable.TABLE_NAME, null, values);
    }

    public Cursor getAllEntries(){
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
            DBContract.GamesTable._ID,
            DBContract.GamesTable.COLUMN_NAME_NAME,
            DBContract.GamesTable.COLUMN_NAME_PICTURE,
            DBContract.GamesTable.COLUMN_NAME_MIN_PLAYERS,
            DBContract.GamesTable.COLUMN_NAME_MAX_PLAYERS,
            DBContract.GamesTable.COLUMN_NAME_IDEAL_PLAYERS,
            DBContract.GamesTable.COLUMN_NAME_CATEGORY,
            DBContract.GamesTable.COLUMN_NAME_PLAY_TIME,
            DBContract.GamesTable.COLUMN_NAME_RATING,
            DBContract.GamesTable.COLUMN_NAME_TIMES_PLAYED,
            DBContract.GamesTable.COLUMN_NAME_PURCHASE_DATE,
            DBContract.GamesTable.COLUMN_NAME_DIFFICULTY
        };

        String sortOrder = DBContract.GamesTable.COLUMN_NAME_NAME + " Desc";

        Cursor cursor = db.query(
                DBContract.GamesTable.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        return cursor;
    }

    public Cursor getEntry(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                DBContract.GamesTable._ID,
                DBContract.GamesTable.COLUMN_NAME_NAME,
                DBContract.GamesTable.COLUMN_NAME_PICTURE,
                DBContract.GamesTable.COLUMN_NAME_MIN_PLAYERS,
                DBContract.GamesTable.COLUMN_NAME_MAX_PLAYERS,
                DBContract.GamesTable.COLUMN_NAME_IDEAL_PLAYERS,
                DBContract.GamesTable.COLUMN_NAME_CATEGORY,
                DBContract.GamesTable.COLUMN_NAME_PLAY_TIME,
                DBContract.GamesTable.COLUMN_NAME_RATING,
                DBContract.GamesTable.COLUMN_NAME_TIMES_PLAYED,
                DBContract.GamesTable.COLUMN_NAME_PURCHASE_DATE,
                DBContract.GamesTable.COLUMN_NAME_DIFFICULTY
        };

        String whereClause = DBContract.GamesTable._ID + " =?";
        String[] idArr = { String.valueOf(id) };

        Cursor cursor = db.query(
                DBContract.GamesTable.TABLE_NAME,
                projection,
                whereClause,
                idArr,
                null,
                null,
                null//sortOrder
        );
        cursor.moveToFirst();

        return cursor;
    }

    public void deleteEntry(int ID){
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = DBContract.GamesTable._ID + " LIKE ?";

        String[] selectionArgs = {String.valueOf(ID) };

        db.delete(DBContract.GamesTable.TABLE_NAME, selection, selectionArgs);
    }

    public void updateEntry(String ID, String name, String picture, String minPlayers, String maxPlayers, String idealPlayers, String category, String playTime, String rating, String timesPlayed, String purchaseDate, String difficulty){
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBContract.GamesTable.COLUMN_NAME_NAME, name);
        values.put(DBContract.GamesTable.COLUMN_NAME_PICTURE, picture);
        values.put(DBContract.GamesTable.COLUMN_NAME_MIN_PLAYERS, minPlayers);
        values.put(DBContract.GamesTable.COLUMN_NAME_MAX_PLAYERS, maxPlayers);
        values.put(DBContract.GamesTable.COLUMN_NAME_IDEAL_PLAYERS, idealPlayers);
        values.put(DBContract.GamesTable.COLUMN_NAME_CATEGORY, category);
        values.put(DBContract.GamesTable.COLUMN_NAME_PLAY_TIME, playTime);
        values.put(DBContract.GamesTable.COLUMN_NAME_RATING, rating);
        values.put(DBContract.GamesTable.COLUMN_NAME_TIMES_PLAYED, timesPlayed);
        values.put(DBContract.GamesTable.COLUMN_NAME_PURCHASE_DATE, purchaseDate);
        values.put(DBContract.GamesTable.COLUMN_NAME_DIFFICULTY, difficulty);

        String selection = DBContract.GamesTable._ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(ID)};

        db.update(DBContract.GamesTable.TABLE_NAME, values, selection, selectionArgs);
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        if(bitmap == null)
            return  null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }
}
