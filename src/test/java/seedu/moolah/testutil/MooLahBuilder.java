package seedu.moolah.testutil;

import seedu.moolah.model.MooLah;
import seedu.moolah.model.expense.Event;
import seedu.moolah.model.expense.Expense;

/**
 * A utility class to help with building MooLah objects.
 * Example usage: <br>
 *     {@code MooLah ab = new MooLahBuilder().withExpense("John", "Doe").build();}
 */
public class MooLahBuilder {

    private MooLah mooLah;

    public MooLahBuilder() {
        mooLah = new MooLah();
    }

    public MooLahBuilder(MooLah mooLah) {
        this.mooLah = mooLah;
    }

    /**
     * Adds a new {@code Expense} to the {@code MooLah} that we are building.
     */
    public MooLahBuilder withExpense(Expense expense) {
        mooLah.addExpense(expense);
        return this;
    }

    /**
     * Adds a new {@code Event} to the {@code MooLah} that we are building.
     */
    public MooLahBuilder withEvent(Event event) {
        mooLah.addEvent(event);
        return this;
    }

    public MooLah build() {
        return mooLah;
    }
}
