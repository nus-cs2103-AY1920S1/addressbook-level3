package seedu.address.model.finance.logentry;

import java.util.Set;

import seedu.address.model.finance.attributes.Amount;
import seedu.address.model.finance.attributes.Category;
import seedu.address.model.finance.attributes.Description;
import seedu.address.model.finance.attributes.Place;
import seedu.address.model.finance.attributes.TransactionDate;
import seedu.address.model.finance.attributes.TransactionMethod;

/**
 * Represents an entry of spending (outflow) in the finance log.
 * Guarantees: details are present and not null, field values are validated, mutable.
 */
public class SpendLogEntry extends LogEntry {

    // Meta data
    public static final String LOG_ENTRY_TYPE = "spend";

    // Fields
    private final Place place;

    /**
     * Every field must be present and not null.
     */
    public SpendLogEntry(Amount amount, TransactionDate transactionDate, Description description,
                         TransactionMethod transactionMethod, Set<Category> categories,
                         Place place) {
        super(amount, transactionDate, description,
                transactionMethod, categories);
        this.place = place;
    }

    public String getLogEntryType() {
        return LOG_ENTRY_TYPE;
    }

    public Place getPlace() {
        return place;
    }

    public void markAsRepaid() {}

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SpendLogEntry)) {
            return false;
        }

        SpendLogEntry otherLogEntry = (SpendLogEntry) other;
        return otherLogEntry.getAmount().equals(getAmount())
                && otherLogEntry.getTransactionDate().equals(getTransactionDate())
                && otherLogEntry.getDescription().equals(getDescription())
                && otherLogEntry.getTransactionMethod().equals(getTransactionMethod())
                && otherLogEntry.getCategories().equals(getCategories())
                && otherLogEntry.getPlace().equals(getPlace());
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
                .append(" Place: ")
                .append(getPlace())
                .append(" Categories: ");
        getCategories().forEach(builder::append);
        return builder.toString();
    }
}
