package seedu.address.model.entity;

/**
 * Represents a Prefix type to indicate the type of Entity.
 * Guarantees: Prefix type values are validated according to enum type, immutable.
 */
public enum PrefixType {
    P, // Participant
    M, // Mentor
    T, // Team
    I; // Issue

    // Constants
    public static final String MESSAGE_CONSTRAINTS =
            "Prefix type should be a string of either one of the following values:\n"
            + "P: to indicate Entity is a Participant\n"
            + "M: to indicate Entity is a Mentor\n"
            + "I: to indicate Entity is an Issue\n"
            + "T: to indicate Entity is  a Team\n";


    /**
     * Returns string representation of object, for storage.
     *
     * @return Email address in string format, for storage.
     */
    public String toStorageValue() {
        return this.name();
    }

    /**
     * Returns if a given string is a valid PrefixType.
     *
     * @param test String of prefix.
     * @return boolean whether test is in valid prefix format.
     */
    public static boolean isValidPrefixType(String test) {
        try {
            PrefixType result = PrefixType.valueOf(test);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
