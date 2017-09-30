package ir.scorize.tpo.ui;

import javax.swing.*;

/**
 * Created by mjafar on 9/28/17.
 */
public class SpeakingDirections implements TestContentPage {
    private JPanel panel1;
    private boolean shownHeadset = false;
    private JTextPane tpSpeakingDirections;
    private JLabel putHeadsets;

    public SpeakingDirections() {
        super();
        java.awt.EventQueue.invokeLater(() -> {
            tpSpeakingDirections.setContentType("text/html");
            tpSpeakingDirections.setText("<h3 align=\"center\">Speaking Section Directions</h3>" +
                    "<p>about a variety of topics. You will answer six questions by speaking into a microphone. Answer each of the questions as completely as possible.</p>" +
                    "<p>In questions and 2. you will speak about familiar topics. Your response will be scored on your ability to speak clearly and coherently about the topics.</p>" +
                    "<p>In questions 3 and 4. You will first read a short text. The text will go away and you will then listen to a talk on the same topic. You will then be asked a question about what you have read and heard You will need to combine appropriate information from the text and the talk to provide a complete answer to the question Your response will be scored on your ability to speak clearly and coherently and on your ability to accurately convey information about what you have read and heard in questions 5 and 6.you will listen to part of a conversation or a lecture. You will then be asked a question about what you have heard. Your response will be scored on your ability to speak clearly and coherently and on your ability to accurately convey information about what you heard. In questions 3 through 6. you may take notes while you read and while you listen to the conversations and lectures. You may use your notes to help prepare your response.</p>" +
                    "<p>Listen carefully to the directions for each question. The directions will not be written on the screen. For each question, you will be given a short time to prepare your response (15 to 30 seconds, depending on the question). A clock will show how much preparation time is remaining. When the preparation time is up. you will be told to begin your response. A clock will show how much response time is remaining. A message will appear on the screen when the response time has ended.</p>" +
                    "<p>In this practice test, you can click on Stop recording to stop the recording of your response. You can also click on Playback response to hear your recording. Once you have heard your response, you will have the opportunity to record your response again or confirm that you want to keep your response. In questions 3 through 6. you can click on replay Talk if you want to listen to the conversations or lectures again. During this practice test, you may click the Pause icon at anytime. This will stop the test until you decide to continue. You may continue the test in a few minutes or at any time during the period that your test is activated.</p>" +
                    "<p>Please note that the Stop recording. Playback response. replay Talk, and Pause icons are available only for this practice test. They will NOT be available during the actual test. If you do not use these functions, your experience will be closer to the actual TOEFL test experience. Performance on the Speaking Practice test is not necessarily a predictor of how you might perform during an actual TOEFL administration.</p>");
            panel1.remove(tpSpeakingDirections);
        });
    }

    @Override
    public JPanel getContentPart() {
        return panel1;
    }

    @Override
    public void handleContinueClick(TestEnvironment te) {
        java.awt.EventQueue.invokeLater(() -> {
            if (!shownHeadset) {
                panel1.add(tpSpeakingDirections);
                panel1.remove(putHeadsets);
                shownHeadset = true;
//                 TODO: play instructions via sound
            } else {
                SpeakingPages sp = new SpeakingPages(te);
                te.mCurrentQuestionSet = 0;
                sp.loadSpeaking(te.mCurrentQuestionSet);
                te.setContentPart(sp, -1);
            }
            panel1.invalidate();
            te.invalidate();
            te.repaint();
            panel1.repaint();
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
