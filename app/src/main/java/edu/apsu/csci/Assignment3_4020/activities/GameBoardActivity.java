/*
    Authors: Daniel Davis, Dalton Claxton, Peyton White
    Date: 14 April 2020
    Description: A simple implementation of the classic game Simon
 */

package edu.apsu.csci.Assignment3_4020.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import edu.apsu.csci.Assignment3_4020.R;
import edu.apsu.csci.Assignment3_4020.classes.Alert;
import edu.apsu.csci.Assignment3_4020.classes.GamePiece;

public class GameBoardActivity extends AppCompatActivity implements GamePiece.PushListener {

    private View leftTop, leftBottom, rightTop, rightBottom;
    private GamePiece red, green, blue, yellow;
    private GamePiece[] board;

    protected TextView indicator;

    protected ArrayList<GamePiece> moves;
    protected int simonMove = 0;

    protected boolean userPlaying;
    protected int userMove = 0;

    private SoundPool soundPool;
    private Set<Integer> soundsLoaded;

    protected Alert alert;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_board);

        soundsLoaded = new HashSet<Integer>();

        indicator = findViewById(R.id.indicator);
        activateBoard();
        boardEnabled(false);

        alert = new Alert(this);
        alert.setPositiveButton(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dcl, int i) {
                if (i == DialogInterface.BUTTON_POSITIVE) {
                    (new Handler()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            initializeSimon();
                            playSimon();
                        }
                    }, 1000);
                }
            }
        });
        alert.showInstructions();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //load the sounds
        AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
        attrBuilder.setUsage(AudioAttributes.USAGE_GAME);

        SoundPool.Builder spBuilder = new SoundPool.Builder();
        spBuilder.setAudioAttributes(attrBuilder.build());
        //might need to set it to 1
        spBuilder.setMaxStreams(4);
        soundPool = spBuilder.build();

        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {

                if(status == 0)
                {
                    ///success
                    soundsLoaded.add(sampleId);
                    Log.i("Sound","Sound Loaded " + sampleId);
                }
                else {
                    //error not load sound log message + status var
                    Log.i("Sound","Sound Not Loaded");
                }
            }
        });

        //load sounds final for now but might based on listerners
        int dooId = soundPool.load(this,R.raw.doo,1);
        final int faId = soundPool.load(this,R.raw.fa,1);
        final int miId = soundPool.load(this,R.raw.mi,1);
        final int reId = soundPool.load(this,R.raw.re,1);





    }

    @Override
    protected void onStop() {
        super.onStop();
        //release the sounds
        if(soundPool != null)
        {
            soundPool.release();
            soundPool = null;

            soundsLoaded.clear();
        }



    }


    private void playSound(int soundId)
    {
        //play the sound
        if(soundsLoaded.contains(soundId))
        {
            soundPool.play(soundId,1.0f,1.0f,0,0,1.0f);
            Log.i("Sound","played sound");

        }
        else {
            Log.i("Sound","does not contain sound");
        }


    }




    private void activateBoard() {
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
                        board[position].setPushListener(this);
                        position++;
                    }
                }
            }
        }
    }

    private void boardEnabled(boolean b) {
        for (GamePiece piece : board) {
            piece.enabled = b;
            }
        }

    @Override
    public void onPush(View v) {
        if (userPlaying) {
            if (v == moves.get(userMove)) {
                indicator.setText("" + (userMove + 1));
                userMove++;

                if (userMove >= moves.size()) {
                    userPlaying = false;
                    userMove = 0;
                    indicator.setText("\u2714"); // Check mark

                    (new Handler()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            incrementSimon();
                        }
                    }, 1000);
                }
            } else {
                userPlaying = false;
                userMove = 0;
                indicator.setText("\u2718"); // X mark

                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initializeSimon();
                    }
                }, 3000);
            }
        }


    }

    protected void initializeSimon() {
        moves = new ArrayList<>();
        incrementSimon();
    }

    public void incrementSimon() {
        moves.add(board[(int) (Math.random() * board.length)]);
        playSimon();
    }

    protected void playSimon() {
        boardEnabled(false);
        indicator.setText("" + (simonMove + 1));

        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                moves.get(simonMove).on();

                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        moves.get(simonMove).off();

                        simonMove++;
                        if(simonMove < moves.size()) {
                            playSimon();
                        } else {
                            indicator.setText("?");
                            boardEnabled(true);

                            simonMove = 0;
                            userPlaying = true;
                        }
                    }
                }, 300);
            }
        }, 1000);
    }
}
