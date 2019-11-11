package seedu.revision.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.revision.model.History;
import seedu.revision.model.quiz.Statistics;

/**
 * A utility class containing a list of {@code Statistics} objects to be used in tests.
 */
public class TypicalHistory {

    public static final Statistics STATISTICSA = new Statistics("25/25,10/10,8/8,7/7");
    public static final Statistics STATISTICSB = new Statistics("23/25,9/10,8/8,6/7");
    public static final Statistics STATISTICSC = new Statistics("10/25,5/10,3/8,2/7");

    private TypicalHistory() {} // prevents instantiation

    /**
     * Returns a {@code History} with all the typical statistics.
     */
    public static History getTypicalHistory() {
        History history = new History();
        for (Statistics statistics : getTypicalStatistics()) {
            history.addStatistics(statistics);
        }
        return history;
    }

    public static List<Statistics> getTypicalStatistics() {
        return new ArrayList<>(Arrays.asList(STATISTICSA, STATISTICSB, STATISTICSC));
    }
}
