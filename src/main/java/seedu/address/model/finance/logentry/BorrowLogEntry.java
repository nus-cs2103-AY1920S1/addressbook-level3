package seedu.address.model.finance.logentry;

import java.util.Set;

import seedu.address.model.finance.attributes.Amount;
import seedu.address.model.finance.attributes.Category;
import seedu.address.model.finance.attributes.Deadline;
import seedu.address.model.finance.attributes.Description;
import seedu.address.model.finance.attributes.Person;
import seedu.address.model.finance.attributes.TransactionDate;
import seedu.address.model.finance.attributes.TransactionMethod;

/**
 * Represents an entry of borrowing from someone in the finance log.
 * Guarantees: details are present and not null, field values are validated, mutable.
 */
public class BorrowLogEntry extends LogEntry {

    // Meta data
    public static final String LOG_ENTRY_TYPE = "borrow";
    private static boolean isRepaid; // whether borrowed money has been returned to lender

    // Fields
    private final Person from; // person borrowed from
    private Deadline deadline;

    /**
     * Every field must be present and not null.
     */
    public BorrowLogEntry(Amount amount, TransactionDate transactionDate, Description description,
                          TransactionMethod transactionMethod, Set<Category> categories,
                          Person from, Deadline deadline) {
        super(amount, transactionDate, description,
                transactionMethod, categories);
        this.from = from;
        this.deadline = deadline;
        this.isRepaid = false;
    }

    public String getLogEntryType() {
        return LOG_ENTRY_TYPE;
    }

    public Person getFrom() {
        return from;
    }

    public Deadline getDeadline() {
        return deadline;
    }

    public boolean getIsRepaid() {
        return isRepaid;
    }

    public void setRepaid() {
        isRepaid = true;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof BorrowLogEntry)) {
            return false;
        }

        BorrowLogEntry otherLogEntry = (BorrowLogEntry) other;
        return otherLogEntry.getAmount().equals(getAmount())
                && otherLogEntry.getTransactionDate().equals(getTransactionDate())
                && otherLogEntry.getDescription().equals(getDescription())
                && otherLogEntry.getTransactionMethod().equals(getTransactionMethod())
                && otherLogEntry.getCategories().equals(getCategories())
                && otherLogEntry.getFrom().equals(getFrom())
                && otherLogEntry.getDeadline().equals(getDeadline())
                && (otherLogEntry.getIsRepaid() == getIsRepaid());
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
                .append(" Deadline: ")
                .append(getDeadline())
                .append(" Categories: ");
        getCategories().forEach(builder::append);
        return builder.toString();
    }
}
