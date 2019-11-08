package seedu.address.testutil;

import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.transaction.Amount;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";

    private Name name;
    private Amount amount;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        amount = Amount.zero();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        amount = personToCopy.getBalance();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code balance} of the {@code Person} that we are building.
     */
    public PersonBuilder withAmount(String amount) {
        this.amount = new Amount(Double.parseDouble(amount));
        return this;
    }

    public Person build() {
        return new Person(name, amount);
    }

}
