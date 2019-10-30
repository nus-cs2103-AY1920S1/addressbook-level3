package seedu.system.model.person;

import static seedu.system.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.system.model.UniqueElement;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person extends UniqueElement {

    // Identity fields
    private final Name name;
    private final CustomDate dateOfBirth;
    private final Gender gender;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, CustomDate dateOfBirth, Gender gender) {
        requireAllNonNull(name, dateOfBirth, gender);
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    public Name getName() {
        return name;
    }

    public CustomDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameElement(UniqueElement otherElement) {
        if (!(otherElement instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) otherElement;

        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
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
        return otherPerson.getName().equals(getName());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, dateOfBirth, gender);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Date of Birth: ")
                .append(getDateOfBirth())
                .append(" Gender: ")
                .append(getGender());
        return builder.toString();
    }

}
