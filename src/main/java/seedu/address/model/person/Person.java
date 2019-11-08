package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Split;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;

    // Data fields
    private Amount balance;

    /**
     * All parameters must be non-null.
     */
    public Person(Name name, Amount amount) {
        requireAllNonNull(name, amount);
        this.name = name;
        this.balance = amount;
    }

    public Person(Name name) {
        this(name, Amount.zero());
    }

    public Name getName() {
        return name;
    }

    public Amount getBalance() {
        return balance;
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
            && otherPerson.getName().equals(getName());
    }

    /**
     * @param amount amount of money spent in {@link Split}
     */
    public void spend(Amount amount) {
        balance = balance.subtractAmount(amount);
    }

    public void receive(Amount amount) {
        balance = balance.addAmount(amount);
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

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return otherPerson.getName().equals(getName())
            && balance.equals(otherPerson.balance);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, balance);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
            .append(" Balance: ")
            .append(getBalance());
        return builder.toString();
    }

    public void resetBalance() {
        balance = Amount.zero();
    }
}
