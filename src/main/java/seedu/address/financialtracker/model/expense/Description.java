package seedu.address.financialtracker.model.expense;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.AppUtil;

/**
 * Description of an expense.
 */
public class Description {

    public static final String MESSAGE_CONSTRAINTS =
            "Description can take any values, and it should not be blank\n"
            + "Maximum characters are 100 characters.";

    /*
     * The first character of the description must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";
    public final String value;

    public Description(String desc) {
        requireNonNull(desc);
        AppUtil.checkArgument(isValidDescription(desc), MESSAGE_CONSTRAINTS);
        this.value = desc;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(VALIDATION_REGEX) && (test.length() <= 100);
    }


    @Override
    public String toString() {
        return value;
    }
}
