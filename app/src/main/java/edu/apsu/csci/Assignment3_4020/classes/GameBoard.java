/*
    Authors: Daniel Davis, Dalton Claxton, Peyton White
    Date: 14 April 2020
    Description: A simple implementation of the classic game Simon
 */

package edu.apsu.csci.Assignment3_4020.classes;

import android.app.Activity;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import edu.apsu.csci.Assignment3_4020.R;

public abstract class GameBoard implements GamePiece.PushListener {
    private GamePiece[] board;
    private ArrayList<GamePiece> moves;
    private TextView indicator;

    private Activity activity;

    private SoundPool soundPool;

    GameBoard(Activity activity) {
        this.activity = activity;
        board = new GamePiece[4];
        indicator = activity.findViewById(R.id.indicator);
        moves = new ArrayList<>();
    }

    void initBoard() {
        ViewGroup gameBoard = activity.findViewById(R.id.gameBoardLL);
        int position = 0;

        for (int i = 0; i < gameBoard.getChildCount(); i++) {
            View child = gameBoard.getChildAt(i);

            if (child instanceof LinearLayout) {
                for (int j = 0; j < ((LinearLayout) child).getChildCount(); j++) {
                    if (((LinearLayout) child).getChildAt(j) instanceof GamePiece) {
                        GamePiece piece = (GamePiece) ((LinearLayout) child).getChildAt(j);

                        // Initialize board listeners
                        board[position] = activity.findViewById(piece.getId());
                        board[position].setPushListener(this);
                        position++;
                    }
                }
            }
        }
    }

    void loadSoundPool() {
        AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
        attrBuilder.setUsage(AudioAttributes.USAGE_GAME);

        SoundPool.Builder spBuilder = new SoundPool.Builder();
        spBuilder.setAudioAttributes(attrBuilder.build());
        spBuilder.setMaxStreams(4);
        soundPool = spBuilder.build();

        final int doId = soundPool.load(activity, R.raw.doo, 1);
        final int reId = soundPool.load(activity, R.raw.re, 1);
        final int miId = soundPool.load(activity, R.raw.mi, 1);
        final int faId = soundPool.load(activity, R.raw.fa, 1);

        // Associate game pieces with their sounds
        for (GamePiece piece : board) {
            switch (piece.getId()) {
                case R.id.topleft:
                    piece.setSoundListener(soundPool, doId);
                    piece.setTag(0);
                    break;
                case R.id.topright:
                    piece.setSoundListener(soundPool, reId);
                    piece.setTag(1);
                    break;
                case R.id.bottomleft:
                    piece.setSoundListener(soundPool, miId);
                    piece.setTag(2);
                    break;
                case R.id.bottomright:
                    piece.setSoundListener(soundPool, faId);
                    piece.setTag(3);
                    break;
                default:
                    break;
            }
        }
    }

    void releaseSoundPool() {
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
    }

    public void initMoves() {
        moves = new ArrayList<>();
    }

    public void enableBoard(boolean boardState) {
        for (GamePiece piece : board) {
            piece.enabled = boardState;
        }
    }

    GamePiece getBoardAt(int i) {
        return board[i];
    }

    public GamePiece getBoardAt(View v) {
        for (GamePiece piece : board) {
            if (piece.getId() == v.getId()) {
                return piece;
            }
        }
        return null;
    }

    public void setIndicator(String s) {
        this.indicator.setText(s);
    }

    public GamePiece getMove(int i) {
        return moves.get(i);
    }

    public void addMove(GamePiece p) {
        moves.add(p);
    }

    public int sizeOfMoves() {
        return moves.size();
    }

    int size() {
        return board.length;
    }
}
