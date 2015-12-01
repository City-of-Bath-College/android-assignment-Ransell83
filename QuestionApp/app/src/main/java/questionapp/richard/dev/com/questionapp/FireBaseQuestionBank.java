package questionapp.richard.dev.com.questionapp;

// ************************************************************************************************
// The following class sets up an object for questions returned from Firebase and returns values
// ************************************************************************************************

public class FireBaseQuestionBank {

    // Variable declarations each based on exact text match from Firebase
    Boolean answer;
    String picture;
    String question;

    // Empty default constructor, necessary for Firebase to be able to deserialize questions
    public FireBaseQuestionBank() {
        // Intentionally left blank to return object
    }

    // Return objects for individual values
    public Boolean getAnswer() {
        return answer;
    }

    public String getPicture() {
        return picture;
    }

    public String getQuestion() {
        return question;
    }

}