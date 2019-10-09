package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Patient in ORGANice.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Patient extends Person {
    //fields
    private final Age age;

    /**
     * Every field must be present and not null.
     */
    public Patient(Type type, Nric nric, Name name, Phone phone, Age age) {
        super(type, nric, name, phone);
        requireAllNonNull(age);
        this.age = age;
    }

    public Age getAge() {
        return age;
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
