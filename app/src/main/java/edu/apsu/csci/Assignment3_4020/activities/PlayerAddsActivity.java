/*
    Authors: Daniel Davis, Dalton Claxton, Peyton White
    Date: 14 April 2020
    Description: A simple implementation of the classic game Simon
 */

package edu.apsu.csci.Assignment3_4020.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import edu.apsu.csci.Assignment3_4020.R;
import edu.apsu.csci.Assignment3_4020.classes.Alert;
import edu.apsu.csci.Assignment3_4020.classes.GameBoard;
import edu.apsu.csci.Assignment3_4020.db.DbDataSource;

public class PlayerAddsActivity extends AppCompatActivity {
    private GameBoard board;
    private boolean userPlaying;
    private boolean patternUpdating = false;

    private int userMove = 0;
    private int simonMove = 0;

    private DbDataSource dataSource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_board);

        dataSource = new DbDataSource(this);

        board = new GameBoard(this) {
            @Override
            public void onPush(View v) {
                if (userPlaying) {
                    if (patternUpdating && userMove == board.sizeOfMoves()) {
                        board.addMove(board.getBoardAt(v));
                        board.setIndicator("?");

                        patternUpdating = false;
                        userMove = 0;
                        return;
                    }

                    if (v == board.getMove(userMove)) {
                        setIndicator("" + (userMove + 1));
                        userMove++;

                     if (userMove == board.sizeOfMoves()) {
                         patternUpdating = true;
                     }

                    } else {
                        userPlaying = false;
                        dataSource.insertHighscore(userMove,3);
                        userMove = 0;
                        enableBoard(false);

                        setIndicator("\u2718"); // X mark

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                initializeSimon();
                            }
                        }, 2000);
                    }
                }
            }
        };

        board.initBoard();
        board.enableBoard(false);

        Alert alert = new Alert(this);
        alert.setPositiveButton(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dcl, int i) {
                if (i == DialogInterface.BUTTON_POSITIVE) {
                    new Handler().postDelayed(new Runnable() {
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
    public void onStart() {
        super.onStart();
        board.loadSoundPool();
    }

    @Override
    public void onStop() {
        super.onStop();
        board.releaseSoundPool();
    }

    protected void initializeSimon() {
        board.initMoves();
        simonMove = 0;
        incrementSimon();
    }

    public void incrementSimon() {
        board.addMove(board.getBoardAt((int) (Math.random() * board.size())));
        playSimon();
    }

    protected  void playSimon() {
        board.enableBoard(false);
        board.setIndicator("" + (simonMove + 1));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                board.getMove(simonMove).on();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        board.getMove(simonMove).off();
                        board.setIndicator("?");
                        board.enableBoard(true);
                        userPlaying = true;
                    }
                }, 300);
            }
        }, 750);
    }
}
