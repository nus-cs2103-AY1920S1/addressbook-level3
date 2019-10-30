package seedu.system.model.person;

/**
 * Only 2 Genders can be used: male or female.
 */
public enum Gender {
    MALE("male"),
    FEMALE("female");

    public static final String MESSAGE_CONSTRAINTS =
            "Gender should be provided as either male or female";

    private String name;

    private Gender(String name) {
        this.name = name;
    }

    /**
     * Checks {@code gender} corresponds to a  format.
     */
    public static boolean isValidGender(String gender) {
        return gender.toLowerCase().equals(MALE.toString())
            || gender.toLowerCase().equals(FEMALE.toString());
    }

    public static Gender getGenderCorrespondingToName(String name) {
        if (name.equals((MALE.toString()))) {
            return MALE;
        } else if (name.equals((FEMALE.toString()))) {
            return FEMALE;
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return name;
    }

}
