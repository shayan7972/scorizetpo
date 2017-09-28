package ir.scorize.tpo.models;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by mjafar on 8/19/17.
 */
public class Marker {
    private int startIndex;
    private int endIndex;
    private String type;
    private Collection<Integer> questions = new LinkedList<>();
    private String display;

    public Marker(int startIndex, int endIndex, String type, Collection<Integer> questions, String display) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.type = type;
        this.questions = questions;
        this.display = display;
    }

    public Marker(int startIndex, int endIndex, String type, Collection<Integer> questions) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.type = type;
        this.questions = questions;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public String getType() {
        return type;
    }

    public Collection<Integer> getQuestions() {
        return questions;
    }

    public String getDisplay() {
        return display;
    }

    @Override
    public String toString() {
        return String.format("Marker<%s> %2d -> %2d", type, startIndex, endIndex);
    }
}
