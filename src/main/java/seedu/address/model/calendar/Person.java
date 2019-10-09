package seedu.address.model.calendar;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person in the calender entry.
 */
public class Person {
    public static final String MESSAGE_CONSTRAINTS = "Person can take any values, and it should not be blank";

    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Person}.
     *
     * @param person A valid person.
     */
    public Person(String person) {
        requireNonNull(person);
        checkArgument(isValidPerson(person), MESSAGE_CONSTRAINTS);
        value = person;
    }

    /**
     * Returns true if a given string is a valid person.
     */
    public static boolean isValidPerson(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Person
                && value.equals(((Person) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

