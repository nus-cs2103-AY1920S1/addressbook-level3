package seedu.tarence.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.tarence.commons.util.AppUtil.checkArgument;

/**
 * Represents a Module Code.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class ModCode {

    public static final String MESSAGE_CONSTRAINTS =
            "Module code consists of a two- or three-letter prefix that denotes the discipline, and four digits."
            + " It may end with a single letter."
            + " It should not be blank";

    /*
     * The first character of the module code must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^[a-zA-Z]{2,3}[0-9]{4}[a-zA-Z]?$";

    public final String modCode;

    /**
     * Every field must be present and not null.
     */
    public ModCode(String modCode) {
        requireNonNull(modCode);
        checkArgument(isValidModCode(modCode), MESSAGE_CONSTRAINTS);
        this.modCode = modCode;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidModCode(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return modCode;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModCode // instanceof handles nulls
                && modCode.equalsIgnoreCase(((ModCode) other).modCode)); // state check
    }

    @Override
    public int hashCode() {
        return modCode.hashCode();
    }

}
