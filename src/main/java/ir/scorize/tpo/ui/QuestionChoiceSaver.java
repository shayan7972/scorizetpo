package ir.scorize.tpo.ui;

/**
 * Created by mjafar on 8/18/17.
 */
public interface QuestionChoiceSaver {
    public void saveQuestionSelectedChoices(int questionNumber, int[] choices);
    public int[]  getQuestionSelectedChoices(int questionNumber);
    public boolean isSelected(int questionNumber, int choiceNumber);
}
