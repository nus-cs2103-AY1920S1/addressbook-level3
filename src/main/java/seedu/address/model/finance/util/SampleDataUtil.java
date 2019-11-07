package seedu.address.model.finance.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.finance.FinanceLog;
import seedu.address.model.finance.ReadOnlyFinanceLog;
import seedu.address.model.finance.attributes.Category;
import seedu.address.model.finance.logentry.LogEntry;

/**
 * Contains utility methods for populating {@code FinanceLog} with sample data.
 */
public class SampleDataUtil {
    public static LogEntry[] getSampleLogEntries() {
        return new LogEntry[]{};
    }

    public static ReadOnlyFinanceLog getSampleFinanceLog() {
        FinanceLog sampleFl = new FinanceLog();
        for (LogEntry sampleLogEntry : getSampleLogEntries()) {
            sampleFl.addLogEntry(sampleLogEntry);
        }
        return sampleFl;
    }

    /**
     * Returns a set of categories containing the list of strings given.
     */
    public static Set<Category> getCategorySet(String... strings) {
        return Arrays.stream(strings)
                .map(Category::new)
                .collect(Collectors.toSet());
    }

}
