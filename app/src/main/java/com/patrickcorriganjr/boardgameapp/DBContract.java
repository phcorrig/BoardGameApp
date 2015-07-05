package com.patrickcorriganjr.boardgameapp;

import android.provider.BaseColumns;

/**
 * Created by Bag Boy Rebel on 6/13/2015.
 */
public final class DBContract {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "games.db";
    private static final String TEXT_TYPE          = " TEXT";
    private static final String BLOB_TYPE          = " BLOB";
    private static final String INT_TYPE          = " INTEGER";
    private static final String COMMA_SEP          = ",";

    private DBContract(){}

    public static abstract class GamesTable implements BaseColumns{
        public static final String TABLE_NAME = "GamesTable";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_PICTURE = "picture";
        public static final String COLUMN_NAME_MIN_PLAYERS = "minPlayers";
        public static final String COLUMN_NAME_MAX_PLAYERS = "maxPlayers";
        public static final String COLUMN_NAME_IDEAL_PLAYERS = "idealPlayers";
        public static final String COLUMN_NAME_CATEGORY = "category";
        public static final String COLUMN_NAME_PLAY_TIME = "playTime";
        public static final String COLUMN_NAME_RATING = "rating";
        public static final String COLUMN_NAME_TIMES_PLAYED = "timesPlayed";
        public static final String COLUMN_NAME_PURCHASE_DATE = "purchaseDate";
        public static final String COLUMN_NAME_DIFFICULTY = "difficulty";
        //public static final String COLUMN_NAME_EXPANSIONS = "expansions";


        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_PICTURE + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_MIN_PLAYERS + INT_TYPE + COMMA_SEP +
                COLUMN_NAME_MAX_PLAYERS + INT_TYPE + COMMA_SEP +
                COLUMN_NAME_IDEAL_PLAYERS + INT_TYPE + COMMA_SEP +
                COLUMN_NAME_CATEGORY + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_PLAY_TIME + INT_TYPE + COMMA_SEP +
                COLUMN_NAME_RATING + INT_TYPE + COMMA_SEP +
                COLUMN_NAME_TIMES_PLAYED + INT_TYPE + COMMA_SEP +
                COLUMN_NAME_PURCHASE_DATE + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_DIFFICULTY + TEXT_TYPE + ")";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    }
}
