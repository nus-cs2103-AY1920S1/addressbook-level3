package seedu.address.model.cap.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Module's name in modulo.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class ModuleCode {

    public static final String MESSAGE_CONSTRAINTS =
            "ModuleCode should only contain alphanumeric characters, there "
                + "should not be spaces and it should not be blank";

    /*
     * The first character of the module code must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String moduleCode;

    /**
     * Constructs a {@code ModuleCode}.
     *
     * @param code A valid module code.
     */
    public ModuleCode(String code) {
        requireNonNull(code);
        checkArgument(isValidName(code), MESSAGE_CONSTRAINTS);
        moduleCode = code.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid module code.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return moduleCode;
    }

    public String getModuleCode() {
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

    /**
     * Returns true if a given string is a valid module code for the module.
     */
    public static boolean isValidModuleCode(String test) {
        try {
            String code = test.substring(0, 2).toLowerCase();
            switch (code) {
            case "cs":
            case "ec":
            case "acc":
            case "fn":
            case "fi":
            case "ar":
            case "ie":
            case "ges":
            case "ger":
            case "geh":
            case "geq":
            case "get":
            case "cfg":
            case "asp":
            case "as":
                return true;
            default:
                return false;
            }
        } catch (StringIndexOutOfBoundsException e) {
            return false;
        }
    }
}
