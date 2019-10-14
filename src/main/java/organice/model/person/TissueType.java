package organice.model.person;

import static java.util.Objects.requireNonNull;
import static organice.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's tissue type in ORGANice.
 * Guarantees: immutable; is valid as declared in {@link #isValidTissueType(String)}
 */
public class TissueType {

    public static final String MESSAGE_CONSTRAINTS =
            "Tissue types are 6 integers from 1 to 12 separated by commas"
                + " An example will be tt/1,2,3,4,5,6";
    public final String value;

    /**
     * Constructs a {@code TissueType}.
     *
     * @param tissueType A valid tissue type
     */
    public TissueType(String tissueType) {
        requireNonNull(tissueType);
        checkArgument(isValidTissueType(tissueType), MESSAGE_CONSTRAINTS);
        value = tissueType;
    }

    /**
     * Returns true if a given string is a valid tissue type.
     */
    public static boolean isValidTissueType(String test) {
        String[] tissueTypeValue = test.split(",");
        if (tissueTypeValue.length != 6 || hasDuplicates(tissueTypeValue)) {
            return false;
        }
        for (int i = 0; i < tissueTypeValue.length; i++) {
            try {
                Integer tt = Integer.parseInt(tissueTypeValue[i]);
                if (tt < 1 || tt > 12) {
                    return false;
                }
            } catch (NumberFormatException | NullPointerException nfe) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns true if a given string array contains duplicate.
     */
    public static boolean hasDuplicates(String[] test) {
        for (int i = 0; i < test.length; i++) {
            for (int j = i + 1; j < test.length; j++) {
                if (test[i].equals(test[j])) {
                    return true;
                }
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
                || (other instanceof TissueType // instanceof handles nulls
                && value.equals(((TissueType) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
