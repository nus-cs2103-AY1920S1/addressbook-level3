package seedu.address.model.body;

//@@author ambervoong
/**
 * Represents a National Registration Identity Card number in Mortago.
 * Guarantees: immutable; is valid as declared in {@link #isValidNric(String)}
 */
public class Nric {

    public static final String VALID_NRIC = ""; // To be updated with error message.

    public final String nric;

    public Nric(String nric) {
        this.nric = nric;
    }

    public String getNric() {
        return nric;
    }

    /**
     * Checks if a given string is a valid NRIC number.
     * @param nric
     * @return
     */
    public static boolean isValidNric(String nric) {
        return false;
    }
}
