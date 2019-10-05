package seedu.address.model.entity;

/**
 * Enumerates sex.
 */
public enum Sex {
    MALE, FEMALE;

    public static final String MESSAGE_CONSTRAINTS =
        "Sex should either be \"male\" or \"female\".";

    /**
     * Checks if {@code String sex} is female.
     */
    public static boolean isMale(String sex) {
        return sex.equalsIgnoreCase("male");
    }

    /**
     * Checks if {@code String sex} is male.
     */
    public static boolean isFemale(String sex) {
        return sex.equalsIgnoreCase("female");
    }

    /**
     * Checks if {@code String sex} is a valid sex.
     */
    public static boolean isValidSex(String sex) {
        if (sex.equalsIgnoreCase("Male") || sex.equalsIgnoreCase("female")) {
            return true;
        } else {
            return false;
        }
    }
}
