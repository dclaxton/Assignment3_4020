/*
    Authors: Daniel Davis, Dalton Claxton, Peyton White
    Date: 14 April 2020
    Description: A simple implementation of the classic game Simon
 */
package edu.apsu.csci.Assignment3_4020.activities;

import android.os.Bundle;

import edu.apsu.csci.Assignment3_4020.R;

public class SimonRewindActivity extends GameBoardActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        alert.showInstructions(this);
    }

    private void playSimonRewind() {

    }
}
