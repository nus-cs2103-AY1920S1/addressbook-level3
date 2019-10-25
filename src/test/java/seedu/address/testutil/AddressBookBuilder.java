package seedu.address.testutil;

import seedu.address.model.MooLah;
import seedu.address.model.expense.Expense;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code MooLah ab = new AddressBookBuilder().withExpense("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private MooLah mooLah;

    public AddressBookBuilder() {
        mooLah = new MooLah();
    }

    public AddressBookBuilder(MooLah mooLah) {
        this.mooLah = mooLah;
    }

    /**
     * Adds a new {@code Expense} to the {@code MooLah} that we are building.
     */
    public AddressBookBuilder withExpense(Expense expense) {
        mooLah.addExpense(expense);
        return this;
    }

    public MooLah build() {
        return mooLah;
    }
}
