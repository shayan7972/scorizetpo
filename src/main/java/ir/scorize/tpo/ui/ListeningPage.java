package ir.scorize.tpo.ui;

import ir.scorize.tpo.models.Listening;

import javax.swing.*;

/**
 * Created by mjafar on 9/8/17.
 */
public class ListeningPage implements TestContentPage {
    private JProgressBar pbProgress;
    private JLabel lblPictures;
    private JPanel pnlMain;
    private TestEnvironment mTestEnvironment;
    private Listening mCurrentListening;

    public ListeningPage(TestEnvironment testEnvironment) {
        super();
        this.mTestEnvironment = testEnvironment;
    }

    @Override
    public JPanel getContentPart() {
        return pnlMain;
    }

    @Override
    public void handleContinueClick(TestEnvironment te) {
        // Go right to the questions
        java.awt.EventQueue.invokeLater(() -> {
            ListeningQuestion listeningPage = new ListeningQuestion(te);
            listeningPage.loadListening();
            te.setContentPart(listeningPage, -1);
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

    public void loadListening(int currentQuestionSet) {
        Listening currentListening = mTestEnvironment.mTest.getListening(currentQuestionSet);
        mCurrentListening = currentListening;

        String picturesFile = currentListening.getPicturesFileName();
        String listeningFile = currentListening.getFileName();

        lblPictures.setIcon(new ImageIcon(picturesFile));
        // TODO: play file
    }
}
