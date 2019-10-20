package seedu.address.model.finance.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.finance.FinanceLog;
import seedu.address.model.finance.ReadOnlyFinanceLog;
import seedu.address.model.finance.attributes.Category;
import seedu.address.model.finance.attributes.TransactionMethod;
import seedu.address.model.finance.logentry.Amount;
import seedu.address.model.finance.logentry.Description;
import seedu.address.model.finance.logentry.LogEntry;
import seedu.address.model.finance.logentry.SpendLogEntry;
import seedu.address.model.finance.logentry.TransactionDate;

/**
 * Contains utility methods for populating {@code FinanceLog} with sample data.
 */
public class SampleDataUtil {
    public static LogEntry[] getSampleLogEntries() {
        return new LogEntry[] {
            new SpendLogEntry(new Amount("2.80"),
                    new TransactionDate("11-10-2019"),
                    new Description("Yong Tau Foo"),
                    new TransactionMethod("Cash"),
                    getCategorySet("Food", "School")),
            new SpendLogEntry(new Amount("50"),
                    new TransactionDate("13-10-2019"),
                    new Description("Dian Xiao Er"),
                    new TransactionMethod("Cash"),
                    getCategorySet("Mother", "Birthday"))
        };
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
