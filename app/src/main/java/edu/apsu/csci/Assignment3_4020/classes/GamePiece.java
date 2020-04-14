/*
    Authors: Daniel Davis, Dalton Claxton, Peyton White
    Date: 14 April 2020
    Description: A simple implementation of the classic game Simon
 */

/* GamePiece design credited to https://github.com/j-san */

package edu.apsu.csci.Assignment3_4020.classes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class GamePiece extends View implements View.OnTouchListener {
    private GradientDrawable drawable;

    public interface PushListener {
        public void onPush(View v);
    }

    private PushListener onPushListener;

    public GamePiece(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void onDraw(Canvas canvas) {
        setOnTouchListener(this);
        drawable = (GradientDrawable)this.getBackground();
    }

    @Override
    public boolean onTouch(View v, MotionEvent evt) {
        if(evt.getAction() == MotionEvent.ACTION_DOWN) {
            on();
        }
        if(evt.getAction() == MotionEvent.ACTION_UP) {
            off();
            if(onPushListener != null) {
                onPushListener.onPush(this);
            }
        }
        return true;
    }

    public void on() {
        drawable.setAlpha(0);
    }

    public void off() {
        drawable.setAlpha(255);
    }

    public PushListener getPushListener() {
        return onPushListener;
    }

    public void setPushListener(PushListener l) {
        this.onPushListener = l;
    }
}
