package ir.scorize.tpo.ui;

import ir.scorize.tpo.TimingHelper;
import ir.scorize.tpo.models.SpeakingTask;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

/**
 * Created by mjafar on 9/29/17.
 */
public class SpeakingPages implements TestContentPage {

    private TestEnvironment mTestEnvironment;
    private JPanel recorder;
    private JLabel lblQuestion;
    private JLabel lblStatus;
    private JLabel lblTimer;
    private JProgressBar prgTimer;
    private JPanel container;
    private JPanel listenToThingPanel;
    private JLabel picListenToThing;
    private JProgressBar prgListenToThingIndicator;
    private JPanel readText;
    private JLabel lblReadingDirections;
    private JTextPane txtShortPassage;

    private CardLayout mCards;

    private int currentTaskNumber;

    private TimingHelper mCurrentTasks;

    public SpeakingPages(TestEnvironment te) {
        super();
        mTestEnvironment = te;
        mCards = new CardLayout();
        container = new JPanel(mCards);
        container.setLayout(mCards);
        container.add(listenToThingPanel, "listen");
        container.add(recorder, "recorder");
        container.add(readText, "passage");

        txtShortPassage.setBorder(new BevelBorder(BevelBorder.LOWERED));
    }

    @Override
    public JPanel getContentPart() {
        return container;
    }

    @Override
    public void handleContinueClick(TestEnvironment te) {
        try {
            mCurrentTasks.cancel();
            loadNextTask();
        } catch (ArrayIndexOutOfBoundsException ex) {
            // End of speaking section. Go to writing
        }
    }

    private void loadNextTask() {
        loadSpeaking(getQuestionNumber() + 1);
    }

    @Override
    public void handleNextClick(TestEnvironment te) {

    }

    @Override
    public void handlePrevClick(TestEnvironment te) {

    }

    @Override
    public int getQuestionNumber() {
        return currentTaskNumber;
    }

    @Override
    public int getAbsoluteQuestionNumber() {
        return 0;
    }

    private SpeakingTask getCurrentSpeaking() {
        return getSpeaking(getQuestionNumber());
    }

    private SpeakingTask getSpeaking(int taskNumber) {
        return mTestEnvironment.mTest.getSpeaking(taskNumber);
    }

    public void loadSpeaking(int taskNumber) {
        System.out.println("SpeakingPages.loadSpeaking");
        System.out.println("taskNumber = [" + taskNumber + "]");

        SpeakingTask currentTask = getSpeaking(taskNumber);
        currentTaskNumber = taskNumber;

        java.awt.EventQueue.invokeLater(() -> mTestEnvironment.mTopPart.setTitle(String.format("%d of %d", currentTaskNumber + 1, mTestEnvironment.mTest.speakingCount())));

        switch (currentTask.getStyle()) {
            case "independent":
                mCurrentTasks = setupIndependentSpeaking(currentTask);
                break;
            case "textandconversation":
            case "textandlecture":
                mCurrentTasks = setupTextAndConversation(currentTask);
                break;
            case "conversation":
            case "lecture":
                mCurrentTasks = setupOnlyConversation(currentTask);
                break;
        }
        mCurrentTasks.then(this::loadNextTask).start();
    }

    private TimingHelper setupIndependentSpeaking(final SpeakingTask currentSpeaking) {
        return createRecordPageSequence(
            currentSpeaking.getQuestion(),
            currentSpeaking.getPreparationTime(),
            currentSpeaking.getSpeakingTime(),
            ""  // File name
        );
    }

    private TimingHelper setupTextAndConversation(final SpeakingTask currentSpeaking) {
        return createTextAndConversationSequence(currentSpeaking)
        .then(createRecordPageSequence(
                currentSpeaking.getQuestion(),
                currentSpeaking.getPreparationTime(),
                currentSpeaking.getSpeakingTime(),
                "" // File name
        ));
    }

    private TimingHelper setupOnlyConversation(final SpeakingTask currentTask) {
        return createListenToConversationSequence(currentTask)
        .then(createRecordPageSequence(
                currentTask.getQuestion(),
                currentTask.getPreparationTime(),
                currentTask.getSpeakingTime(),
                "" // File name
        ));
    }

