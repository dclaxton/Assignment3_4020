/*
    Authors: Daniel Davis, Dalton Claxton, Peyton White
    Date: 14 April 2020
    Description: A simple implementation of the classic game Simon
 */

package edu.apsu.csci.Assignment3_4020.listeners;

import android.app.Activity;
import android.view.View;

import edu.apsu.csci.Assignment3_4020.activities.MenuActivity;
import edu.apsu.csci.Assignment3_4020.activities.MenuActivity;

public class GoToActivityWithClosing extends GoToActivity {
    public GoToActivityWithClosing(Activity fromActivity, Class<? extends Activity> toActivityClass) {
        super(fromActivity, toActivityClass);
    }

    // Prevents a menu from going back to the previous activity when 'Exit' is pressed
    @Override
    public void onClick(View v) {
        if (!MenuActivity.isOpen) {
            super.onClick(v);
        }
        fromActivity.finish();
    }
}