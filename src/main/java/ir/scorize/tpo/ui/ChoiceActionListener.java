package ir.scorize.tpo.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by mjafar on 9/15/17.
 */
public class ChoiceActionListener implements ActionListener {
    private int mChoiceNumber;
    private QuestionChoiceSaver mSaver;
    private ButtonGroup mBtnGroup;
    private TestContentPage mTestPage;

    public ChoiceActionListener(final int choiceNumber, final QuestionChoiceSaver saver, final ButtonGroup btnGroup, final TestContentPage testPage) {
        this.mChoiceNumber = choiceNumber;
        this.mSaver = saver;
        mBtnGroup = btnGroup;
        mTestPage = testPage;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int currentQuestionNumber = mTestPage.getAbsoluteQuestionNumber();

        if (this.mSaver.isSelected(currentQuestionNumber, this.mChoiceNumber)) {
            // Deselect this choice
            int[] newChoices = new int[mSaver.getQuestionSelectedChoices(currentQuestionNumber).length - 1];
            int i = 0;
            for(int choice : mSaver.getQuestionSelectedChoices(currentQuestionNumber)) {
                if (choice != mChoiceNumber) {
                    newChoices[i++] = choice;
                }
            }
            this.mSaver.saveQuestionSelectedChoices(currentQuestionNumber, newChoices);
            if (newChoices.length == 0) {
                mBtnGroup.clearSelection();
            }
        } else {
            // Change the choice
            int[] newChoices = new int[mSaver.getQuestionSelectedChoices(currentQuestionNumber).length - 1];
            int i = 0;
            for(int choice : mSaver.getQuestionSelectedChoices(currentQuestionNumber)) {
                newChoices[i++] = choice;
            }
            newChoices[i++] = mChoiceNumber;
            this.mSaver.saveQuestionSelectedChoices(currentQuestionNumber, newChoices);
        }
    }
}