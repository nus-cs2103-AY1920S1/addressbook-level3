package seedu.address.model.note;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * To get the module code of the module
 */
public class ModuleCode {
    public static final String MESSAGE_CONSTRAINTS =
            "Modules Codes should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String moduleCode;

    /**
     * Constructs a {@code ModuleCode}.
     *
     * @param module A valid module code.
     */
    public ModuleCode(String module) {
        requireNonNull(module);
        checkArgument(isValidModuleCode(module), MESSAGE_CONSTRAINTS);
        this.moduleCode = module;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidModuleCode(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return moduleCode;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModuleCode // instanceof handles nulls
                && moduleCode.equals(((ModuleCode) other).moduleCode)); // state check
    }

    @Override
    public int hashCode() {
        return moduleCode.hashCode();
    }
}
