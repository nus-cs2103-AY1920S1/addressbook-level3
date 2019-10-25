package seedu.address.model.statistics;

import java.util.List;

import javafx.util.Pair;
import seedu.address.model.question.Difficulty;
import seedu.address.model.question.Subject;

/**
 * Represents a stack bar chart object.
 */
public class StackBarChartModel {

    private Difficulty difficulty;
    private List<Pair<Subject, Integer>> data;

    public StackBarChartModel(Difficulty difficulty, List<Pair<Subject, Integer>> data) {
        this.difficulty = difficulty;
        this.data = data;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public List<Pair<Subject, Integer>> getData() {
        return data;
    }
}
