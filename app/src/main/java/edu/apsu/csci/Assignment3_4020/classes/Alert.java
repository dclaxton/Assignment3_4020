package edu.apsu.csci.Assignment3_4020.classes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import edu.apsu.csci.Assignment3_4020.R;
import edu.apsu.csci.Assignment3_4020.activities.PlayerAddsActivity;
import edu.apsu.csci.Assignment3_4020.activities.SimonRewindActivity;
import edu.apsu.csci.Assignment3_4020.activities.SimonSaysActivity;

class Alert {
    private AlertDialog.Builder aBuilder;
    private AlertDialog ad;
    private Context c;

    Alert(Context c) {
        aBuilder = new AlertDialog.Builder(c);
        ad = null;
        this.c = c;
    }

    void showInstructions() {
        aBuilder.setTitle("Instructions");

        if (c instanceof SimonSaysActivity) {
            aBuilder.setMessage(R.string.simon_says_instructions);
        } else if (c instanceof PlayerAddsActivity) {
            aBuilder.setMessage(R.string.player_adds_instructions);
        } else if (c instanceof SimonRewindActivity) {
            aBuilder.setMessage(R.string.simon_rewind_instructions);
        }

        buildDialog();
    }

    void showScores(int score, String highScore) {
        aBuilder.setTitle("Game Over");
        aBuilder.setMessage("Score: " + score + "\nHigh Score: " + highScore);

        buildDialog();
    }

    private void buildDialog() {
        ad = aBuilder.create();
        ad.setCancelable(false);
        ad.setCanceledOnTouchOutside(false);

        ad.show();
    }

    void setPositiveButton(String s, DialogInterface.OnClickListener dcl) {
        aBuilder.setPositiveButton(s, dcl);
    }

    private void setNegativeButton(String s, DialogInterface.OnClickListener dcl) {
        aBuilder.setNegativeButton(s, dcl);
    }

    void backToMenu(final Activity a) {
        setNegativeButton("Menu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (i == DialogInterface.BUTTON_NEGATIVE) {
                    a.finish();
                }
            }
        });
    }
}
