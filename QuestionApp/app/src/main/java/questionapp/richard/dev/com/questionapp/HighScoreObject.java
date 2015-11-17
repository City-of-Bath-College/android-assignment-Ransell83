package questionapp.richard.dev.com.questionapp;

public class HighScoreObject {
    private String name;
    private Integer score;
    private Long timestamp;

    public HighScoreObject(Integer score, String name, Long timestamp){
        this.score = score;
        this.name = name;
        this.timestamp = timestamp;
    }

    public String getName(){
        return name;
    }

    public Integer getScore(){
        return score;
    }

    public Long getTime(){
        return timestamp;
    }

    public HighScoreObject() {
        //nothing here!
    }

}