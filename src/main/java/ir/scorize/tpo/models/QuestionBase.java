package ir.scorize.tpo.models;

/**
 * Created by mjafar on 9/9/17.
 */
public class QuestionBase {
    protected String questionText;
    protected String[] choices = new String[4];

    public String getQuestionText() {
        return questionText;
    }

    public String[] getChoices() {
        return choices;
    }

}
