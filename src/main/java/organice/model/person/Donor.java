package organice.model.person;

import static organice.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Donor in ORGANice.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Donor extends Person {
    //fields
    private final Age age;

    /**
     * Every field must be present and not null.
     */
    public Donor(Type type, Nric nric, Name name, Phone phone, Age age) {
        super(type, nric, name, phone);
        requireAllNonNull(age);
        this.age = age;
    }

    public Age getAge() {
        return age;
    }

    /**
     * Returns true if both donors have the same identity and data fields.
     * This defines a stronger notion of equality between two donors.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Donor)) {
            return false;
        }

        Donor otherPerson = (Donor) other;
        return otherPerson.getNric().equals(getNric())
            && otherPerson.getName().equals(getName())
            && otherPerson.getPhone().equals(getPhone())
            && otherPerson.getType().equals(getType())
            && otherPerson.getAge().equals(getAge());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(type, nric, name, phone, age);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(super.toString())
            .append(" Age: ")
            .append(getAge());

        return builder.toString();
    }

}
