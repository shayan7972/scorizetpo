package ir.scorize.tpo.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by mjafar on 8/5/17.
 */
public class ReadingQuestion extends QuestionBase {
    protected String followingText;

    public String getFollowingText() {
        return followingText;
    }

    public ReadingQuestion(String questionText, String[] choices) {
        this(questionText, choices, "");
    }

    @JsonCreator
    public ReadingQuestion(
            @JsonProperty("questionText") String questionText,
            @JsonProperty("choices") String[] choices,
            @JsonProperty("followingText") String followingText
    ) {
        this.questionText = questionText;
        this.choices = choices;
        this.followingText = followingText;
    }
}
