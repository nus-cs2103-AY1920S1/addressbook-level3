package seedu.ichifund.model.repeater;

import static seedu.ichifund.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.ichifund.model.Amount;
import seedu.ichifund.model.Description;
import seedu.ichifund.model.transaction.Category;

/**
 * Represents a Repeater in IchiFund.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Repeater {
    private final Amount amount;
    private final Description description;
    private final Category category;
    private final MonthOffset monthStartOffset;
    private final MonthOffset monthEndOffset;

    public Repeater(Description description, Amount amount, Category category,
            MonthOffset monthStartOffset, MonthOffset monthEndOffset) {
        requireAllNonNull(description, amount, category, monthStartOffset, monthEndOffset);

        this.description = description;
        this.amount = amount;
        this.category = category;
        this.monthStartOffset = monthStartOffset;
        this.monthEndOffset = monthEndOffset;
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

    public MonthOffset getMonthStartOffset() {
        return this.monthStartOffset;
    }

    public MonthOffset getMonthEndOffset() {
        return this.monthEndOffset;
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
                && otherRepeater.getMonthStartOffset().equals(getMonthStartOffset())
                && otherRepeater.getMonthEndOffset().equals(getMonthEndOffset());
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
                && otherRepeater.getMonthStartOffset().equals(getMonthStartOffset())
                && otherRepeater.getMonthEndOffset().equals(getMonthEndOffset());
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, amount, category, monthStartOffset, monthEndOffset);
    }
}
