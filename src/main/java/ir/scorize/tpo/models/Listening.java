package ir.scorize.tpo.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import ir.scorize.tpo.DataManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by mjafar on 9/9/17.
 */
public class Listening {

    @JsonProperty("fileName")
    private String fileName;
    @JsonProperty("picturesFileName")
    private String picturesFileName;
    @JsonProperty("questions")
    private ListeningQuestion[] questions;
    private int questionsOffset;

    public Listening(int questionsOffset) {
        this.questionsOffset = questionsOffset;
    }

    public String getFileName() {
        return fileName;
    }

    public String getPicturesFileName() {
        return picturesFileName;
    }

    public ListeningQuestion[] getQuestions() {
        return questions;
    }

    @JsonCreator
    public Listening(
            @JsonProperty("fileName") String fileName,
            @JsonProperty("picturesFileName") String picturesFileName,
            @JsonProperty("questions") ListeningQuestion[] questions
    ) {
        this.fileName = fileName;
        this.picturesFileName = picturesFileName;
        this.questions = questions;
    }

    public static Listening loadListening(int examNumber, int listeningNumber, int questionsOffset) {
        File dataDir = new File(DataManager.kDataFolder);
        File listeningFile = new File(dataDir, (examNumber + 1) + "/listening/" + (listeningNumber + 1) + ".yaml");
        BufferedReader fr = null;

        try {
            fr = new BufferedReader(new FileReader(listeningFile));

            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

            Listening l = mapper.readValue(fr, Listening.class);
            l.questionsOffset = questionsOffset;

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

    public int getQuestionsCount() {
        return this.questions.length;
    }

    public int getQuestionsOffset() {
        return questionsOffset;
    }
}
