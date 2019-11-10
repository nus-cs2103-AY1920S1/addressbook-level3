package seedu.ichifund.model.repeater;

import static seedu.ichifund.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.ichifund.model.Description;
import seedu.ichifund.model.amount.Amount;
import seedu.ichifund.model.date.Date;
import seedu.ichifund.model.transaction.Category;
import seedu.ichifund.model.transaction.TransactionType;

/**
 * Represents a Repeater in IchiFund.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Repeater {
    private final RepeaterUniqueId uniqueId;
    private final Description description;
    private final Amount amount;
    private final Category category;
    private final TransactionType transactionType;
    private final MonthOffset monthStartOffset;
    private final MonthOffset monthEndOffset;
    private final Date startDate;
    private final Date endDate;

    public Repeater(RepeaterUniqueId uniqueId, Description description, Amount amount, Category category,
            TransactionType transactionType, MonthOffset monthStartOffset, MonthOffset monthEndOffset,
            Date startDate, Date endDate) {

        requireAllNonNull(uniqueId, description, amount, category, transactionType, monthStartOffset,
                monthEndOffset, startDate, endDate);

        this.uniqueId = uniqueId;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.transactionType = transactionType;
        this.monthStartOffset = monthStartOffset;
        this.monthEndOffset = monthEndOffset;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public RepeaterUniqueId getUniqueId() {
        return this.uniqueId;
    }

    public Description getDescription() {
        return this.description;
    }

    public Amount getAmount() {
        return this.amount;
    }

    public Category getCategory() {
        return this.category;
    }

    public TransactionType getTransactionType() {
        return this.transactionType;
    }

    public boolean isExpenditure() {
        return transactionType.isExpenditure();
    }

    public MonthOffset getMonthStartOffset() {
        return this.monthStartOffset;
    }

    public MonthOffset getMonthEndOffset() {
        return this.monthEndOffset;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    /**
     * Returns true if both repeaters of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two repeaters.
     */
    public boolean isSameRepeater(Repeater otherRepeater) {
        if (otherRepeater == this) {
            return true;
        }

        return otherRepeater != null
                && otherRepeater.getDescription().equals(getDescription())
                && otherRepeater.getAmount().equals(getAmount())
                && otherRepeater.getCategory().equals(getCategory())
                && otherRepeater.getTransactionType().equals(getTransactionType())
                && otherRepeater.getMonthStartOffset().equals(getMonthStartOffset())
                && otherRepeater.getMonthEndOffset().equals(getMonthEndOffset())
                && otherRepeater.getStartDate().equals(getStartDate())
                && otherRepeater.getEndDate().equals(getEndDate());
    }

    /**
     * Returns true if both repeaters have the same identity and data fields.
     * This defines a stronger notion of equality between two repeaters.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Repeater)) {
            return false;
        }

        Repeater otherRepeater = (Repeater) other;
        return otherRepeater.getDescription().equals(getDescription())
                && otherRepeater.getAmount().equals(getAmount())
                && otherRepeater.getCategory().equals(getCategory())
                && otherRepeater.getTransactionType().equals(getTransactionType())
                && otherRepeater.getMonthStartOffset().equals(getMonthStartOffset())
                && otherRepeater.getMonthEndOffset().equals(getMonthEndOffset())
                && otherRepeater.getStartDate().equals(getStartDate())
                && otherRepeater.getEndDate().equals(getEndDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(uniqueId, description, amount, category, transactionType,
                monthStartOffset, monthEndOffset, startDate, endDate);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDescription())
                .append(" Amount: ")
                .append(getAmount())
                .append(" Category: ")
                .append(getCategory())
                .append(" Transaction Type: ")
                .append(getTransactionType())
                .append(" Month Start Offset: ")
                .append(getMonthStartOffset())
                .append(" Month End Offset: ")
                .append(getMonthEndOffset())
                .append(" Start Date: ")
                .append(getStartDate())
                .append(" End Date: ")
                .append(getEndDate());
        return builder.toString();
    }
}
