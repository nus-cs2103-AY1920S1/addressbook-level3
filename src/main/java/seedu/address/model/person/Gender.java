package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's gender in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidGender(String)}
 */
public class Gender {

    public static final String DATA_TYPE = "Gender";
    public static final String MALE = "Male";
    public static final String FEMALE = "Female";
    public static final String UNKNOWN = "Do not wish to disclose";

    public static final String MESSAGE_CONSTRAINTS =
        "Gender can only be one of the following: " + MALE + ", " + FEMALE + ", " + UNKNOWN;

    public final String gender;

    public Gender(String gender) {
        requireNonNull(gender);
        this.gender = gender;
    }

    public static boolean isValidGender(String test) {
        return test.equals(MALE) || test.equals(FEMALE) || test.equals(UNKNOWN);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Gender // instanceof handles nulls
            && gender.equals(((Gender) other).gender)); // state check
    }

    @Override
    public String toString() { return gender; }

    @Override
    public int hashCode() {
        return gender.hashCode();
    }

}
