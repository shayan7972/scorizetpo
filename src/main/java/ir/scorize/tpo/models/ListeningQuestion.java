package ir.scorize.tpo.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by mjafar on 9/9/17.
 */
public class ListeningQuestion extends QuestionBase {
    private String fileAddress;
    private boolean showHeadsetIcon;
    private boolean multiChoice;

    public String getFileAddress() {
        return fileAddress;
    }

    public boolean isShowHeadsetIcon() {
        return showHeadsetIcon;
    }

    public boolean isMultiChoice() {
        return multiChoice;
    }

    @JsonCreator
    public ListeningQuestion(
            @JsonProperty("questionText") String questionText,
            @JsonProperty("choices") String[] choices,
            @JsonProperty("fileAddress") String fileAddress,
            @JsonProperty("showHeadsetIcon") boolean showHeadsetIcon,
            @JsonProperty("multiChoice") boolean multiChoice
    ) {
        this.questionText = questionText;
        this.choices = choices;
        this.fileAddress = fileAddress;
        this.showHeadsetIcon = showHeadsetIcon;
        this.multiChoice = multiChoice;
    }
}
