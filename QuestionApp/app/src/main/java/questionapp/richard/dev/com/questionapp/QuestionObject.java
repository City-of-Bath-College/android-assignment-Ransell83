package questionapp.richard.dev.com.questionapp;

// ************************************************************************************************
// The following class sets up an object for each question and returns the values
// ************************************************************************************************

public class QuestionObject {

    // Variable declarations for each question object
    private String question;
    private Boolean answer;
    private String picture;

    // Sets return values for entire object
    public QuestionObject(String question, Boolean answer, String picture) {
        this.question = question;
        this.answer = answer;
        this.picture = picture;
    }

    // Return objects for individual values
    public String getQuestion() {
        return question;
    }

    public Boolean isAnswer() {
        return answer;
    }

    public String getPicture() {
        return picture;
    }

}