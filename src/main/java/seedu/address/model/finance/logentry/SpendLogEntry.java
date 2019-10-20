package seedu.address.model.finance.logentry;

import java.util.Set;

import seedu.address.model.finance.attributes.Category;
import seedu.address.model.finance.attributes.TransactionMethod;

/**
 * Represents an entry in the finance log.
 * Guarantees: details are present and not null, field values are validated, mutable.
 */
public class SpendLogEntry extends LogEntry {

    // Meta data
    public static final String LOG_ENTRY_TYPE = "expenditure";

    /**
     * Every field must be present and not null.
     */
    public SpendLogEntry(Amount amount, TransactionDate transactionDate, Description description,
                         TransactionMethod transactionMethod, Set<Category> categories) {
        super(amount, transactionDate, description,
                transactionMethod, categories);
    }

    public String getLogEntryType() {
        return LOG_ENTRY_TYPE;
    }

}
