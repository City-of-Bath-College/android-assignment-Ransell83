package questionapp.richard.dev.com.questionapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class IntroductionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);

        Button btnPlay = (Button) findViewById(R.id.btnPlay);
        Button btnHighScore = (Button) findViewById(R.id.btnHighScore);
        Button btnAbout = (Button) findViewById(R.id.btnAbout);
        Button btnClear = (Button) findViewById(R.id.btnClear);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(IntroductionActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        btnHighScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(IntroductionActivity.this, HighScoreActivity.class);
                startActivity(i);
            }
        });

        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(IntroductionActivity.this, ProfileCard.class);
                startActivity(i);
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<HighScoreObject> highScores = Paper.book().read("High scores", new ArrayList<HighScoreObject>());

                if (highScores.size() > 0) {
                    // Clear message
                    Paper.book().delete("High scores");
                    int maxScore = 0;

                    for (int i = 0; i < highScores.size(); i++) {

                        HighScoreObject h = highScores.get(i);

                        if (h.getScore() > maxScore) {
                            maxScore = h.getScore();
                        }

                    }
                    Toast.makeText(IntroductionActivity.this, "High scores have been cleared", Toast.LENGTH_SHORT).show();
                    TextView highestScoreText = (TextView) findViewById(R.id.highestScore);
                    highestScoreText.setText("Current high score is: " + maxScore);
                    Intent i = new Intent(IntroductionActivity.this, IntroductionActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(IntroductionActivity.this, "There are no records available.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    protected void onResume(){
        super.onResume();
        Paper.init(this);

        List<HighScoreObject> highScores = Paper.book().read("High scores", new ArrayList<HighScoreObject>());

        int maxScore = 0;

        for (int i = 0; i < highScores.size(); i++) {

            HighScoreObject h = highScores.get(i);

            if (h.getScore() > maxScore) {
                maxScore = h.getScore();
            }

        }
        TextView highestScoreText = (TextView) findViewById(R.id.highestScore);
        highestScoreText.setText("Current high score is: " + maxScore);
        Log.d("RICHARD_APP", "Reached onResume");
    }

    /*
    protected void onStop(){
        super.onStop();
        Paper.init(this);
        Paper.book().delete("High scores");
        Log.d("RICHARD_APP", "Reached onStop");
    }
    */

}