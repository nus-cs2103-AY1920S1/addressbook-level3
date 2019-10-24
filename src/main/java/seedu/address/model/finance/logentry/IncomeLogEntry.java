package seedu.address.model.finance.logentry;

import java.util.Set;

import seedu.address.model.finance.attributes.Amount;
import seedu.address.model.finance.attributes.Category;
import seedu.address.model.finance.attributes.Description;
import seedu.address.model.finance.attributes.Person;
import seedu.address.model.finance.attributes.TransactionDate;
import seedu.address.model.finance.attributes.TransactionMethod;

/**
 * Represents an entry of income (inflow) in the finance log.
 * Guarantees: details are present and not null, field values are validated, mutable.
 */
public class IncomeLogEntry extends LogEntry {

    // Meta data
    public static final String LOG_ENTRY_TYPE = "income";

    // Fields
    private final Person from; // source of income

    /**
     * Every field must be present and not null.
     */
    public IncomeLogEntry(Amount amount, TransactionDate transactionDate, Description description,
                          TransactionMethod transactionMethod, Set<Category> categories,
                          Person from) {
        super(amount, transactionDate, description,
                transactionMethod, categories);
        this.from = from;
    }

    public String getLogEntryType() {
        return LOG_ENTRY_TYPE;
    }

    public Person getFrom() {
        return from;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof IncomeLogEntry)) {
            return false;
        }

        IncomeLogEntry otherLogEntry = (IncomeLogEntry) other;
        return otherLogEntry.getAmount().equals(getAmount())
                && otherLogEntry.getTransactionDate().equals(getTransactionDate())
                && otherLogEntry.getDescription().equals(getDescription())
                && otherLogEntry.getTransactionMethod().equals(getTransactionMethod())
                && otherLogEntry.getCategories().equals(getCategories())
                && otherLogEntry.getFrom().equals(getFrom());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Amount: ")
                .append(getAmount())
                .append(" Transaction Date: ")
                .append(getTransactionDate())
                .append(" Description: ")
                .append(getDescription())
                .append(" Transaction Method: ")
                .append(getTransactionMethod())
                .append(" From: ")
                .append(getFrom())
                .append(" Categories: ");
        getCategories().forEach(builder::append);
        return builder.toString();
    }
}
