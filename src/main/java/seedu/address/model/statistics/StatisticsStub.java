package seedu.address.model.statistics;

import java.util.Arrays;
import java.util.List;

/**
 * A stub for the statistics feature. Used in place of storage.
 */
public class StatisticsStub {

    private List<String> wrongQns;
    private List<String> correctQns;
    private List<Integer> statistics;

    public StatisticsStub() {
        this.wrongQns = Arrays.asList("Q1", "Q2", "Q3");
        this.correctQns = Arrays.asList("Q4", "Q5", "Q6");
        this.statistics = Arrays.asList(35, 5);
    }

    public List<Integer> getStatistics() {
        return statistics;
    }

    public List<String> getCorrectQuestions() {
        return correctQns;
    }

    public List<String> getWrongQns() {
        return wrongQns;
    }
}
