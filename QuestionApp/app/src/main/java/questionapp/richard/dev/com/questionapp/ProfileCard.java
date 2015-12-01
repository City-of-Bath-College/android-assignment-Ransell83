package questionapp.richard.dev.com.questionapp;

// Required imports for class

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

// ************************************************************************************************
// The following class sets up the profile card activity to the screen
// ************************************************************************************************

public class ProfileCard extends AppCompatActivity {

    // onCreate method to set up activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_card); // Specifies related XML file

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
    }

    // Sets up menu for activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sendFeedback: // Finds XML id
                // Sets up intent to user to send feedback by user chosen method
                Intent Email = new Intent(Intent.ACTION_SEND);
                Email.setType("text/email");
                Email.putExtra(Intent.EXTRA_EMAIL, new String[]{"contactme@richard-ansell.com"});
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

}