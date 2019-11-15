package seedu.address.financialtracker.model.expense;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.AppUtil;

/**
 * Type of expenditure of an expense.
 */
public class Type {

    public static final String MESSAGE_CONSTRAINTS =
            "Type can take any values, and it should not be blank\n"
            + "Maximum number of characters is 100.";

    /*
     * The first character of the type must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";
    public final String value;

    public Type(String type) {
        requireNonNull(type);
        AppUtil.checkArgument(isValidType(type), MESSAGE_CONSTRAINTS);
        this.value = type.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidType(String test) {
        return test.matches(VALIDATION_REGEX) && (test.length() <= 100);
    }


    @Override
    public String toString() {
        return value;
    }
}
