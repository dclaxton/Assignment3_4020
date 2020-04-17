/*
    Authors: Daniel Davis, Dalton Claxton, Peyton White
    Date: 14 April 2020
    Description: A simple implementation of the classic game Simon
 */

package edu.apsu.csci.Assignment3_4020.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySqlLiteHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "highscores.sqlite";
    private static final int DB_VERSION = 1;
    static final String DATA_TABLE = "Data";

    // DB columns
    public enum HighscoreColumns
    {
        primary_key,
        simon_says,
        player_adds,
        simon_rewind;

        public static String[] names()
        {
            HighscoreColumns[] v = values();
            String[] names = new String[v.length];
            for(int i = 0; i < v.length;i++)
            {
                names[i] = v[i].toString();
            }
            return names;
        }
    }

    MySqlLiteHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create table with created columns
        String sql = "CREATE TABLE " + DATA_TABLE + " (" +
                HighscoreColumns.primary_key + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                HighscoreColumns.simon_says + " INTEGER , " +
                HighscoreColumns.player_adds + " INTEGER ," +
                HighscoreColumns.simon_rewind + " INTEGER  " +
                ");";

        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
