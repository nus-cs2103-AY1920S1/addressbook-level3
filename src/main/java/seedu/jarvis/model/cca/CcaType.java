package seedu.jarvis.model.cca;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.commons.util.AppUtil.checkArgument;

/**
 * Represents a Cca's type in Jarvis.
 * Guarantees: immutable; is valid as declared in {@link #isValidCcaType(String)}
 */
public class CcaType {

    public static final String MESSAGE_CONSTRAINTS =
            "Cca type should be either a sport, performing art, uniformed group or clubs and societies, "
                    + "and it should not be blank";

    /**
     * Each CCA type must correspond to 1 of the following enum types.
     */
    public enum CcaTypeEnum {
        sport,
        performingArt,
        uniformedGroup,
        club
    }

    public final String ccaType;

    /**
     * Constructs a {@code CcaType}.
     *
     * @param ccaType A valid name.
     */
    public CcaType(String ccaType) {
        requireNonNull(ccaType);
        checkArgument(isValidCcaType(ccaType), MESSAGE_CONSTRAINTS);
        this.ccaType = ccaType;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidCcaType(String test) {
        requireNonNull(test);
        for (CcaTypeEnum c : CcaTypeEnum.values()) {
            if (c.name().equals(test)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public String toString() {
        return ccaType;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CcaType // instanceof handles nulls
                && ccaType.equals(((CcaType) other).ccaType)); // state check
    }

    @Override
    public int hashCode() {
        return ccaType.hashCode();
    }

}
