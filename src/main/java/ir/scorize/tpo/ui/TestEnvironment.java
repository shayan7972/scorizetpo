package ir.scorize.tpo.ui;

import ir.scorize.tpo.models.Reading;
import ir.scorize.tpo.models.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TreeMap;

/**
 * Created by mjafar on 8/4/17.
 */
public class TestEnvironment extends JFrame implements QuestionChoiceSaver {
    TopPart mTopPart;
    private TestContentPage mCurrentContentPart;
    private JPanel mCurrentContentPartPanel;

    Test mTest;

//    int mCurrentPageParam;

    int mCurrentQuestionSet = 0;

    public TestEnvironment(int examNumber) {
        super("Scorize TOEFL preperation software");

        mTest = new Test(examNumber);

        BoxLayout flow = new BoxLayout(getContentPane(), BoxLayout.Y_AXIS);
        setLayout(flow);

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        setBounds(50, 50, width - 100, height - 100);

        setUpTopPart(width, height);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void setUpTopPart(int width, int height) {
        mTopPart = new TopPart();
        mTopPart.btnNext.addActionListener(mNextClicked);
        mTopPart.btnBack.addActionListener(mBackClicked);
        mTopPart.btnOk.addActionListener(mOkClicked);
        mTopPart.btnPreview.addActionListener(mPreviewClicked);
        mTopPart.btnHelp.addActionListener(mHelpClicked);
        mTopPart.btnContinue.addActionListener(mContinueClicked);
        mTopPart.btnHideTime.addActionListener(mHideTimeClicked);
        mTopPart.btnPause.addActionListener(mPauseClicked);

        JPanel topPartContentPart = mTopPart.mPanel;
        topPartContentPart.setMaximumSize(new Dimension(width, 174));
        add(topPartContentPart);
    }

    public void setContentPart(TestContentPage contentPart, int params) {
        if (mCurrentContentPartPanel != null) {
            remove(mCurrentContentPartPanel);
        }
        mCurrentContentPart = contentPart;
        mCurrentContentPartPanel = contentPart.getContentPart();
        add(mCurrentContentPartPanel);
        invalidate();
        repaint();
    }


    ActionListener mNextClicked = (e) -> mCurrentContentPart.handleNextClick(TestEnvironment.this);
    ActionListener mBackClicked = (e) -> mCurrentContentPart.handlePrevClick(TestEnvironment.this);
    ActionListener mOkClicked = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    };
    ActionListener mPreviewClicked = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    };
    ActionListener mHelpClicked = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    };
    ActionListener mContinueClicked = (e) -> mCurrentContentPart.handleContinueClick(TestEnvironment.this);
    ActionListener mHideTimeClicked = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    };
    ActionListener mPauseClicked = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    };


    private TreeMap<Integer, int[]> mAnswers = new TreeMap<>();

    @Override
    public void saveQuestionSelectedChoices(int questionNumber, int[] choiceNumber) {
        if (choiceNumber.length == 0) {
            mAnswers.remove(questionNumber);
        } else {
            mAnswers.put(questionNumber, choiceNumber);
        }
    }

    @Override
    public int[] getQuestionSelectedChoices(int questionNumber) {
        return mAnswers.containsKey(questionNumber) ? mAnswers.get(questionNumber) : new int[0];
    }

    @Override
    public boolean isSelected(int questionNumber, int choiceNumber) {
        for (int choice : getQuestionSelectedChoices(questionNumber)) {
            if (choice == choiceNumber)
                return true;
        }

        return false;
    }
}