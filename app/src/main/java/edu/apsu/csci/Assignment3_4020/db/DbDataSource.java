/*
    Authors: Daniel Davis, Dalton Claxton, Peyton White
    Date: 14 April 2020
    Description: A simple implementation of the classic game Simon
 */

package edu.apsu.csci.Assignment3_4020.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DbDataSource {

    private SQLiteDatabase database;
    private MySqlLiteHelper databaseHelper;

    public DbDataSource(Context context)
    {
        databaseHelper = new MySqlLiteHelper(context);
    }

    public void open()
    {
        database = databaseHelper.getWritableDatabase();
    }

    public void close()
    {
        database.close();
    }

    // Gets all Highscores
    public List<String> getAllHighscores() {
        List<String> highscores = new ArrayList<>();
        String columns[] = MySqlLiteHelper.HighscoreColumns.names();
        Cursor cursor = database.query(MySqlLiteHelper.DATA_TABLE,columns,null,null,null,null, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            String highscore = cursorToHighscore(cursor);
            highscores.add(highscore);
            cursor.moveToNext();

        }
        cursor.close();
        return highscores;
    }

    // Inserts highscore into DB
    public void insertHighscore(String timeRecorded,int whichGame) {
        ContentValues contentValues = new ContentValues();
        if(whichGame == 1)
        {
            contentValues.put(MySqlLiteHelper.HighscoreColumns.simon_says.toString(),timeRecorded);
        }
        else if(whichGame == 2)
        {
            contentValues.put(MySqlLiteHelper.HighscoreColumns.player_adds.toString(),timeRecorded);

        }
        else
        {
            contentValues.put(MySqlLiteHelper.HighscoreColumns.simon_rewind.toString(),timeRecorded);
        }

        open();
        database.insert(MySqlLiteHelper.DATA_TABLE, null, contentValues);
    }



    private String cursorToHighscore(Cursor cursor)
    {
        String highScore = "";

        //int scoreId = cursor.getInt(MySqlLiteHelper.HighscoreColumns.primary_key.ordinal());

        String SimonSays = cursor.getString(MySqlLiteHelper.HighscoreColumns.simon_says.ordinal());


        //String dateStr = cursor.getString(MySqlLiteHelper.HighscoreColumns.date_created.ordinal());


        return highScore;
    }





}
