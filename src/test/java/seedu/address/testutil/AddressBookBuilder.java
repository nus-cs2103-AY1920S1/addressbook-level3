package seedu.address.testutil;

import seedu.address.model.ExpenseList;
import seedu.address.model.expense.Person;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code ExpenseList ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private ExpenseList expenseList;

    public AddressBookBuilder() {
        expenseList = new ExpenseList();
    }

    public AddressBookBuilder(ExpenseList expenseList) {
        this.expenseList = expenseList;
    }

    /**
     * Adds a new {@code Person} to the {@code ExpenseList} that we are building.
     */
    public AddressBookBuilder withPerson(Person person) {
        expenseList.addPerson(person);
        return this;
    }

    public ExpenseList build() {
        return expenseList;
    }
}
