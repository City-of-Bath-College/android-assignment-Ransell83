package questionapp.richard.dev.com.questionapp;

// Required imports for class

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

// ************************************************************************************************
// The following class sets up the introduction activity or referred to as the home page of the app
// ************************************************************************************************

public class IntroductionActivity extends AppCompatActivity {

    // onCreate method to set up activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction); // Specifies related XML file

        // Sets up ActionBar at top of screen
        Toolbar introductionToolbar = (Toolbar) findViewById(R.id.tool_bar); // Specifies related XML file
        setSupportActionBar(introductionToolbar); // Sets action bar

        // Sets colour of status bar
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.ColorPrimaryDark)); // Sets colour of status bar
        }

        // Find button attributes by XML id and stores these in Button variables
        Button btnPlay = (Button) findViewById(R.id.btnPlay);
        Button btnHighScore = (Button) findViewById(R.id.btnHighScore);
        Button btnAbout = (Button) findViewById(R.id.btnAbout);

        // Listen for clicks on the Play Button
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Opens MainActivity class if selected
                Intent i = new Intent(IntroductionActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        // Listen for clicks on the High Score Button
        btnHighScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Opens HighScoreActivity class if selected
                Intent i = new Intent(IntroductionActivity.this, HighScoreActivity.class);
                startActivity(i);
            }
        });

        // Listen for clicks on the About Button
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Opens ProfileCard class if selected
                Intent i = new Intent(IntroductionActivity.this, ProfileCard.class);
                startActivity(i);
            }
        });

    }

    // Sets up menu for activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sendFeedback: // Finds XML id
                // Sets up intent to user to send feedback by user chosen method
                Intent Email = new Intent(Intent.ACTION_SEND);
                Email.setType("text/email");
                Email.putExtra(Intent.EXTRA_EMAIL, new String[] { "contactme@richard-ansell.com" });
                Email.putExtra(Intent.EXTRA_SUBJECT, "Feedback about QuestionApp");
                Email.putExtra(Intent.EXTRA_TEXT, "Dear QuestionApp Developers," + "\n\n");
                startActivity(Intent.createChooser(Email, "Send Feedback:"));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Displays menu in activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present
        getMenuInflater().inflate(R.menu.menu, menu); // Finds XML id
        return true;
    }

    // onResume method when activity is resumed
    protected void onResume(){
        super.onResume();
        Paper.init(this);

        // Read High Scores from paper storage and stores values
        List<HighScoreObject> highScores = Paper.book().read("High scores", new ArrayList<HighScoreObject>());

        // Initialise variable
        int maxScore = 0;

        // Determine highest score
        for (int i = 0; i < highScores.size(); i++) {

            HighScoreObject h = highScores.get(i);

            if (h.getScore() > maxScore) {
                maxScore = h.getScore();
            }

        }

        // Find highestScore XML id and set value equal to highestScoreText TextView
        TextView highestScoreText = (TextView) findViewById(R.id.highestScore);

        // Change text of highestScoreText TextView and display highest score
        highestScoreText.setText("Current high score is: " + maxScore);
    }

}