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
    public List<Integer> getAllHighscores(int whichGame) {
        open();

        List<Integer> highscores = new ArrayList<>();
        String columns[] =MySqlLiteHelper.HighscoreColumns.names(); //getWhichColumns(whichGame); //MySqlLiteHelper.HighscoreColumns.names();
        Cursor cursor = database.query(MySqlLiteHelper.DATA_TABLE,columns,null,null,null,null, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            Integer highscore = cursorToHighscore(cursor,whichGame);
            highscores.add(highscore);
            cursor.moveToNext();

        }
        cursor.close();
        return highscores;
    }
    private String[] getWhichColumns(int whichGame)
    {
        String[] c;
        if(whichGame == 1)
        {
            c = new String[]{"simon_says"};
        }
        else if(whichGame == 2)
        {
            c = new String[]{"simon_rewind"};
        }
        else
        {
            c = new String[]{"player_adds"};
        }
        return c;
    }





    // Inserts highscore into DB
    public void insertHighscore(int scoreRecorded,int whichGame) {
        ContentValues contentValues = new ContentValues();
        if(whichGame == 1)
        {
            contentValues.put(MySqlLiteHelper.HighscoreColumns.simon_says.toString(),scoreRecorded);
        }
        else if(whichGame == 2)
        {
            contentValues.put(MySqlLiteHelper.HighscoreColumns.player_adds.toString(),scoreRecorded);

        }
        else
        {
            contentValues.put(MySqlLiteHelper.HighscoreColumns.simon_rewind.toString(),scoreRecorded);
        }

        open();
        database.insert(MySqlLiteHelper.DATA_TABLE, null, contentValues);
    }


//going to need updating
    private Integer cursorToHighscore(Cursor cursor,int whichGame)
    {

        int highscore;

        //int scoreId = cursor.getInt(MySqlLiteHelper.HighscoreColumns.primary_key.ordinal());
        if(whichGame == 1)
        {
            highscore = cursor.getInt(MySqlLiteHelper.HighscoreColumns.simon_says.ordinal());
        }
        else if(whichGame == 2)
        {
            highscore = cursor.getInt(MySqlLiteHelper.HighscoreColumns.simon_rewind.ordinal());
        }
        else
        {
            highscore = cursor.getInt(MySqlLiteHelper.HighscoreColumns.player_adds.ordinal());
        }





        Integer highScore = new Integer(highscore);

        //String dateStr = cursor.getString(MySqlLiteHelper.HighscoreColumns.date_created.ordinal());


        return highScore;
    }







}
