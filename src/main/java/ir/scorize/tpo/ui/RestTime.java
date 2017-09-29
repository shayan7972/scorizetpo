package ir.scorize.tpo.ui;

import javax.swing.*;
import java.util.TimerTask;

/**
 * Created by mjafar on 9/28/17.
 */
public class RestTime implements TestContentPage {
    private JPanel panel1;
    private JLabel lblTimer;
    private TestEnvironment mTestEnvironment;
    private int timeRemaining = 10 * 60;
    private boolean mTimerCancelled = false;

    public RestTime(TestEnvironment testEnvironment) {
        mTestEnvironment = testEnvironment;
        java.util.Timer timer = new java.util.Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (mTimerCancelled) {
                    timer.cancel();
                    return;
                }
                timeRemaining -= 1;
                int minutes = timeRemaining / 60;
                int seconds = timeRemaining % 60;
                String repr = String.format("%02d:%02d", minutes, seconds);

                lblTimer.setText(repr);

                if (timeRemaining == 0) {
                    continueToSpeaking();
                    timer.cancel();
                }
            }
        }, 0, 1000);
    }

    @Override
    public JPanel getContentPart() {
        return panel1;
    }

    @Override
    public void handleContinueClick(TestEnvironment te) {
        continueToSpeaking();
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

    private void continueToSpeaking() {
        final TestEnvironment te = mTestEnvironment;
        mTimerCancelled = true;

        java.awt.EventQueue.invokeLater(() -> {
            SpeakingDirections sd = new SpeakingDirections();
            te.setContentPart(sd, -1);
        });
    }
}
