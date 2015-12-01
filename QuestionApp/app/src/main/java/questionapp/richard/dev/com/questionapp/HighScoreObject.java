package questionapp.richard.dev.com.questionapp;

// ************************************************************************************************
// The following class sets up an object for each question and returns the values
// ************************************************************************************************

public class HighScoreObject implements Comparable<HighScoreObject> { // Also compares values within HighScoreObject

    // Variable declarations for each question object
    private String name;
    private Integer score;
    private Long timestamp;

    // Sets return values for entire object
    public HighScoreObject(Integer score, String name, Long timestamp) {
        this.score = score;
        this.name = name;
        this.timestamp = timestamp;
    }

    // Empty default constructor, necessary for deserializing questions
    public HighScoreObject() {
        // Intentionally left blank to return object
    }

    // Return objects for individual values
    public String getName() {
        return name;
    }

    public Integer getScore() {
        return score;
    }

    public Long getTime() {
        return timestamp;
    }

    // Compares high scores and returns value
    public int compareTo(HighScoreObject scores) {
        return score.compareTo(scores.score);
    }

}