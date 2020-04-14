/*
    Authors: Daniel Davis, Dalton Claxton, Peyton White
    Date: 14 April 2020
    Description: A simple implementation of the classic game Simon
 */

package edu.apsu.csci.Assignment3_4020.activities;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

import edu.apsu.csci.Assignment3_4020.R;
import edu.apsu.csci.Assignment3_4020.classes.GamePiece;

public class GameBoardActivity extends AppCompatActivity implements GamePiece.PushListener {

    private View leftTop, leftBottom, rightTop, rightBottom;
    private GamePiece red, green, blue, yellow;
    private GamePiece[] board;

    private TextView indicator;

    protected LinkedHashMap moves = new LinkedHashMap();
    public SoundPool soundPool;
    private Set<Integer> soundsLoaded;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_board);

        indicator = findViewById(R.id.indicator);

        board = new GamePiece[4];
        ViewGroup gameBoard = findViewById(R.id.gameBoardLL);
        int position = 0;

        for (int i = 0; i < gameBoard.getChildCount(); i++) {
            View child = gameBoard.getChildAt(i);

            if (child instanceof LinearLayout) {
                for (int j = 0; j < ((LinearLayout) child).getChildCount(); j++) {
                    if (((LinearLayout) child).getChildAt(j) instanceof GamePiece) {
                        GamePiece piece = (GamePiece) ((LinearLayout) child).getChildAt(j);

                        board[position] = findViewById(piece.getId());
                        board[position].setPushListener(piece.getPushListener());
                        position++;
                    }
                }
            }
        }

    }

    @Override
    public void onPush(View v) {

    }


}
