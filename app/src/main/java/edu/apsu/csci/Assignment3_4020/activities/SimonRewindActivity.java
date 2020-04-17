/*
    Authors: Daniel Davis, Dalton Claxton, Peyton White
    Date: 14 April 2020
    Description: A simple implementation of the classic game Simon
 */

package edu.apsu.csci.Assignment3_4020.activities;

import android.os.Handler;
import android.view.View;

import edu.apsu.csci.Assignment3_4020.classes.GameLogic;

public class SimonRewindActivity extends GameLogic {

    private int userMove = 1;

    public void makeMove(View v) {
        if (userPlaying) {
            if (v == board.getMove(board.sizeOfMoves() - userMove)) {
                board.setIndicator("" + (userMove + 1));
                userMove++;

                if (userMove >= board.sizeOfMoves() + 1) {
                    userPlaying = false;
                    userMove = 1;
                    board.enableBoard(false);

                    board.setIndicator("\u2714"); // Check mark

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            incrementSimon();
                        }
                    }, 1000);
                }
            } else {
                userPlaying = false;
                dataSource.insertHighscore(userMove, 2);
                userMove = 1;
                board.enableBoard(false);

                board.setIndicator("\u2718"); // X mark

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initializeSimon();
                    }
                }, 2000);
            }
        }
    }
}
