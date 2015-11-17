package questionapp.richard.dev.com.questionapp; // Name of package

import android.os.Bundle; // 1) Stores a set of name-value pairs as the utility class
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar; // 3) Interactive alternative to Toast messages
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar; // 5) For using a toolbar within the application
import android.view.View;
import android.view.ViewGroup; // 7) Allows for other views named 'children'
import android.widget.ArrayAdapter; // 8) Handles a collection of data to return each item within the collection as a view
import android.widget.ListView; // 9) Displays items in a scrolling vertical list format
import android.widget.TextView; // 10) Displays text to user and provides user with option to edit the text
import android.widget.Toast; // 11) Displays a brief message to the user on screen

import java.security.Timestamp;
import java.text.SimpleDateFormat; // 13) Converts strings including date and or time information to and from java.util.Date objects
import java.util.ArrayList; // 14) Stores a range of data and expands as data is added
import java.util.List; // 15) Ordered collection of data
import java.util.Date; // 16) Contains the current date and time

import io.paperdb.Paper; // 17) Quick data storage in Android
import io.paperdb.PaperDbException; // 17) For use with Paper

public class HighScoreActivity extends AppCompatActivity { // 18) New publicly accessible activity as an extension

    private ListView listView; // 19) Private variable to display the list of records
    private List<HighScoreObject> highScores; // 20) Private variable to store records within array

    @Override // 21) Overrides the existing method currently in use
    protected void onCreate(Bundle savedInstanceState){ // 22) Can only be accessed its package and subclass of its class in a package elsewhere
        // 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        listView = (ListView)findViewById(R.id.listView);

        Paper.init(this);

        highScores = Paper.book().read("High scores", new ArrayList<HighScoreObject>());

        if (highScores.size() == 0) {
            Toast.makeText(this, "There are no records available.", Toast.LENGTH_SHORT).show();
        } else if (highScores.size() == 1) {
            Toast.makeText(this, "There is " + highScores.size() + " record available.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "There are " + highScores.size() + " records available.", Toast.LENGTH_SHORT).show();
        }

        HighScoreAdapter adapter = new HighScoreAdapter(highScores);

        listView.setAdapter(adapter);
    }

    private class HighScoreAdapter extends ArrayAdapter<HighScoreObject>{

        public HighScoreAdapter(List<HighScoreObject> items){
            super(HighScoreActivity.this, 0, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){

            if(convertView == null){
                convertView = getLayoutInflater().inflate(
                        R.layout.row_highscore, null);
            }

            // Get the highscore object for the row we're looking at
            HighScoreObject highScore = highScores.get(position);
            Date date = new Date(highScore.getTime());

            SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MM-yyyy");

            TextView lblTitle = (TextView)convertView.findViewById(R.id.lblTitle);

            lblTitle.setText(highScore.getScore() + " - " + highScore.getName() + " - " + fmtOut.format(date));

            return convertView;
        } // end get view
    } // end adapter class

}