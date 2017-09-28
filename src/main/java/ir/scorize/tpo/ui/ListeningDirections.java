package ir.scorize.tpo.ui;

import javax.swing.*;

/**
 * Created by mjafar on 8/25/17.
 */
public class ListeningDirections implements TestContentPage {
    private JPanel pnlListening;
    private JTextPane tpListeningDirections;
    private boolean shownHeadset = false;
    private JLabel putHeadsets;

    public ListeningDirections() {
        super();
        java.awt.EventQueue.invokeLater(() -> {
            tpListeningDirections.setContentType("text/html");
            tpListeningDirections.setText("<h3 align=\"center\">Listening Section Directions</h3>" +
                    "<p>This test measures your ability to understand conversations and lectures in English. The Listening section is divided into 2 separately timed parts. In each part you will listen to 1 conversation and 2 lectures. You will hear each conversation or lecture only one time.</p>" +
                    "<p>After each conversation or lecture, you will answer some questions about it. The questions typically as about the main idea and supporting details. Some questions ask about a speakers purpose or attitude. Answer the questions based on what is stated or implied by the speakers.</p>" +
                    "<p>You may take notes while you listen. You may use your notes to help you answer the questions. Your notes will not be scored. If you need to change the volume while you listen, click on the Volume icon at the top of the screen.</p>" +
                    "<p>In some questions, you will see this icon: This means that you will hear, but not see. part of the question, Some of the questions have special directions. These directions appear in a graybox on the screen. Most questions are worth 1 point. If a question is worth more than 1 point, it will have special directions that indicate how many points you can receive.</p>" +
                    "<p>You must answer each question. After you answer, click on Next. Then click on OK to confirm your answer and go on to the next question. After you click on OK. you cannot return to previous questions. If you are using the Untimed Mode, you may return to previous questions and you may listen to each conversation and lecture again. Remember that prior exposure to the conversations, lectures, and questions could lead to an increase in your section scores and may not reflect a score you would get when seeing them for the first time.</p>" +
                    "<p>During this practice test, you may click the Pause icon at any time. This will stop the test until you decide to continue. You may continue the test in a few minutes or at any time during the period that your test is activated.</p>" +
                    "<p>In an actual test, and if you are using Timed Mode, a clock at the top of the screen will show you how much time is remaining. The clock will not countdown while you are listening. The clock will countdown only while you are answering the questions.</p>");
            pnlListening.remove(tpListeningDirections);
        });
    }

    public JPanel getContentPart() {
        return pnlListening;
    }

    @Override
    public void handleContinueClick(TestEnvironment te) {
        java.awt.EventQueue.invokeLater(() -> {
            if (!shownHeadset) {
                pnlListening.add(tpListeningDirections);
                pnlListening.remove(putHeadsets);
                shownHeadset = true;
                // TODO: play instructions via sound
            } else {
                ListeningPage listeningPage = new ListeningPage(te);
                te.mCurrentQuestionSet = 0;
                listeningPage.loadListening(te.mCurrentQuestionSet);
                te.setContentPart(listeningPage, -1);
            }
            pnlListening.invalidate();
            te.invalidate();
            te.repaint();
            pnlListening.repaint();
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