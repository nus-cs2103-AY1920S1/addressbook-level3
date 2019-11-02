package seedu.revision.model.util;

import seedu.revision.model.History;
import seedu.revision.model.ReadOnlyHistory;
import seedu.revision.model.quiz.Statistics;

/**
 * Contains utility methods for populating {@code History} with sample data.
 */
public class SampleHistoryUtil {
    public static Statistics[] getSampleStatistics() {
        return new Statistics[] {
            new Statistics("13/19"),
            new Statistics("10/19"),
            new Statistics("17/19"),
            new Statistics("15/19"),
            new Statistics("15/19"),
            new Statistics("13/19"),
            new Statistics("9/19"),
            new Statistics("11/19"),
            new Statistics("12/19"),
            new Statistics("9/19"),
        };
    }

    public static ReadOnlyHistory getSampleHistory() {
        History sampleHistory = new History();
        for (Statistics sampleStatistics : getSampleStatistics()) {
            sampleHistory.addStatistics(sampleStatistics);
        }

        return sampleHistory;
    }
}
