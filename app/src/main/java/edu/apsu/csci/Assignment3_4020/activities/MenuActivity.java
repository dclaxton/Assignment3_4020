/*
    Authors: Daniel Davis, Dalton Claxton, Peyton White
    Date: 14 April 2020
    Description: A simple implementation of the classic game Simon
 */

package edu.apsu.csci.Assignment3_4020.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import edu.apsu.csci.Assignment3_4020.R;
import edu.apsu.csci.Assignment3_4020.listeners.GoToActivity;

public class MenuActivity extends AppCompatActivity {
    public static boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MenuActivity.isOpen = true;
        setContentView(R.layout.activity_menu);

        // Menu buttons
        findViewById(R.id.simon_says_button).setOnClickListener(new GoToActivity(this, SimonSaysActivity.class));
        findViewById(R.id.player_adds_button).setOnClickListener(new GoToActivity(this, PlayerAddsActivity.class));
        findViewById(R.id.simon_rewind_button).setOnClickListener(new GoToActivity(this, SimonRewindActivity.class));
        findViewById(R.id.credits_button).setOnClickListener(new GoToActivity(this, CreditsActivity.class));
        findViewById(R.id.highscore_button).setOnClickListener(new GoToActivity(this, HighscoreActivity.class));
    }

    // Exit button
    public void onExitButton(final View view) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isOpen = false;
    }
}
