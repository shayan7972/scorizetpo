package ir.scorize.tpo.ui;

import ir.scorize.tpo.models.Listening;

import javax.swing.*;

/**
 * Created by mjafar on 9/15/17.
 */
public class ListeningQuestion implements TestContentPage {
    private JPanel pnlMain;
    private JLabel lblQuestion;
    private JPanel pnlSingleChoice;
    private JRadioButton choice1;
    private JRadioButton choice4;
    private JRadioButton choice3;
    private JRadioButton choice2;
    private JPanel pnlMultiChoice;
    private JCheckBox option1;
    private JCheckBox option2;
    private JCheckBox option3;
    private JCheckBox option4;
    private ButtonGroup buttonGroup1;
    private TestEnvironment mTestEnvironment;

    /**
     * Local value for question number. between 0 to 20 something. not the absolute value.
     */
    private int questionNumber;


    public ListeningQuestion(TestEnvironment testEnvironment) {
        super();
        mTestEnvironment = testEnvironment;
        setupChoiceActionListeners(testEnvironment);
    }

    @Override
    public JPanel getContentPart() {
        return pnlMain;
    }

    @Override
    public void handleContinueClick(TestEnvironment te) {
        nextListening(te);
    }

    @Override
    public void handleNextClick(TestEnvironment te) {
        try {
            ++questionNumber;
            updateQuestion();
        } catch (ArrayIndexOutOfBoundsException ex) {
            // It's the last question
            nextListening(te);
        }
    }

    @Override
    public void handlePrevClick(TestEnvironment te) {
        try {
            --questionNumber;
            updateQuestion();
        } catch (ArrayIndexOutOfBoundsException ex) {
            prevListening(te);
        }
    }

    private void nextListening(TestEnvironment te) {
        try {
            ListeningPage lp = new ListeningPage(te);
            questionNumber = -1;
            ++te.mCurrentQuestionSet;
            lp.loadListening(te.mCurrentQuestionSet);
            te.setContentPart(lp, questionNumber);
        } catch (ArrayIndexOutOfBoundsException ex2) {
            String title = "Finish Warning";
            String text = "You have seen all of the questions in this part of the Listening Section.\n" +
                    "You may go back and Review. As long as there is time remaining, you can check your work.\n" +
                    "Click on Cancel to continue working. \n" +
                    "Click on Ok to go on. Once you leave this part of the Listening section, you WILL NOT " +
                    "be able to return to it. ";
            int result = JOptionPane.showConfirmDialog(te, text, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                java.awt.EventQueue.invokeLater(() -> {
                    // TODO: uncomment
//                    te.mTopPart.setTitle("Rest");
//                    te.setContentPart(new RestDirections(), 0);

                    te.invalidate();
                    te.repaint();
                });
            }
        }
    }

    private void prevListening(TestEnvironment te) {
        int t = questionNumber;
        try {
            questionNumber = 0;
            --te.mCurrentQuestionSet;
            loadListening();
            te.setContentPart(this, questionNumber);
        } catch (ArrayIndexOutOfBoundsException ignored) {
            questionNumber = t;
        }
    }

    private void setupChoiceActionListeners(TestEnvironment testEnvironment) {
        choice1.addActionListener(new ChoiceActionListener(0, testEnvironment, buttonGroup1, this));
        choice2.addActionListener(new ChoiceActionListener(1, testEnvironment, buttonGroup1, this));
        choice3.addActionListener(new ChoiceActionListener(2, testEnvironment, buttonGroup1, this));
        choice4.addActionListener(new ChoiceActionListener(3, testEnvironment, buttonGroup1, this));

        option1.addActionListener(new MultiValueChoiceActionListener(0, testEnvironment, null, this));
        option2.addActionListener(new MultiValueChoiceActionListener(1, testEnvironment, null, this));
        option3.addActionListener(new MultiValueChoiceActionListener(2, testEnvironment, null, this));
        option4.addActionListener(new MultiValueChoiceActionListener(3, testEnvironment, null, this));
    }


    private void updateQuestion() {
        System.out.println("ListeningQuestion.updateQuestion");
        System.out.println("questionNumber = [" + questionNumber + "]");

        Listening currentListening = getCurrentListening();

        ir.scorize.tpo.models.ListeningQuestion question = currentListening.getQuestions()[questionNumber];
        String[] choices = question.getChoices();

        lblQuestion.setText(String.format("%d. %s", getAbsoluteQuestionNumber() + 1, question.getQuestionText()));
        pnlMultiChoice.setVisible(question.isMultiChoice());
        pnlSingleChoice.setVisible(!question.isMultiChoice());

        AbstractButton opt1, opt2, opt3, opt4;
        if (question.isMultiChoice()) {
            opt1 = option1; opt2 = option2; opt3 = option3; opt4 = option4;
        } else {
            opt1 = choice1; opt2 = choice2; opt3 = choice3; opt4 = choice4;
            buttonGroup1.clearSelection();
        }

        opt1.setText(choices[0]);
        opt2.setText(choices[1]);
        opt3.setText(choices[2]);
        opt4.setText(choices[3]);

        opt1.setSelected(mTestEnvironment.isSelected(getAbsoluteQuestionNumber(), 0));
        opt2.setSelected(mTestEnvironment.isSelected(getAbsoluteQuestionNumber(), 1));
        opt3.setSelected(mTestEnvironment.isSelected(getAbsoluteQuestionNumber(), 2));
        opt4.setSelected(mTestEnvironment.isSelected(getAbsoluteQuestionNumber(), 3));
    }

    private Listening getListening(int listeningSetNumber) {
        return mTestEnvironment.mTest.getListening(listeningSetNumber);
    }

    private Listening getCurrentListening() {
        return getListening(mTestEnvironment.mCurrentQuestionSet);
    }

    public void loadListening() {
        questionNumber = 0;
        updateQuestion();
    }

    @Override
    public int getQuestionNumber() {
        return questionNumber;
    }

    @Override
    public int getAbsoluteQuestionNumber() {
        return getQuestionNumber() + getCurrentListening().getQuestionsOffset();
    }
}
