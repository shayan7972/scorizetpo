package ir.scorize.tpo.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import ir.scorize.tpo.DataManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by mjafar on 9/29/17.
 */
public class SpeakingTask {
    @JsonProperty("style")
    private String style;
    @JsonProperty("passage")
    private String passage;
    @JsonProperty("readingTime")
    private int readingTime;
    @JsonProperty("preparationTime")
    private int preparationTime;
    @JsonProperty("speakingTime")
    private int speakingTime;
    @JsonProperty("question")
    private String question;
    @JsonProperty("questionFile")
    private String questionFile;
    @JsonProperty("conversationFile")
    private String conversationFile;
    @JsonProperty("lectureImage")
    private String lectureImage;

//    private int questionsOffset;
//
//    public SpeakingTask(int questionsOffset) {
//        this.questionsOffset = questionsOffset;
//    }

    @JsonCreator
    public SpeakingTask(
            @JsonProperty(value = "style", required = true) String style,
            @JsonProperty(value = "preparationTime", required = true) int preparationTime,
            @JsonProperty(value = "speakingTime", required = true) int speakingTime,
            @JsonProperty(value = "question", required = true) String question,
            @JsonProperty("passage") String passage,
            @JsonProperty("readingTime") int readingTime,
            @JsonProperty("questionFile") String questionFile,
            @JsonProperty("conversationFile") String conversationFile,
            @JsonProperty("lectureImage") String lectureImage
    ) {
        this.style = style;
        this.passage = passage;
        this.readingTime = readingTime;
        this.preparationTime = preparationTime;
        this.speakingTime = speakingTime;
        this.question = question;
        this.questionFile = questionFile;
        this.conversationFile = conversationFile;
        this.lectureImage = lectureImage;
    }

    public String getStyle() {
        return style;
    }

    public int getReadingTime() {
        return readingTime;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public int getSpeakingTime() {
        return speakingTime;
    }

    public String getQuestion() {
        return question;
    }

    public String getQuestionFile() {
        return questionFile;
    }

    public String getConversationFile() {
        return conversationFile;
    }

    public String getLectureImage() {
        return lectureImage;
    }

    //    public static SpeakingTask[] loadSpeaking(int examNumber, int questionsOffset) {
    public static SpeakingTask[] loadSpeaking(int examNumber) {
        File dataDir = new File(DataManager.kDataFolder);
        File speakingFile = new File(dataDir, (examNumber + 1) + "/speaking/tasks.yaml");
        BufferedReader fr = null;

        try {
            fr = new BufferedReader(new FileReader(speakingFile));

            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

            SpeakingTask[] l = mapper.readValue(fr, SpeakingTask[].class);

            return l;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fr != null) fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String getPassage() {
        return passage;
    }
//    public int getQuestionsOffset() {
//        return questionsOffset;
//    }
}
