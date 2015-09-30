package questionapp.richard.dev.com.questionapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView question;
    private Button btnTrue;
    private Button btnFalse;
    private ImageView imgFlag;
    private List<QuestionObject> questions;
    private QuestionObject currentQuestion;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Connect private variables declared above to items on activity_main.xml file
        question = (TextView)findViewById(R.id.question);
        btnTrue = (Button)findViewById(R.id.btnTrue);
        btnFalse = (Button)findViewById(R.id.btnFalse);
        imgFlag = (ImageView)findViewById(R.id.imgFlag);

        // Set questions for country flags
        question.setText("Is London the capital of England?");

        // Set country flag
        imgFlag.setImageResource(R.drawable.unitedkingdom);

        index = 0;

        // On click listener
        btnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                determineButtonPress(true);
            }
        });

        btnFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                determineButtonPress(false);
            }
        });

        generateQuestions();
        setUpQuestion();
    }

    private void generateQuestions(){
        questions = new ArrayList<>();
        questions.add(new QuestionObject("Is London the capital of England?", true, R.drawable.unitedkingdom));
        questions.add(new QuestionObject("Is Buenos Aires the capital of Argentina?", true, R.drawable.argentina));
        questions.add(new QuestionObject("Is City of Brussels the capital of Belgium?", true, R.drawable.belgium));
        questions.add(new QuestionObject("Is Brasilia the capital of Brazil?", true, R.drawable.brazil));
        questions.add(new QuestionObject("Is Beijing the capital of Chile?", false, R.drawable.chile));
        questions.add(new QuestionObject("Is Zagreb the capital of China?", false, R.drawable.china));
        questions.add(new QuestionObject("Is Prague the capital of Croatia?", false, R.drawable.crotia));
        questions.add(new QuestionObject("Is Santiago the capital of Czech Republic?", false, R.drawable.czechrepublic));
        questions.add(new QuestionObject("Is Cairo the capital of Egypt?", true, R.drawable.egypt));
        questions.add(new QuestionObject("Is Helsinki the capital of Finland?", true, R.drawable.finland));
        questions.add(new QuestionObject("Is Paris the capital of France?", true, R.drawable.france));
        questions.add(new QuestionObject("Is New Delhi the capital of Germany?", false, R.drawable.germany));
        questions.add(new QuestionObject("Is New Delhi the capital of India?", true, R.drawable.india));
        questions.add(new QuestionObject("Is Dublin the capital of Jamaica?", false, R.drawable.jamaica));
        questions.add(new QuestionObject("Is Beijing the capital of Japan?", false, R.drawable.japan));
        questions.add(new QuestionObject("Is Beirut the capital of Lebanon?", true, R.drawable.lebanon));
        questions.add(new QuestionObject("Is Skopje the capital of Macedonia?", true, R.drawable.macedonia));
        questions.add(new QuestionObject("Is Washington the capital of Mexico?", false, R.drawable.mexico));
        questions.add(new QuestionObject("Is Warsaw the capital of Poland?", true, R.drawable.poland));
        questions.add(new QuestionObject("Is Barcelona the capital of Portugal?", false, R.drawable.portugal));
        questions.add(new QuestionObject("Is Moscow the capital of Russia?", true, R.drawable.russia));
        questions.add(new QuestionObject("Is Seoul the capital of South Korea?", true, R.drawable.southkorea));
        questions.add(new QuestionObject("Is Ottawa the capital of Spain?", false, R.drawable.spain));
        questions.add(new QuestionObject("Is Munich the capital of Sweden?", false, R.drawable.sweden));
        questions.add(new QuestionObject("Is London the capital of England?", true, R.drawable.unitedkingdom));
    }

    private void setUpQuestion(){
        currentQuestion = questions.get(index);
        question.setText(currentQuestion.getQuestion());
        imgFlag.setImageResource(currentQuestion.getPicture());

        index++;
    }

    private void determineButtonPress(boolean answer){
        boolean expectedAnswer = currentQuestion.isAnswer();
        if (answer == expectedAnswer) {
            // Correct!
            Toast.makeText(MainActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Wrong!", Toast.LENGTH_SHORT).show();
        }
        setUpQuestion();
    }
}
