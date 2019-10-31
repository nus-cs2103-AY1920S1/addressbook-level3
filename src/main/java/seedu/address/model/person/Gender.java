package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's gender in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidGender(String)}
 */

public class Gender {
    public static final String MESSAGE_CONSTRAINTS = "Gender can only be Male, Female or Others, and it should not be "
        + "blank";
    public final String genderOfPerson;
    /**
     * Constructs an {@code Gender}.
     *
     * @param gender A valid gender.
     */
    public Gender(String gender) {
        requireNonNull(gender);
        checkArgument(isValidGender(gender), MESSAGE_CONSTRAINTS);
        genderOfPerson = gender.substring(0, 1).toUpperCase() + gender.substring(1).toLowerCase();
    }
    /**
     * Returns if a given string is a valid email.
     */
    public static boolean isValidGender(String test) {
        return (test.toLowerCase().equals("male") || test.toLowerCase().equals("female") || test.toLowerCase().equals(
            "others"));
    }
    @Override
    public String toString() {
        return genderOfPerson;
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Gender // instanceof handles nulls
            && genderOfPerson.equals(((Gender) other).genderOfPerson)); // state check
    }
    @Override
    public int hashCode() {
        return genderOfPerson.hashCode();
    }
}
