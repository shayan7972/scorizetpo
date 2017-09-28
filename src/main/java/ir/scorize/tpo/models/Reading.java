package ir.scorize.tpo.models;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import ir.scorize.tpo.DataManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * Created by mjafar on 8/5/17.
 */
public class Reading {
    private ReadingQuestion[] questions;
    private String content;
    private int questionsOffset;

    public Reading(int questionsOffset) {
        this.questionsOffset = questionsOffset;
    }

    public ReadingQuestion[] getQuestions() {
        return questions;
    }

    public String getContent() {
        return content;
    }

    public static Reading loadReading(int examNumber, int readingNumber, int questionsOffset) {
        File dataDir = new File(DataManager.kDataFolder);
        File readingText = new File(dataDir, (examNumber + 1) + "/reading/" + (readingNumber + 1) + ".html");
        File questions = new File(dataDir, (examNumber + 1) + "/reading/" + (readingNumber + 1) + "-questions.yaml");
        BufferedReader fr = null;

        try {
            fr = new BufferedReader(new FileReader(readingText));

            Reading r = new Reading(questionsOffset);
            r.content = String.join("\n", fr.lines().collect(Collectors.toList()));

            fr.close();
            fr = new BufferedReader(new FileReader(questions));
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

            r.questions = mapper.readValue(fr, ReadingQuestion[].class);

            return r;
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
