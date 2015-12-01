package questionapp.richard.dev.com.questionapp;

// Required imports for class

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import io.paperdb.Paper;

// ************************************************************************************************
// The following class sets up high score activity to display all scores to the screen
// ************************************************************************************************

public class HighScoreActivity extends AppCompatActivity {

    // Variable declarations
    private ListView listView;
    private List<HighScoreObject> highScores;

    // onCreate method to set up activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score); // Specifies related XML file

        // Sets up ActionBar at top of screen
        Toolbar profileCardToolbar = (Toolbar) findViewById(R.id.tool_bar); // Specifies related XML file
        setSupportActionBar(profileCardToolbar); // Sets action bar

        // Sets colour of status bar
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.ColorPrimaryDark)); // Sets colour of status bar
        }

        // Get a support ActionBar corresponding to this toolbar to return to home screen
        ActionBar ab = getSupportActionBar();

        // Enable the Up button to return to home screen
        ab.setDisplayHomeAsUpEnabled(true);

        // Finds XML id and sets value equal to listView variable
        listView = (ListView) findViewById(R.id.listView);

        // Initialises paper storage in this context
        Paper.init(this);

        // Reads paper storage key for "High scores", stores values in a new ArrayList and sets value equal to highScores
        highScores = Paper.book().read("High scores", new ArrayList<HighScoreObject>());

        // Displays toast message specific to number of high score records in storage
        if (highScores.size() == 0) { // If highScores is equal to zero, display following message
            Toast.makeText(this, "There are no records available", Toast.LENGTH_SHORT).show();
        } else if (highScores.size() == 1) { // If highScores is equal to one, display following message
            Toast.makeText(this, "There is " + highScores.size() + " record available", Toast.LENGTH_SHORT).show();
        } else { // If highScores is greater than one, display following message
            Toast.makeText(this, "There are " + highScores.size() + " records available", Toast.LENGTH_SHORT).show();
        }

        // Sets adapter to store highScores
        HighScoreAdapter adapter = new HighScoreAdapter(highScores);

        // Sets listView equal to adapter
        listView.setAdapter(adapter);
    }

    // Sets up menu for activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clearHighScores: // Finds XML id

                // Deletes all high scores if value is greater than zero
                if (highScores.size() > 0) {

                    // Prompts user before deleting high scores
                    AlertDialog.Builder builder = new AlertDialog.Builder(this); // Sets up alert dialog
                    builder.setTitle("Delete high scores"); // Sets title
                    builder.setMessage("Are you sure you want to delete all of the high scores?"); // Sets message

                    // Set up the buttons
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { // Listens for user clicks on OK
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            // Clear paper storage of high scores
                            Paper.book().delete("High scores");

                            // Initialise variable
                            int maxScore = 0;

                            // Determine highest score
                            for (int i = 0; i < highScores.size(); i++) {
                                HighScoreObject h = highScores.get(i);
                                if (h.getScore() > maxScore) {
                                    maxScore = h.getScore();
                                }
                            }

                            // Display message to advise high scores have been cleared
                            Toast.makeText(HighScoreActivity.this, "High scores have been cleared", Toast.LENGTH_SHORT).show();

                            // Reload activity
                            Intent i = new Intent(HighScoreActivity.this, HighScoreActivity.class);
                            startActivity(i);
                        }
                    });

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() { // Listens for user clicks on Cancel
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel(); // Closes alert dialog
                        }
                    });
                    builder.show(); // Shows alert dialog

                } else {

                    // Display message if no records are available
                    Toast.makeText(HighScoreActivity.this, "There are no records available", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.sendFeedback: // Finds XML id
                // Sets up intent to user to send feedback by user chosen method
                Intent Email = new Intent(Intent.ACTION_SEND);
                Email.setType("text/email");
                Email.putExtra(Intent.EXTRA_EMAIL, new String[]{"contactme@richard-ansell.com"}); // Sets return email address for feedback
                Email.putExtra(Intent.EXTRA_SUBJECT, "Feedback about QuestionApp"); // Sets subject
                Email.putExtra(Intent.EXTRA_TEXT, "Dear QuestionApp Developers," + "\n\n"); // Sets text
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
        getMenuInflater().inflate(R.menu.menu_high_score, menu); // Finds XML id
        return true;
    }

    // Extends ArrayAdapter functionality
    private class HighScoreAdapter extends ArrayAdapter<HighScoreObject> {

        // Initialises number of items in HighScoreObject equal to zero
        public HighScoreAdapter(List<HighScoreObject> items) {
            super(HighScoreActivity.this, 0, items);
        }

        // Sets view for high scores
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // Organises highScores by highest score value first
            Collections.sort(highScores, Collections.reverseOrder());

            // Access row_highscore
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(
                        R.layout.row_highscore, null);
            }

            // Get the highscore object for the row we're looking at
            HighScoreObject highScore = highScores.get(position);
            Date date = new Date(highScore.getTime()); // Set date

            SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MM-yyyy"); // Set format of date

            // Find row attributes and store in TextView variables
            TextView lblScore = (TextView) convertView.findViewById(R.id.lblScore);
            TextView lblName = (TextView) convertView.findViewById(R.id.lblName);
            TextView lblDate = (TextView) convertView.findViewById(R.id.lblDate);

            // Change text values
            lblScore.setText(Integer.toString(highScore.getScore()));
            lblName.setText(highScore.getName());
            lblDate.setText(fmtOut.format(date));

            return convertView;
        }
    }

}