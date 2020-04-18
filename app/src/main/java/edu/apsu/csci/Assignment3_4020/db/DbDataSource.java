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

import java.util.ArrayList;
import java.util.List;

public class DbDataSource {

    private SQLiteDatabase database;
    private MySqlLiteHelper databaseHelper;

    public DbDataSource(Context context) {
        databaseHelper = new MySqlLiteHelper(context);
    }

    private void open() {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        database.close();
    }

    private List<Integer> getAllHighScores(int gameID) {
        open();

        List<Integer> highScores = new ArrayList<>();
        String[] columns = MySqlLiteHelper.HighScoreColumns.names();
        Cursor cursor = database.query(MySqlLiteHelper.DATA_TABLE, columns, null, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Integer high_score = cursorToHighScore(cursor, gameID);
            highScores.add(high_score);
            cursor.moveToNext();
        }

        cursor.close();
        return highScores;
    }

    public void insertHighScore(int gameID, int scoreRecorded) {
        ContentValues contentValues = new ContentValues();
        if (gameID == 1) {
            contentValues.put(MySqlLiteHelper.HighScoreColumns.simon_says.toString(), scoreRecorded);
        } else if (gameID == 2) {
            contentValues.put(MySqlLiteHelper.HighScoreColumns.player_adds.toString(), scoreRecorded);
        } else {
            contentValues.put(MySqlLiteHelper.HighScoreColumns.simon_rewind.toString(), scoreRecorded);
        }

        open();
        database.insert(MySqlLiteHelper.DATA_TABLE, null, contentValues);
    }

    private Integer cursorToHighScore(Cursor cursor, int gameID) {
        int high_score;

        if (gameID == 1) {
            high_score = cursor.getInt(MySqlLiteHelper.HighScoreColumns.simon_says.ordinal());
        } else if (gameID == 2) {
            high_score = cursor.getInt(MySqlLiteHelper.HighScoreColumns.player_adds.ordinal());
        } else {
            high_score = cursor.getInt(MySqlLiteHelper.HighScoreColumns.simon_rewind.ordinal());
        }

        Integer highScore;
        highScore = high_score;

        return highScore;
    }

    public String getHighScore(int gameID) {
        List<Integer> highScores = getAllHighScores(gameID);
        int highScore = highScores.get(0);

        // Determines high score
        for (int i : highScores) {
            if (i > highScore) {
                highScore = i;
            }
        }

        return String.valueOf(highScore);
    }

}