    private TimingHelper createRecordPageSequence(final String question, final int preparationTime, final int recordingTime, final String outputFile) {
        int waitBeforeStart = 5; // TODO: read the question time
        int beepTime = 2;

        return TimingHelper.create()
                .runOnce(() -> EventQueue.invokeLater(() -> {
                    mCards.show(container, "recorder");

                    lblQuestion.setText(question);
                    lblStatus.setText("");
                    prgTimer.setValue(0);
                }))
                .wait(waitBeforeStart * 1000)
                .then(() -> EventQueue.invokeLater(() -> {
                    lblStatus.setText("Prepare your answer");
                    prgTimer.setMaximum(preparationTime);
                    prgTimer.setValue(0);
                    lblTimer.setText(String.format("%02d:%02d", preparationTime / 60, preparationTime % 60));
                }))
                .repeat((counter) -> {
                    final int remaining = preparationTime - counter;
                    EventQueue.invokeLater(() -> {
                        lblTimer.setText(String.format("%02d:%02d", remaining / 60, remaining % 60));
                        prgTimer.setValue(counter);
                    });

                    return null;
                }, 1000, preparationTime)
                .then(() -> EventQueue.invokeLater(() -> {
                    // TODO: Play the beep
                    lblStatus.setText("");
                    prgTimer.setValue(prgTimer.getMaximum());
                }))
                .wait(beepTime * 1000)
                .then(() -> {
                    // TODO: start recording the user
                    lblStatus.setText("Recording");
                    prgTimer.setMaximum(recordingTime);
                    prgTimer.setValue(0);
                    lblTimer.setText("00:00");
                })
                .repeat((counter) -> {
                    EventQueue.invokeLater(() -> {
                        lblTimer.setText(String.format("%02d:%02d", counter / 60, counter % 60));
                        prgTimer.setValue(counter);
                    });

                    return null;
                }, 1000, recordingTime)
                .then(() -> java.awt.EventQueue.invokeLater(() -> {
                    lblStatus.setText("");
                    prgTimer.setValue(prgTimer.getMaximum());
                    System.out.println(outputFile);
                    // TODO: save recording file
                }));
    }

    private TimingHelper createTextAndConversationSequence(final SpeakingTask currentSpeaking) {
        int waitBeforeStart = 0; // TODO: read the question title time

        return TimingHelper.create()
            .runOnce(() -> java.awt.EventQueue.invokeLater(()-> {
                mCards.show(container, "passage");

                lblQuestion.setText(currentSpeaking.getQuestion());

                txtShortPassage.setText(currentSpeaking.getPassage());
                txtShortPassage.setVisible(false);
                lblReadingDirections.setText(String.format("Reading time: %d seconds", currentSpeaking.getReadingTime()));
            }))
            .wait(1000 * waitBeforeStart)
            .then(() -> {
                txtShortPassage.setVisible(true);
            })
            .repeat((counter) -> {
                int remainingTime = currentSpeaking.getReadingTime() - counter;
                java.awt.EventQueue.invokeLater(() -> {
                    lblReadingDirections.setText(String.format("Reading time: %d seconds", remainingTime));
                });

                return null;
            }, 1000, currentSpeaking.getReadingTime())
            .then(createListenToConversationSequence(currentSpeaking));
    }

    private TimingHelper createListenToConversationSequence(SpeakingTask currentSpeaking) {
        int conversationLength = 5;
        return TimingHelper.create()
                .runOnce(() -> java.awt.EventQueue.invokeLater(() -> {
                    // TODO: play the dialog
                    mCards.show(container, "listen");
                    if (currentSpeaking.getLectureImage() != null) {
                        String resourceName = "resources/" + currentSpeaking.getLectureImage();
                        System.out.println(resourceName);
                        picListenToThing.setIcon(new ImageIcon(getClass().getResource(resourceName)));
                    }
                    prgListenToThingIndicator.setValue(0);
                    prgListenToThingIndicator.setMaximum(conversationLength); // TODO: dialog length
                }))
                .repeat((counter) -> {
                    java.awt.EventQueue.invokeLater(() -> prgListenToThingIndicator.setValue(prgListenToThingIndicator.getValue() + 1));
                    return null;
                }, 1000, conversationLength)
                .then(() -> java.awt.EventQueue.invokeLater(() -> {
                    prgListenToThingIndicator.setValue(prgListenToThingIndicator.getMaximum());
                }))
                .wait(1000);
    }
}
