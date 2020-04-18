package edu.apsu.csci.Assignment3_4020.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.List;

import edu.apsu.csci.Assignment3_4020.R;
import edu.apsu.csci.Assignment3_4020.db.DbDataSource;


public class HighscoreActivity extends AppCompatActivity {

    private DbDataSource dataSource;
    private List<Integer> highscores;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_highscores);

        dataSource = new DbDataSource(this);

        //final TextView tv = findViewById(R.id.textviewhighscores);

        findViewById(R.id.buttonsimonsays).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                highscores = dataSource.getAllHighscores(1);
                showHighscores(highscores);

            }
        });

        findViewById(R.id.buttonplayeradd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                highscores = dataSource.getAllHighscores(3);
                showHighscores(highscores);
            }
        });

        findViewById(R.id.buttonsimonrewind).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                highscores = dataSource.getAllHighscores(2);
                showHighscores(highscores);
            }
        });
    }

    private void showHighscores(List<Integer> highscores) {
        final TextView tv = findViewById(R.id.textviewhighscores);
        tv.setText("");
        for (int i : highscores) {
            if (i > 0) {
                tv.append(i + "\n");
                Log.i("Highscores", "S: " + i);
            }
        }
    }

}
