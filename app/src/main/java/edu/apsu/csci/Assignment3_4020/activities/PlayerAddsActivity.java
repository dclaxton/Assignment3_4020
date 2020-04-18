/*
    Authors: Daniel Davis, Dalton Claxton, Peyton White
    Date: 14 April 2020
    Description: A simple implementation of the classic game Simon
 */

package edu.apsu.csci.Assignment3_4020.activities;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import edu.apsu.csci.Assignment3_4020.classes.GameLogic;

public class PlayerAddsActivity extends GameLogic {

    private boolean patternUpdating = false;

    public void makeMove(View v) {
        if (userPlaying) {
            if (patternUpdating && userMove == board.sizeOfMoves()) {
                board.addMove(board.getBoardAt(v));
                board.setIndicator("?");

                patternUpdating = false;
                userMove = 0;
                return;
            }

            if (v == board.getMove(userMove)) {
                board.setIndicator("" + (userMove + 1));
                userMove++;

                if (userMove == board.sizeOfMoves()) {
                    patternUpdating = true;
                }

            } else {
                userPlaying = false;
                dataSource.insertHighscore(3, board.sizeOfMoves() - 1);
                endGame(3, board.sizeOfMoves() - 1);

                userMove = 0;
                board.enableBoard(false);

                board.setIndicator("\u2718"); // X mark
            }
        }
    }

    protected void initializeSimon() {
        board.initMoves();
        simonMove = 0;
        incrementSimon();
    }

    protected void playSimon() {
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
