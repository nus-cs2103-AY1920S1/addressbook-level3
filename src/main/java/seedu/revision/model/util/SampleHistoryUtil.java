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
            new Statistics("13/19,7/7,5/5,1/7"),
            new Statistics("10/19,3/7,3/5,4/7"),
            new Statistics("17/19,6/7,5/5,6/7"),
            new Statistics("15/19,5/7,4/5,6/7"),
            new Statistics("15/19,5/7,5/5,5/7"),
            new Statistics("13/19,4/7,2/5,7/7"),
            new Statistics("9/19,7/7,1/5,1/7"),
            new Statistics("11/19,6/7,0/5,5/7"),
            new Statistics("12/19,4/7,4/5,4/7"),
            new Statistics("9/19,4/7,5/5,0/7"),
            new Statistics("13/19,7/7,5/5,1/7"),
            new Statistics("10/19,3/7,3/5,4/7"),
            new Statistics("14/19,6/7,4/5,4/7"),
            new Statistics("8/19,5/7,3/5,0/7"),
            new Statistics("15/19,6/7,4/5,5/7"),
            new Statistics("16/19,5/7,4/5,7/7"),
            new Statistics("12/19,7/7,4/5,1/7"),
            new Statistics("11/19,5/7,0/5,6/7"),
            new Statistics("12/19,3/7,4/5,5/7"),
            new Statistics("11/19,6/7,5/5,0/7"),
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
