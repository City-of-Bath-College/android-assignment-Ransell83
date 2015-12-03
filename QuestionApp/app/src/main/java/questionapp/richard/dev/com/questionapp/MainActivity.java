package questionapp.richard.dev.com.questionapp;

// Required imports for class
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.paperdb.Paper;

// ************************************************************************************************
// The following class sets up the main activity for the questions to be displayed to the screen
// for the user to begin answering the questionnaire
// ************************************************************************************************

public class MainActivity extends AppCompatActivity {

    // Variable declarations
    public int questionProgressValue;
    String[] FirebaseQuestions = new String[24];
    Boolean[] FirebaseAnswers = new Boolean[24];
    String[] FirebaseImages = new String[24];
    String[] SavedQuestions = new String[24];
    Boolean[] SavedAnswers = new Boolean[24];
    String[] SavedImages = new String[24];
    MediaPlayer player;
    boolean buttonSound = true;
    boolean vibrationEffect = true;
    private TextView question;
    private Button btnTrue;
    private Button btnFalse;
    private ImageView imgFlags;
    private List<QuestionObject> questions;
    private QuestionObject currentQuestion;
    private int index;
    private int score;
    private TextView scoreInGame;
    private ProgressBar progressBar;
    private int valuePerQuestion;
    private String name = "";
    private Firebase mRef;

    // onCreate method to set up activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Specifies related XML file

        // Sets up ActionBar at top of screen
        Toolbar mainToolbar = (Toolbar) findViewById(R.id.tool_bar); // Specifies related XML file
        setSupportActionBar(mainToolbar); // Sets action bar

        // Sets up the external database (Firebase) to be used in this context
        Firebase.setAndroidContext(this);

