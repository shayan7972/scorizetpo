package ir.scorize.tpo.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by mjafar on 9/28/17.
 */
public class MultiValueChoiceActionListener extends ChoiceActionListener {

    public MultiValueChoiceActionListener(int choiceNumber, QuestionChoiceSaver saver, ButtonGroup btnGroup, TestContentPage testPage) {
        super(choiceNumber, saver, btnGroup, testPage);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int currentQuestionNumber = mTestPage.getAbsoluteQuestionNumber();

        if (this.mSaver.isSelected(currentQuestionNumber, this.mChoiceNumber)) {
            // Deselect this choice
            int[] newChoices = new int[mSaver.getQuestionSelectedChoices(currentQuestionNumber).length - 1];
            int i = 0;
            for (int choice : mSaver.getQuestionSelectedChoices(currentQuestionNumber)) {
                if (choice != mChoiceNumber) {
                    newChoices[i++] = choice;
                }
            }
            this.mSaver.saveQuestionSelectedChoices(currentQuestionNumber, newChoices);
            if (newChoices.length == 0 && mBtnGroup != null) {
                mBtnGroup.clearSelection();
            }
        } else {
            // Change the choice
            int[] newChoices = new int[mSaver.getQuestionSelectedChoices(currentQuestionNumber).length + 1];
            int i = 0;
            for (int choice : mSaver.getQuestionSelectedChoices(currentQuestionNumber)) {
                newChoices[i++] = choice;
            }
            newChoices[i++] = mChoiceNumber;
            this.mSaver.saveQuestionSelectedChoices(currentQuestionNumber, newChoices);
        }
    }
}
