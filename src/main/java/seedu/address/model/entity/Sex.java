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
        return sex.equalsIgnoreCase("male") || sex.equalsIgnoreCase("m");
    }

    /**
     * Checks if {@code String sex} is male.
     */
    public static boolean isFemale(String sex) {
        return sex.equalsIgnoreCase("female") || sex.equalsIgnoreCase("f");
    }

    /**
     * Checks if {@code String sex} is a valid sex.
     */
    public static boolean isValidSex(String sex) {
        if (isMale(sex) || isFemale(sex)) {
            return true;
        } else {
            return false;
        }
    }
}
