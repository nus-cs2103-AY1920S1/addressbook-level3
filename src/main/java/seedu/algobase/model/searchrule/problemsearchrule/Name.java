package seedu.algobase.model.searchrule.problemsearchrule;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.commons.util.AppUtil.checkArgument;

/**
 * Represents a Problem's name in the algobase.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name implements Comparable<Name> {

    public static final String MESSAGE_CONSTRAINTS =
        "Names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * A name is any combination of alphanumeric characters or whitespace.
     * A name cannot be blank.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String name;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        this.name = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return !test.isBlank() && test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Name // instanceof handles nulls
            && name.equals(((Name) other).name)); // state check
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public int compareTo(Name o) {
        if (this == o) {
            return 0;
        }
        return this.name.compareTo(o.name);
    }
}
