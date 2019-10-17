package seedu.address.model.finance.util;

import seedu.address.model.finance.FinanceLog;
import seedu.address.model.finance.ReadOnlyFinanceLog;
import seedu.address.model.finance.logentry.Amount;
import seedu.address.model.finance.logentry.Description;
import seedu.address.model.finance.logentry.LogEntry;
import seedu.address.model.finance.logentry.TransactionDate;

/**
 * Contains utility methods for populating {@code FinanceLog} with sample data.
 */
public class SampleDataUtil {
    public static LogEntry[] getSampleLogEntries() {
        return new LogEntry[] {
            new LogEntry(new Amount("Alex Yeoh"),
                    new TransactionDate("87438807"),
                    new Description("alexyeoh@example.com")),
            new LogEntry(new Amount("Bernice Yu"),
                    new TransactionDate("99272758"),
                    new Description("berniceyu@example.com")),
            new LogEntry(new Amount("Charlotte Oliveiro"),
                    new TransactionDate("93210283"),
                    new Description("charlotte@example.com"))
        };
    }

    public static ReadOnlyFinanceLog getSampleFinanceLog() {
        FinanceLog sampleFl = new FinanceLog();
        for (LogEntry sampleLogEntry : getSampleLogEntries()) {
            sampleFl.addLogEntry(sampleLogEntry);
        }
        return sampleFl;
    }

}
