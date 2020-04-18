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

    // Starts at one for the first move
    private int userMove = 1;

    public void makeMove(View v) {
        if (userPlaying) {
            // Increments the sequence if correct
            if (v == board.getMove(board.sizeOfMoves() - userMove)) {
                board.setIndicator("" + (userMove + 1));
                userMove++;

                // If the correct sequence is followed in reverse
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
                // Incorrect move is made
                userPlaying = false;
                dataSource.insertHighScore(2, board.sizeOfMoves() - 1);
                endGame(2, board.sizeOfMoves() - 1);

                userMove = 1;
                board.enableBoard(false);

                board.setIndicator("\u2718"); // X mark
            }
        }
    }
}
