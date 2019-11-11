package seedu.tarence.model.person;

import static seedu.tarence.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Person in the application.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    protected final Name name;
    protected final Email email;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Email email) {
        requireAllNonNull(name, email);
        this.name = name;
        this.email = email;
    }

    public Name getName() {
        return name;
    }

    public Email getEmail() {
        return email;
    }

    /**
     * Returns true if both persons have the same name and same email
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName())
                && otherPerson.getEmail().equals(getEmail());
    }

    /**
     * Returns true if both persons have the same identity or data fields.
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
                && otherPerson.getEmail().equals(getEmail());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, email);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Email: ")
                .append(getEmail());
        return builder.toString();
    }

}
