package ir.scorize.tpo.ui;

import javax.swing.*;

/**
 * Created by mjafar on 8/4/17.
 */
public class ReadingDirections implements TestContentPage {
    private JPanel pnlReading;
    private JTextPane tpReadingDirections;

    public ReadingDirections() {
        tpReadingDirections.setContentType("text/html");
        tpReadingDirections.setText("<h3 align=\"center\">Reading Directions</h3>" +
                "<p>In this part of the reading section, you will read 3 passage. In the test you will have 60 minutes to read the passage and answer the questions. Most questions are worth 1 point but the last question in this set is worth more than 1 point. The directions indicate how many points you may receive. Some passages include a word or phrase that is underlined in blue. Click on the word or phrase to see a definition or an explanation.</p>\n" +
                "<p> When you want to move to the next question, click on Next. You may skip questions and go back to them later if you want to return to previous questions, click on Back. You can click on review at any time and the review screen will show you which questions you have answered and which you have not answered. From this review screen, you may go directly to any question you have already seen in the reading section.</p>\n" +
                "<p>Click on <string>Continue</string> to go on.</p>");
    }

    public JPanel getContentPart() {
        return pnlReading;
    }

    @Override
    public void handleContinueClick(TestEnvironment te) {
        java.awt.EventQueue.invokeLater(() -> {
            ReadingPage readingPage = new ReadingPage(te);
            readingPage.loadReading(te.mCurrentQuestionSet);
            te.setContentPart(readingPage, -1);
        });
    }

    @Override
    public void handleNextClick(TestEnvironment te) {

    }

    @Override
    public void handlePrevClick(TestEnvironment te) {

    }

    @Override
    public int getQuestionNumber() {
        return 0;
    }

    @Override
    public int getAbsoluteQuestionNumber() {
        return 0;
    }
}
