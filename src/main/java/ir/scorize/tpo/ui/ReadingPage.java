package ir.scorize.tpo.ui;

import ir.scorize.tpo.models.Marker;
import ir.scorize.tpo.models.Reading;
import ir.scorize.tpo.models.ReadingQuestion;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by mjafar on 8/5/17.
 */
public class ReadingPage implements TestContentPage {
    private JPanel mainPanel;
    private JPanel pnlQuestion;
    private JTextPane txtQuestion;
    private JTextPane txtText;
    private JRadioButton rdChoice1;
    private JRadioButton rdChoice2;
    private JRadioButton rdChoice3;
    private JRadioButton rdChoice4;
    private JTextPane txtAfterText;
    private JScrollPane pnlText;
    private ButtonGroup questionChoices;

    public static final String kDiamond = "\uD83D\uDD37";

    public static final char[] kNumbers = "0️⃣1️⃣2️⃣3️⃣3️⃣4️⃣".toCharArray();

    private static final SimpleAttributeSet kHighlighted = new SimpleAttributeSet();
    private static final SimpleAttributeSet kNormal = new SimpleAttributeSet();
    static {
        StyleConstants.setForeground(kHighlighted, Color.LIGHT_GRAY);
        StyleConstants.setForeground(kNormal, Color.WHITE);
    }

    /**
     * Local value for question number. between 0 to 20 something. not the absolute value.
     */
    private int questionNumber;
    private TestEnvironment mTestEnvironment;

    private TreeMap<Integer, Collection<Marker>> mMarkersMap = new TreeMap<>();

    public ReadingPage(TestEnvironment testEnvironment) {
        super();
        this.mTestEnvironment = testEnvironment;
        mainPanel.setLayout(new GridLayout(1, 2));
        setupChoiceActionListeners(testEnvironment);
    }

    private void setupChoiceActionListeners(TestEnvironment testEnvironment) {
        rdChoice1.addActionListener(new ChoiceActionListener(0, testEnvironment, questionChoices, this));
        rdChoice2.addActionListener(new ChoiceActionListener(1, testEnvironment, questionChoices, this));
        rdChoice3.addActionListener(new ChoiceActionListener(2, testEnvironment, questionChoices, this));
        rdChoice4.addActionListener(new ChoiceActionListener(3, testEnvironment, questionChoices, this));
    }

    public void loadReading(int passageNumber) {
        Reading currentReading = getReading(passageNumber);

        String readingContent = currentReading.getContent();
        txtText.setText(readingContent);
        parseMarkers(txtText.getText());

        setChoicesVisible(false);
        questionNumber = -1;
        mTestEnvironment.mTopPart.setTitle(String.format("Passage %d of %d", passageNumber + 1, mTestEnvironment.mTest.readingCount()));
    }


    private void parseMarkers(String text) {
        mMarkersMap.clear();

        int i = 0;
        while (i < text.length() && i != -1) {
            // A <T P1="V1" P2="V2 V3"> B </T> C
            //   ^i                   ^j  ^k
            i = text.indexOf('<', i);
            if (i  == -1) break;
            int j = text.indexOf('>', i);
            String tagDefinition = text.substring(i+1, j);
            if (tagDefinition.charAt(0) != '/') {
                int l = tagDefinition.indexOf(' ');
                if (l == -1) {
                    l = j - i - 2 + 1;
                }
                String tagName = tagDefinition.substring(0, l);
                int k = text.indexOf("</" + tagName + ">", j);

                String[] questsions = null;
                String style = "";

                ++l;
                while (l != -1 && l < tagDefinition.length()) {
                    int m;
                    m = tagDefinition.indexOf('=', l);
                    String propertyName = tagDefinition.substring(l, m).trim();
                    l = tagDefinition.indexOf('"', m);
                    m = tagDefinition.indexOf('"', l + 1);
                    String propertyValue = tagDefinition.substring(l + 1, m);

                    if ("question".equalsIgnoreCase(propertyName)) {
                        questsions = propertyValue.split("\\s+");
                    } else if ("style".equalsIgnoreCase(propertyName)) {
                        style = propertyValue;
                    }
                    l = m + 1;
                }

                if ("marker".equalsIgnoreCase(tagName) || "highlight".equalsIgnoreCase(tagName)) {
                    assert questsions != null;
                    List<Integer> questionsInt = Arrays.stream(questsions).map(Integer::parseInt)
                            .map(X -> X-1).collect(Collectors.toList());

                    Marker marker = new Marker(j, k, tagName.toLowerCase(), questionsInt, style);
                    for (int n : questionsInt) {
                        List<Marker> tmpList = (List<Marker>) mMarkersMap.get(n);
                        if (tmpList == null) {
                            tmpList = new LinkedList<>();
                        }
                        tmpList.add(marker);
                        mMarkersMap.put(n, tmpList);
                    }
                }

            }
            i = j + 1;
        }

        for (Map.Entry<Integer, Collection<Marker>> m : mMarkersMap.entrySet()) {
            System.out.println(m.getKey());
            for (Marker k : m.getValue()) {
                System.out.println("  > " + k);
            }
        }
    }

    private void setChoicesVisible(boolean visibility) {
        JComponent[] items = new JComponent[] {txtQuestion, rdChoice1, rdChoice2, rdChoice3, rdChoice4, txtAfterText};
        for(JComponent item:items) item.setVisible(visibility);
    }

