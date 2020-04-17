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
        highscores = dataSource.getAllHighscores();



        final TextView tv = findViewById(R.id.textviewhighscores);

        findViewById(R.id.buttonsimonsays).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.setText("");
                for(int i : highscores)
                {
                    tv.append(i + "\n");
                    Log.i("Highscores","S: " + i);
                }


            }
        });





    }
}
