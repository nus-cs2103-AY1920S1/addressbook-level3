package seedu.address.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Module's NUS code.
 * Guarantees: immutable; is valid as declared in {@link #isValidCode(String)}
 */
public class ModuleCode {

    public static final String MESSAGE_CONSTRAINTS = "Each module of study has a unique module code"
            + " consisting of a two- or three-letter prefix that denotes the discipline, and four digits,"
            + " the first of which indicates the level of the module (e.g., 1000 indicates a Level 1 module"
            + " and 2000, a Level 2 module).\n";

    public static final String VALIDATION_REGEX = "^[a-zA-z]{2,3}(\\d{4})";

    public final String value;

    /**
     * Constructs a {@code ModuleCode}.
     *
     * @param code a valid module code.
     */
    public ModuleCode(String code) {
        requireNonNull(code);
        checkArgument(isValidCode(code), MESSAGE_CONSTRAINTS);
        value = code;
    }

    /**
     * Returns true if a given string is a valid module code.
     */
    public static boolean isValidCode(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModuleCode // instanceof handles nulls
                && value.equals(((ModuleCode) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
