package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.model.category.Category;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Split;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Optional<Phone> phone;
    private final Optional<Email> email;

    // Data fields
    private final Optional<Address> address;
    private final Set<Category> categories = new HashSet<>();

    private Amount balance;

    /**
     * Only name must be non-null
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Category> categories, Amount amount) {
        requireAllNonNull(name, phone, email, address, categories, amount);
        this.name = name;
        this.phone = Optional.of(phone);
        this.email = Optional.of(email);
        this.address = Optional.of(address);
        this.categories.addAll(categories);
        this.balance = amount;
    }

    public Person(Name name, Phone phone, Email email, Address address, Set<Category> categories) {
        this(name, phone, email, address, categories, Amount.zero());
    }

    public Person(Name name) {
        requireAllNonNull(name);
        this.name = name;
        this.phone = Optional.empty();
        this.email = Optional.empty();
        this.address = Optional.empty();
        this.balance = Amount.zero();
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone.orElse(new Phone("65166666"));
    }

    public Email getEmail() {
        return email.orElse(new Email("empty@emptyemail.com"));
    }

    public Address getAddress() {
        return address.orElse(new Address("empty"));
    }

    public Amount getBalance() {
        return balance;
    }

    /**
     * Returns an immutable category set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Category> getCategories() {
        return Collections.unmodifiableSet(categories);
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
            && otherPerson.getName().equals(getName())
            && (otherPerson.getPhone().equals(getPhone()) || otherPerson.getEmail().equals(getEmail()));
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
            && otherPerson.getPhone().equals(getPhone())
            && otherPerson.getEmail().equals(getEmail())
            && otherPerson.getAddress().equals(getAddress())
            && otherPerson.getCategories().equals(getCategories());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, categories);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
            .append(" Balance: ")
            .append(getBalance())
            .append(" Phone: ")
            .append(getPhone())
            .append(" Email: ")
            .append(getEmail())
            .append(" Address: ")
            .append(getAddress())
            .append(" Categories: ");
        getCategories().forEach(builder::append);
        return builder.toString();
    }

}
