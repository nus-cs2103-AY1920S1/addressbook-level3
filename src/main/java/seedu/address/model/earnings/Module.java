package seedu.address.model.earnings;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Tutor's Module.
 * Guarantees: immutable; is valid as declared in {@link #isValidModuleName(String)}
 */
public class Module {

    public static final String MESSAGE_CONSTRAINTS =
            "Modules should only contain alphanumeric characters, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String moduleName;

    /**
     * Constructs a {@code Module}.
     *
     * @param mod A valid module name.
     */
    public Module(String mod) {
        requireNonNull(mod);
        checkArgument(isValidModuleName(mod), MESSAGE_CONSTRAINTS);
        moduleName = mod;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidModuleName(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return moduleName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Module // instanceof handles nulls
                && moduleName.equals(((Module) other).moduleName)); // state check
    }

    @Override
    public int hashCode() {
        return moduleName.hashCode();
    }

}
