package seedu.address.model.finance.logentry;

import java.util.Set;

import seedu.address.model.finance.attributes.Amount;
import seedu.address.model.finance.attributes.Category;
import seedu.address.model.finance.attributes.Description;
import seedu.address.model.finance.attributes.Person;
import seedu.address.model.finance.attributes.RepaidDate;
import seedu.address.model.finance.attributes.TransactionDate;
import seedu.address.model.finance.attributes.TransactionMethod;

/**
 * Represents an entry of borrowing from someone in the finance log.
 * Guarantees: details are present and not null, field values are validated, mutable.
 */
public class BorrowLogEntry extends LogEntry {

    // Meta data
    public static final String LOG_ENTRY_TYPE = "borrow";
    private boolean isRepaid; // whether borrowed money has been returned to lender
    private RepaidDate repaidDate; // date when user repaid money

    // Fields
    private final Person from; // person borrowed from

    /**
     * Every field must be present and not null.
     */
    public BorrowLogEntry(Amount amount, TransactionDate transactionDate, Description description,
                          TransactionMethod transactionMethod, Set<Category> categories,
                          Person from) {
        super(amount, transactionDate, description,
                transactionMethod, categories);
        this.from = from;
        this.isRepaid = false;
    }

    public String getLogEntryType() {
        return LOG_ENTRY_TYPE;
    }

    public Person getFrom() {
        return from;
    }

    public boolean isRepaid() {
        return isRepaid;
    }

    /**
     * Marks log entry as repaid
     * (i.e. money returned to person borrowed from)
     * and records down the day of repayment.
     */
    public void markAsRepaid() {
        this.isRepaid = true;
        this.repaidDate = new RepaidDate();
    }

    public RepaidDate getRepaidDate() {
        return this.repaidDate;
    }

    public void setRepaidDate(String rDate, String tDate) {
        repaidDate = new RepaidDate(rDate, tDate);
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
                && (otherLogEntry.isRepaid() == isRepaid())
                && ((otherLogEntry.getRepaidDate() == null && getRepaidDate() == null)
                || otherLogEntry.getRepaidDate().equals(getRepaidDate()));
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
                .append(" Is Repaid: ")
                .append(isRepaid())
                .append(isRepaid() ? " Repaid Date:" : "")
                .append(isRepaid() ? getRepaidDate() : "")
                .append(" Categories: ");
        getCategories().forEach(builder::append);
        return builder.toString();
    }
}
