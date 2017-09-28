package ir.scorize.tpo.ui;

import javax.swing.*;

/**
 * Created by mjafar on 8/4/17.
 */
public interface TestContentPage {

    public JPanel getContentPart();
    public void handleContinueClick(TestEnvironment te);
    public void handleNextClick(TestEnvironment te);
    public void handlePrevClick(TestEnvironment te);

    public int getQuestionNumber();
    public int getAbsoluteQuestionNumber();
}
