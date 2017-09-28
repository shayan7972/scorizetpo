package ir.scorize.tpo.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by mjafar on 9/15/17.
 */
public class ChoiceActionListener implements ActionListener {
    protected int mChoiceNumber;
    protected QuestionChoiceSaver mSaver;
    protected ButtonGroup mBtnGroup;
    protected TestContentPage mTestPage;

    public ChoiceActionListener(final int choiceNumber, final QuestionChoiceSaver saver, final ButtonGroup btnGroup, final TestContentPage testPage) {
        this.mChoiceNumber = choiceNumber;
        this.mSaver = saver;
        mBtnGroup = btnGroup;
        mTestPage = testPage;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int currentQuestionNumber = mTestPage.getAbsoluteQuestionNumber();
        int[] newChoices;

        if (this.mSaver.isSelected(currentQuestionNumber, this.mChoiceNumber)) {
            // Deselect this choice
            newChoices = new int[0];
            if (mBtnGroup != null) {
                mBtnGroup.clearSelection();
            }
        } else {
            // Change the choice
            newChoices = new int[1];
            newChoices[0] = mChoiceNumber;
        }
        this.mSaver.saveQuestionSelectedChoices(currentQuestionNumber, newChoices);
    }
}