        // Sets colour of status bar
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.ColorPrimaryDark)); // Sets colour of status bar
        }

        // Connect variables declared above to items on activity_main.xml file
        question = (TextView) findViewById(R.id.question);
        btnTrue = (Button) findViewById(R.id.btnTrue);
        btnFalse = (Button) findViewById(R.id.btnFalse);
        imgFlags = (ImageView) findViewById(R.id.imgFlag);
        scoreInGame = (TextView) findViewById(R.id.scoreInGame);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.getProgressDrawable().setColorFilter(Color.rgb(63, 81, 181), PorterDuff.Mode.SRC_IN); // Change colour of progressBar to match theme

        // Sets initial text for question text
        question.setText("Questions loading...");

        // Sets initial country flag
        imgFlags.setImageResource(R.drawable.spinner);

        // Variable declarations
        index = 0;
        score = 0;

        // On click listener for when user selects the True Button
        btnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                determineButtonPress(true);
            }
        });

        // On click listener for when user selects the False Button
        btnFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                determineButtonPress(false);
            }
        });

        // Call following methods
        generateQuestions();
        setUpQuestion();
        setUpProgressBar();

        // Initialise paper storage for use in this activity
        Paper.init(this);
    }

    // Sets up questions for activity by retrieving data from an external database (Firebase)
    private void generateQuestions() {
        // Specifies unique database URL to be used in this context
        mRef = new Firebase("https://questionbankapp.firebaseio.com/");

        // Listens to changes in Firebase using the above unique URL
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) { // When data changes, execute the following code by creating a data view
                // Initialise variable counter
                int i = 0;
                // Loop the database and until the end by running through all child attributes present
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    // Use FireBaseQuestionBank class to construct data obtained from data view
                    FireBaseQuestionBank post = postSnapshot.getValue(FireBaseQuestionBank.class);
                    // Get individual values from FireBaseQuestionBank class and store in arrays
                    FirebaseAnswers[i] = post.getAnswer();
                    FirebaseImages[i] = post.getPicture();
                    FirebaseQuestions[i] = post.getQuestion();
                    i++; // Add one to counter
                }

                // Permanently store array values for each attribute in paper storage
                Paper.book().write("question-one", FirebaseQuestions[0]);
                Paper.book().write("question-one-answer", FirebaseAnswers[0]);
                Paper.book().write("question-one-image", FirebaseImages[0]);
                Paper.book().write("question-two", FirebaseQuestions[1]);
                Paper.book().write("question-two-answer", FirebaseAnswers[1]);
                Paper.book().write("question-two-image", FirebaseImages[1]);
                Paper.book().write("question-three", FirebaseQuestions[2]);
                Paper.book().write("question-three-answer", FirebaseAnswers[2]);
                Paper.book().write("question-three-image", FirebaseImages[2]);
                Paper.book().write("question-four", FirebaseQuestions[3]);
                Paper.book().write("question-four-answer", FirebaseAnswers[3]);
                Paper.book().write("question-four-image", FirebaseImages[3]);
                Paper.book().write("question-five", FirebaseQuestions[4]);
                Paper.book().write("question-five-answer", FirebaseAnswers[4]);
                Paper.book().write("question-five-image", FirebaseImages[4]);
                Paper.book().write("question-six", FirebaseQuestions[5]);
                Paper.book().write("question-six-answer", FirebaseAnswers[5]);
                Paper.book().write("question-six-image", FirebaseImages[5]);
                Paper.book().write("question-seven", FirebaseQuestions[6]);
                Paper.book().write("question-seven-answer", FirebaseAnswers[6]);
                Paper.book().write("question-seven-image", FirebaseImages[6]);
                Paper.book().write("question-eight", FirebaseQuestions[7]);
                Paper.book().write("question-eight-answer", FirebaseAnswers[7]);
                Paper.book().write("question-eight-image", FirebaseImages[7]);
                Paper.book().write("question-nine", FirebaseQuestions[8]);
                Paper.book().write("question-nine-answer", FirebaseAnswers[8]);
                Paper.book().write("question-nine-image", FirebaseImages[8]);
                Paper.book().write("question-ten", FirebaseQuestions[9]);
                Paper.book().write("question-ten-answer", FirebaseAnswers[9]);
                Paper.book().write("question-ten-image", FirebaseImages[9]);
                Paper.book().write("question-eleven", FirebaseQuestions[10]);
                Paper.book().write("question-eleven-answer", FirebaseAnswers[10]);
                Paper.book().write("question-eleven-image", FirebaseImages[10]);
                Paper.book().write("question-twelve", FirebaseQuestions[11]);
                Paper.book().write("question-twelve-answer", FirebaseAnswers[11]);
                Paper.book().write("question-twelve-image", FirebaseImages[11]);
                Paper.book().write("question-thirteen", FirebaseQuestions[12]);
                Paper.book().write("question-thirteen-answer", FirebaseAnswers[12]);
                Paper.book().write("question-thirteen-image", FirebaseImages[12]);
                Paper.book().write("question-fourteen", FirebaseQuestions[13]);
                Paper.book().write("question-fourteen-answer", FirebaseAnswers[13]);
                Paper.book().write("question-fourteen-image", FirebaseImages[13]);
                Paper.book().write("question-fifteen", FirebaseQuestions[14]);
                Paper.book().write("question-fifteen-answer", FirebaseAnswers[14]);
                Paper.book().write("question-fifteen-image", FirebaseImages[14]);
                Paper.book().write("question-sixteen", FirebaseQuestions[15]);
                Paper.book().write("question-sixteen-answer", FirebaseAnswers[15]);
                Paper.book().write("question-sixteen-image", FirebaseImages[15]);
                Paper.book().write("question-seventeen", FirebaseQuestions[16]);
                Paper.book().write("question-seventeen-answer", FirebaseAnswers[16]);
                Paper.book().write("question-seventeen-image", FirebaseImages[16]);
                Paper.book().write("question-eighteen", FirebaseQuestions[17]);
                Paper.book().write("question-eighteen-answer", FirebaseAnswers[17]);
                Paper.book().write("question-eighteen-image", FirebaseImages[17]);
                Paper.book().write("question-nineteen", FirebaseQuestions[18]);
                Paper.book().write("question-nineteen-answer", FirebaseAnswers[18]);
                Paper.book().write("question-nineteen-image", FirebaseImages[18]);
                Paper.book().write("question-twenty", FirebaseQuestions[19]);
                Paper.book().write("question-twenty-answer", FirebaseAnswers[19]);
                Paper.book().write("question-twenty-image", FirebaseImages[19]);
                Paper.book().write("question-twenty-one", FirebaseQuestions[20]);
                Paper.book().write("question-twenty-one-answer", FirebaseAnswers[20]);
                Paper.book().write("question-twenty-one-image", FirebaseImages[20]);
                Paper.book().write("question-twenty-two", FirebaseQuestions[21]);
                Paper.book().write("question-twenty-two-answer", FirebaseAnswers[21]);
                Paper.book().write("question-twenty-two-image", FirebaseImages[21]);
                Paper.book().write("question-twenty-three", FirebaseQuestions[22]);
                Paper.book().write("question-twenty-three-answer", FirebaseAnswers[22]);
                Paper.book().write("question-twenty-three-image", FirebaseImages[22]);
                Paper.book().write("question-twenty-four", FirebaseQuestions[23]);
                Paper.book().write("question-twenty-four-answer", FirebaseAnswers[23]);
                Paper.book().write("question-twenty-four-image", FirebaseImages[23]);
            }

            // Error message if unable to read Firebase unique URL
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

        // Read unique key from paper storage and store values in each array
        SavedQuestions[0] = Paper.book().read("question-one");
        SavedAnswers[0] = Paper.book().read("question-one-answer");
        SavedImages[0] = Paper.book().read("question-one-image");
        SavedQuestions[1] = Paper.book().read("question-two");
        SavedAnswers[1] = Paper.book().read("question-two-answer");
        SavedImages[1] = Paper.book().read("question-two-image");
        SavedQuestions[2] = Paper.book().read("question-three");
        SavedAnswers[2] = Paper.book().read("question-three-answer");
        SavedImages[2] = Paper.book().read("question-three-image");
        SavedQuestions[3] = Paper.book().read("question-four");
        SavedAnswers[3] = Paper.book().read("question-four-answer");
        SavedImages[3] = Paper.book().read("question-four-image");
        SavedQuestions[4] = Paper.book().read("question-five");
        SavedAnswers[4] = Paper.book().read("question-five-answer");
        SavedImages[4] = Paper.book().read("question-five-image");
        SavedQuestions[5] = Paper.book().read("question-six");
        SavedAnswers[5] = Paper.book().read("question-six-answer");
        SavedImages[5] = Paper.book().read("question-six-image");
        SavedQuestions[6] = Paper.book().read("question-seven");
        SavedAnswers[6] = Paper.book().read("question-seven-answer");
        SavedImages[6] = Paper.book().read("question-seven-image");
        SavedQuestions[7] = Paper.book().read("question-eight");
        SavedAnswers[7] = Paper.book().read("question-eight-answer");
        SavedImages[7] = Paper.book().read("question-eight-image");
        SavedQuestions[8] = Paper.book().read("question-nine");
        SavedAnswers[8] = Paper.book().read("question-nine-answer");
        SavedImages[8] = Paper.book().read("question-nine-image");
        SavedQuestions[9] = Paper.book().read("question-ten");
        SavedAnswers[9] = Paper.book().read("question-ten-answer");
        SavedImages[9] = Paper.book().read("question-ten-image");
        SavedQuestions[10] = Paper.book().read("question-eleven");
        SavedAnswers[10] = Paper.book().read("question-eleven-answer");
        SavedImages[10] = Paper.book().read("question-eleven-image");
        SavedQuestions[11] = Paper.book().read("question-twelve");
        SavedAnswers[11] = Paper.book().read("question-twelve-answer");
        SavedImages[11] = Paper.book().read("question-twelve-image");
        SavedQuestions[12] = Paper.book().read("question-thirteen");
        SavedAnswers[12] = Paper.book().read("question-thirteen-answer");
        SavedImages[12] = Paper.book().read("question-thirteen-image");
        SavedQuestions[13] = Paper.book().read("question-fourteen");
        SavedAnswers[13] = Paper.book().read("question-fourteen-answer");
        SavedImages[13] = Paper.book().read("question-fourteen-image");
        SavedQuestions[14] = Paper.book().read("question-fifteen");
        SavedAnswers[14] = Paper.book().read("question-fifteen-answer");
        SavedImages[14] = Paper.book().read("question-fifteen-image");
        SavedQuestions[15] = Paper.book().read("question-sixteen");
        SavedAnswers[15] = Paper.book().read("question-sixteen-answer");
        SavedImages[15] = Paper.book().read("question-sixteen-image");
        SavedQuestions[16] = Paper.book().read("question-seventeen");
        SavedAnswers[16] = Paper.book().read("question-seventeen-answer");
        SavedImages[16] = Paper.book().read("question-seventeen-image");
        SavedQuestions[17] = Paper.book().read("question-eighteen");
        SavedAnswers[17] = Paper.book().read("question-eighteen-answer");
        SavedImages[17] = Paper.book().read("question-eighteen-image");
        SavedQuestions[18] = Paper.book().read("question-nineteen");
        SavedAnswers[18] = Paper.book().read("question-nineteen-answer");
        SavedImages[18] = Paper.book().read("question-nineteen-image");
        SavedQuestions[19] = Paper.book().read("question-twenty");
        SavedAnswers[19] = Paper.book().read("question-twenty-answer");
        SavedImages[19] = Paper.book().read("question-twenty-image");
        SavedQuestions[20] = Paper.book().read("question-twenty-one");
        SavedAnswers[20] = Paper.book().read("question-twenty-one-answer");
        SavedImages[20] = Paper.book().read("question-twenty-one-image");
        SavedQuestions[21] = Paper.book().read("question-twenty-two");
        SavedAnswers[21] = Paper.book().read("question-twenty-two-answer");
        SavedImages[21] = Paper.book().read("question-twenty-two-image");
        SavedQuestions[22] = Paper.book().read("question-twenty-three");
        SavedAnswers[22] = Paper.book().read("question-twenty-three-answer");
        SavedImages[22] = Paper.book().read("question-twenty-three-image");
        SavedQuestions[23] = Paper.book().read("question-twenty-four");
        SavedAnswers[23] = Paper.book().read("question-twenty-four-answer");
        SavedImages[23] = Paper.book().read("question-twenty-four-image");

        // Create a new ArrayList
        questions = new ArrayList<>();
        // Store each corresponding question in ArrayList by creating a new QuestionObject using array data for each attribute
        questions.add(new QuestionObject(SavedQuestions[0], SavedAnswers[0], SavedImages[0]));
        questions.add(new QuestionObject(SavedQuestions[1], SavedAnswers[1], SavedImages[1]));
        questions.add(new QuestionObject(SavedQuestions[2], SavedAnswers[2], SavedImages[2]));
        questions.add(new QuestionObject(SavedQuestions[3], SavedAnswers[3], SavedImages[3]));
        questions.add(new QuestionObject(SavedQuestions[4], SavedAnswers[4], SavedImages[4]));
        questions.add(new QuestionObject(SavedQuestions[5], SavedAnswers[5], SavedImages[5]));
        questions.add(new QuestionObject(SavedQuestions[6], SavedAnswers[6], SavedImages[6]));
        questions.add(new QuestionObject(SavedQuestions[7], SavedAnswers[7], SavedImages[7]));
        questions.add(new QuestionObject(SavedQuestions[8], SavedAnswers[8], SavedImages[8]));
        questions.add(new QuestionObject(SavedQuestions[9], SavedAnswers[9], SavedImages[9]));
        questions.add(new QuestionObject(SavedQuestions[10], SavedAnswers[10], SavedImages[10]));
        questions.add(new QuestionObject(SavedQuestions[11], SavedAnswers[11], SavedImages[11]));
        questions.add(new QuestionObject(SavedQuestions[12], SavedAnswers[12], SavedImages[12]));
        questions.add(new QuestionObject(SavedQuestions[13], SavedAnswers[13], SavedImages[13]));
        questions.add(new QuestionObject(SavedQuestions[14], SavedAnswers[14], SavedImages[14]));
        questions.add(new QuestionObject(SavedQuestions[15], SavedAnswers[15], SavedImages[15]));
        questions.add(new QuestionObject(SavedQuestions[16], SavedAnswers[16], SavedImages[16]));
        questions.add(new QuestionObject(SavedQuestions[17], SavedAnswers[17], SavedImages[17]));
        questions.add(new QuestionObject(SavedQuestions[18], SavedAnswers[18], SavedImages[18]));
        questions.add(new QuestionObject(SavedQuestions[19], SavedAnswers[19], SavedImages[19]));
        questions.add(new QuestionObject(SavedQuestions[20], SavedAnswers[20], SavedImages[20]));
        questions.add(new QuestionObject(SavedQuestions[21], SavedAnswers[21], SavedImages[21]));
        questions.add(new QuestionObject(SavedQuestions[22], SavedAnswers[22], SavedImages[22]));
        questions.add(new QuestionObject(SavedQuestions[23], SavedAnswers[23], SavedImages[23]));
    }

    // Set up questions for activity
    private void setUpQuestion() {
        // End game once index value is equal to number of questions
        if (index == questions.size()) {
            endGame();
        } else {
            // Get current question
            currentQuestion = questions.get(index);
            // Display current question to screen by changing text
            question.setText(currentQuestion.getQuestion());
            /* Using the Picasso library, display current image in imgFlags
            for question and use placeholder and error image during process when and if required.
            Please refer to 'loader.xml' for further information regarding the error flag for the following line */
            Picasso.with(this).load(currentQuestion.getPicture()).placeholder(R.drawable.loader).error(R.drawable.error).into(imgFlags);
            index++; // Add one to counter declared in onCreate
        }
    }

    // Sets up progress bar to display progress throughout questionnaire
    private void setUpProgressBar() {
        // Sets maximum value for progress bar
        progressBar.setMax(100);
        // Determines exact value for each question and rounds to nearest whole number and stores value in variable
        valuePerQuestion = Math.round(100 / questions.size());
        // Adds individual question value to the existing value for each question answered
        questionProgressValue = questionProgressValue + valuePerQuestion;
        // Sets progress on progress bar
        progressBar.setProgress(valuePerQuestion);
    }

    // Determines button pressed by user for each question
    private void determineButtonPress(Boolean answer) {
        // Gets answer from QuestionObject for current question and stores value in Boolean variable
        Boolean expectedAnswer = currentQuestion.isAnswer();
        // If answer is correct, execute the following code
        if (answer == expectedAnswer) {
            // Display correct message to the screen
            Toast.makeText(MainActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
            // Add one to score counter declared in onCreate
            score++;
            // Displays current score on screen out of all questions available
            scoreInGame.setText("Your score so far is " + score + "/" + questions.size());
            // Sets audio file that will be played
            player = MediaPlayer.create(MainActivity.this, R.raw.beep1);
            // Call sounds method
            sounds();
            // Adds individual question value to the existing value for question answered
            questionProgressValue = questionProgressValue + valuePerQuestion;
            // Sets progress on progress bar
            progressBar.setProgress(questionProgressValue);
        } else { // If answer is incorrect, execute the following code
            // Display wrong message to the screen
            Toast.makeText(MainActivity.this, "Wrong!", Toast.LENGTH_SHORT).show();
            // Sets audio file that will be played
            player = MediaPlayer.create(MainActivity.this, R.raw.beep2);
            // Call sounds method
            sounds();
            // Call vibration method
            vibration();
            // Adds individual question value to the existing value for question answered
            questionProgressValue = questionProgressValue + valuePerQuestion;
            // Sets progress on progress bar
            progressBar.setProgress(questionProgressValue);
        }
        // Call setUpQuestion method
        setUpQuestion();
    }

    // Sets up sound to be played or stopped
    private void sounds() {
        // buttonSound is true, execute the following
        if (buttonSound) {
            player.start(); // Play media file
        } else { // buttonSound is false, execute the following
            player.stop(); // Play media file
        }
    }

    // Sets up vibration to be executed on device
    private void vibration() {
        // vibrationEffect is true, execute the following
        if (vibrationEffect) {
            // Get instance of Vibrator from current Context
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 500 milliseconds
            v.vibrate(500);
        } else { // vibrationEffect is false, execute the following
            // Do nothing
        }
    }

    // Sets up menu for activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.soundOn: // Finds XML id
                buttonSound = true; // Sets value to true
                // Display sounds are on message to screen
                Toast.makeText(MainActivity.this, "Button sounds are ON", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.soundOff: // Finds XML id
                buttonSound = false; // Sets value to false
                // Display sounds are off message to screen
                Toast.makeText(MainActivity.this, "Button sounds are OFF", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.vibrationON: // Finds XML id
                vibrationEffect = true; // Sets value to true
                // Display vibration effects are on message to screen
                Toast.makeText(MainActivity.this, "Vibration effects are ON", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.vibrationOFF: // Finds XML id
                vibrationEffect = false; // Sets value to false
                // Display vibration effects are off message to screen
                Toast.makeText(MainActivity.this, "Vibration effects are OFF", Toast.LENGTH_SHORT).show();
                return true;
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // End of game method once all questions have been answered
    private void endGame() {
        // Set up alert dialog for use in this context
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Congratulations!"); // Sets title message
        // Sets message to display score for the corresponding round and prompts user to enter their name
        builder.setMessage("You scored " + score + " points this round." + "\n\n" + "Please enter your name.");

        // Set up the input
        final EditText input = new EditText(this);

        // Specify the type of input expected
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the OK button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Set up input for user to enter their name
                name = input.getText().toString(); // Convert text value to string and store in name variable

                // If user enters no name, high score will not be saved
                if (name.isEmpty()) { // Checks if input is empty

                    // Display message if no name is entered
                    Toast.makeText(MainActivity.this, "Score not saved due to no name entered", Toast.LENGTH_LONG).show();

                    // Closes alert dialog screen
                    dialog.cancel();

                    // Return back to the introduction screen
                    finish();

                } else { // If input is not empty, execute following code

                    // New high score and user name
                    HighScoreObject highScore = new HighScoreObject(score, name, new Date().getTime());

                    // Get user prefs
                    List<HighScoreObject> highScores = Paper.book().read("High scores", new ArrayList<HighScoreObject>());

                    // Add item - scores
                    highScores.add(highScore);

                    // Save again
                    Paper.book().write("High scores", highScores);

                    // Closes alert dialog screen
                    dialog.cancel();

                    // Return back to the introduction screen
                    finish();
                }
            }
        });
        // Sets up the Cancel button
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Closes alert dialog screen
                dialog.cancel();
                // Return back to the introduction screen
                finish();
            }
        });
        // Display alert dialog box
        builder.show();
    }
}