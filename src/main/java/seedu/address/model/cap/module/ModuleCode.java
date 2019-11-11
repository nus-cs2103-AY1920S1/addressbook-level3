package seedu.address.model.cap.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Module's name in modulo.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class ModuleCode {

    public static final String MESSAGE_CONSTRAINTS =
            "ModuleCode should only contain alphanumeric characters, and a set of 4 digit number."
                + "There should not be spaces and it should not be blank";

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
        checkArgument(isValidModuleCode(code), MESSAGE_CONSTRAINTS);
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
        boolean isValid = false;
        try {
            for(int i = 2, n = test.length(); i < n ; i++) {
                String toCheck = test.substring(i, i + 4);
                //check if there are more than 1 char after the 4 set of integers
                if (isStringInt(toCheck) && (test.length() - (i + 4)) > 1) {
                    isValid = false;
                    break;
                }
                //checks if the last char is an integer
                if (isStringInt(toCheck) && (test.length() - (i + 4)) == 1) {
                    isValid = !isStringInt(test.substring(i + 3, i + 5));
                    break;
                } else if (isStringInt(toCheck) && (test.length() - (i + 4)) == 0) {
                    isValid = true;
                    break;
                }
            }
        } catch (StringIndexOutOfBoundsException e) {
            return false;
        }

        return isValid;
    }

    private static boolean isStringInt(String s) {
        try
        {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
