package seedu.billboard.model.recurrence;

import static seedu.billboard.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.billboard.commons.core.date.DateInterval;
import seedu.billboard.model.expense.Amount;
import seedu.billboard.model.expense.CreatedDateTime;
import seedu.billboard.model.expense.Description;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.expense.ExpenseList;
import seedu.billboard.model.expense.Name;
import seedu.billboard.model.tag.Tag;

/**
 * Represents a recurrence in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Recurrence {

    public static final String MESSAGE_DATE_INTERVAL_CONSTRAINTS = "Date intervals should only be one of the"
            + "following formats: DAY, WEEK, MONTH, YEAR.";
    // Identity fields
    private ExpenseList expenses;
    private DateInterval interval;

    /**
     * Every field must be present and not null.
     */
    public Recurrence(ExpenseList expenses, DateInterval interval) {
        requireAllNonNull(expenses, interval);
        this.expenses = expenses;
        this.interval = interval;
    }

    public ExpenseList getExpenses() {
        return expenses;
    }

    public Expense getFirstExpense() {
        return expenses.get(0);
    }

    public Name getName() {
        return getFirstExpense().getName();
    }

    public Description getDescription() {
        return getFirstExpense().getDescription();
    }

    public Amount getAmount() {
        return getFirstExpense().getAmount();
    }

    public CreatedDateTime getCreated() {
        return getFirstExpense().getCreated();
    }

    public DateInterval getInterval() {
        return interval;
    }

    public int getIterations() {
        return expenses.size();
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(getFirstExpense().getTags());
    }

    public List<String> getTagNames() {
        return getFirstExpense().getTags().stream().map(t -> t.getTagName()).collect(Collectors.toList());
    }
    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Recurrence)) {
            return false;
        }

        Recurrence otherRecurrence = (Recurrence) other;
        return otherRecurrence.getFirstExpense().equals(getFirstExpense())
                && otherRecurrence.getInterval().equals(getInterval());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(expenses, interval);
    }

    @Override
    public String toString() {
        return "Name: "
                + getName()
                + " Description: "
                + getDescription()
                + " Amount: "
                + getAmount()
                + " Created: "
                + getCreated()
                + " Tags: "
                + getTags()
                + " Interval: "
                + getInterval()
                + " Iterations: "
                + getInterval();
    }
}
