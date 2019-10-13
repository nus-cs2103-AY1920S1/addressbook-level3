package seedu.address.model.help;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents a Help Object's type of help in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidType(String)}
 */

public class Type {

    public static final String MESSAGE_CONSTRAINTS = "There are 3 available types of help: brief, api and "
            + "guide\n Please select one of them";

    private static ArrayList<String> typeList = new ArrayList<String>(
            Arrays.asList("guide",
                    "brief",
                    "api"));

    public final String value;

    /**
     * Constructs an {@code Type}.
     *
     * @param type A valid type of help.
     */

    public Type(String type) {
        requireNonNull(type);
        checkArgument(isValidType(type), MESSAGE_CONSTRAINTS);
        value = type;
    }

    /**
     * Returns if a given string is a valid degree of help.
     */
    public static boolean isValidType(String test) {

        for (String deg : typeList) {
            if (test.equals(deg)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Type // instanceof handles nulls
                && value.equals(((Type) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
