package edu.apsu.csci.Assignment3_4020.classes;

import android.app.AlertDialog;
import android.content.Context;

import edu.apsu.csci.Assignment3_4020.R;
import edu.apsu.csci.Assignment3_4020.activities.PlayerAddsActivity;
import edu.apsu.csci.Assignment3_4020.activities.SimonRewindActivity;
import edu.apsu.csci.Assignment3_4020.activities.SimonSaysActivity;

public class Alert {

    private AlertDialog.Builder aBuilder;
    private AlertDialog ad;

    public void Alerts() {
        aBuilder = null;
        ad = null;
    }

    public void showInstructions(Context c) {
        aBuilder = new AlertDialog.Builder(c);
        aBuilder.setPositiveButton("Play!", null);

        if (c instanceof SimonSaysActivity) {
            aBuilder.setMessage(R.string.simon_says_instructions);
        } else if (c instanceof PlayerAddsActivity) {
            aBuilder.setMessage(R.string.player_adds_instructions);
        } else if (c instanceof SimonRewindActivity) {
            aBuilder.setMessage(R.string.simon_rewind_instructions);
        }

        ad = aBuilder.create();
        ad.show();
    }
}