    private void removeMarkers() {
        LinkedList<Marker> tmpList;

        System.out.println("Remove old markers ["+getAbsoluteQuestionNumber()+"]");
        tmpList = (LinkedList<Marker>) mMarkersMap.getOrDefault(getAbsoluteQuestionNumber(), new LinkedList<>());
        Highlighter highlighter = txtText.getHighlighter();
        highlighter.removeAllHighlights();
        for (Marker m : tmpList) {
            if (m.getType().equals("marker")) {
                try {
                    txtText.getDocument().remove(m.getStartIndex()-1, kDiamond.length());
                    System.out.println(" Removed " + m);
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void addMarkers() {
        LinkedList<Marker> tmpList;

        System.out.println("Add new markers ["+getAbsoluteQuestionNumber()+"]");
        Highlighter highlighter = txtText.getHighlighter();
        tmpList = (LinkedList<Marker>) mMarkersMap.getOrDefault(getAbsoluteQuestionNumber(), new LinkedList<>());
        int offset = -1;
        for (Marker m : tmpList) {
            if (m.getType().equals("marker")) {
                try {
                    txtText.getDocument().insertString(m.getStartIndex() + offset, kDiamond, null);
                    offset += kDiamond.length();
                    System.out.println(" Added " + m);
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            } else if (m.getType().equals("highlight")) {
                try {
                    highlighter.addHighlight(m.getStartIndex() + offset, m.getEndIndex() + offset,
                            new DefaultHighlighter.DefaultHighlightPainter(Color.LIGHT_GRAY));
                    System.out.println(" Added " + m);
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void updateQuestion() {
        System.out.println("ReadingPage.updateQuestion");
        System.out.println("questionNumber = [" + questionNumber + "]");

        Reading currentReading = getCurrentReading();

        ReadingQuestion question = currentReading.getQuestions()[questionNumber];
        String[] choices = question.getChoices();

        txtQuestion.setText(String.format("%d. %s", getAbsoluteQuestionNumber()+1, question.getQuestionText()));
        txtAfterText.setText(question.getFollowingText());
        rdChoice1.setText(choices[0]);
        rdChoice2.setText(choices[1]);
        rdChoice3.setText(choices[2]);
        rdChoice4.setText(choices[3]);

        questionChoices.clearSelection();
        if (mTestEnvironment.isSelected(getAbsoluteQuestionNumber(), 0)) rdChoice1.setSelected(true);
        if (mTestEnvironment.isSelected(getAbsoluteQuestionNumber(), 1)) rdChoice2.setSelected(true);
        if (mTestEnvironment.isSelected(getAbsoluteQuestionNumber(), 2)) rdChoice3.setSelected(true);
        if (mTestEnvironment.isSelected(getAbsoluteQuestionNumber(), 3)) rdChoice4.setSelected(true);
    }

    private Reading getReading(int passageNumber) {
        return mTestEnvironment.mTest.getReading(passageNumber);
    }

    private Reading getCurrentReading() {
        return getReading(mTestEnvironment.mCurrentQuestionSet);
    }

    @Override
    public JPanel getContentPart() {
        return mainPanel;
    }

    @Override
    public void handleContinueClick(TestEnvironment te) {
        nextReading(te);
    }

    private void nextReading(TestEnvironment te) {
        try {
            questionNumber = -1;
            setChoicesVisible(false);
            loadReading(++te.mCurrentQuestionSet);
            te.setContentPart(this, questionNumber);
        } catch (ArrayIndexOutOfBoundsException ex2) {
            // Continue to the listening part
            String title = "Finish Warning";
            String text = "You have seen all of the questions in this part of the Reading Section.\n" +
                    "You may go back and Review. As long as there is time remaining, you can check your work.\n" +
                    "Click on Cancel to continue working. \n" +
                    "Click on Ok to go on. Once you leave this part of the Reading section, you WILL NOT " +
                    "be able to return to it. ";
            int result = JOptionPane.showConfirmDialog(te, text, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                java.awt.EventQueue.invokeLater(() -> {
                    te.mTopPart.setTitle("Listening");
                    te.setContentPart(new ListeningDirections(), 0);
                    te.invalidate();
                    te.repaint();
                });
            }
        }
    }

    private void prevReading(TestEnvironment te) {
        int t = questionNumber;
        try {
            questionNumber = -1;
            setChoicesVisible(false);
            loadReading(--te.mCurrentQuestionSet);
            te.setContentPart(this, questionNumber);
        } catch (ArrayIndexOutOfBoundsException ignored) {
            questionNumber = t;
        }
    }

    @Override
    public void handleNextClick(TestEnvironment te) {
        try {
            // TODO: check if read the whole text
            removeMarkers();
            ++questionNumber;
            updateQuestion();
            setChoicesVisible(true);
            addMarkers();
        } catch (ArrayIndexOutOfBoundsException ex) {
            // It's the last question
            nextReading(te);
        }
    }

    @Override
    public void handlePrevClick(TestEnvironment te) {
        try {
            removeMarkers();
            --questionNumber;
            updateQuestion();
            setChoicesVisible(true);
            addMarkers();
        } catch (ArrayIndexOutOfBoundsException ex) {
            // It's the last question
            prevReading(te);
        }
    }

    @Override
    public int getQuestionNumber() {
        return questionNumber;
    }

    @Override
    public int getAbsoluteQuestionNumber() {
        return getQuestionNumber() + getCurrentReading().getQuestionsOffset();
    }
}
