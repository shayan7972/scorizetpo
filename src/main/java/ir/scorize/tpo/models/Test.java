package ir.scorize.tpo.models;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by mjafar on 9/9/17.
 */
public class Test {
    private Reading[] mReadings;
    private Listening[] mListenings;
    private int examNumber;

    public Test(int examNumber) {
        this.examNumber = examNumber;
        int offset = 0;
        offset = prefetchReadings(offset);
        offset = prefetchListenings(offset);
    }

    private int prefetchListenings(int offset) {
        List<Listening> loadListenings = new LinkedList<>();
        Listening r;
        int i = 0;
        do {
            r = Listening.loadListening(examNumber, i++, offset);
            if (r != null) {
                offset += r.getQuestionsCount();
                loadListenings.add(r);
            }
        } while (r != null);
        mListenings = loadListenings.toArray(new Listening[0]);

        return offset;
    }

    private int prefetchReadings(int offset) {
        List<Reading> loadReadings = new LinkedList<>();
        Reading r;
        int i = 0;
        do {
            r = Reading.loadReading(examNumber, i++, offset);
            if (r != null) {
                offset += r.getQuestionsCount();
                loadReadings.add(r);
            }
        } while (r != null);
        mReadings = loadReadings.toArray(new Reading[0]);

        return offset;
    }

    public Reading getReading(int currentQuestionSet) {
        return mReadings[currentQuestionSet];
    }

    public int readingCount() {
        return mReadings.length;
    }
    
    public Listening getListening(int currentQuestionSet) {
        return mListenings[currentQuestionSet];
    }

    public int listeningCount() {
        return mListenings.length;
    }
}
