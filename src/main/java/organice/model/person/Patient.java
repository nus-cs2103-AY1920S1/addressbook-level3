package organice.model.person;

import static organice.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Patient in ORGANice.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Patient extends Person {
    //fields
    private final Age age;
    private final Priority priority;

    /**
     * Every field must be present and not null.
     */
    public Patient(Type type, Nric nric, Name name, Phone phone, Age age, Priority priority) {
        super(type, nric, name, phone);
        requireAllNonNull(age, priority);
        this.age = age;
        this.priority = priority;
    }

    public Age getAge() {
        return age;
    }

    public Priority getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(super.toString())
                .append(" Age: ")
                .append(getAge())
                .append(" Priority: ")
                .append(getPriority());

        return builder.toString();
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

        if (!(other instanceof Patient)) {
            return false;
        }

        Patient otherPerson = (Patient) other;
        return otherPerson.getType().equals(getType())
                && otherPerson.getNric().equals(getNric())
                && otherPerson.getName().equals(getName())
                && otherPerson.getPhone().equals(getPhone())
                && otherPerson.getAge().equals(getAge())
                && otherPerson.getPriority().equals(getPriority());
    }

}
