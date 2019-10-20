package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

/**
 * Represents a Person's gender in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidGender(String)}
 */
public class Gender {

    public static final String DATA_TYPE = "Gender";
    public static final String MALE = "Male";
    public static final String FEMALE = "Female";
    public static final String UNKNOWN = "Do not wish to disclose";
    public static String MESSAGE_CONSTRAINTS = "Gender can only be one of the following: ";
    private static Set<String> VALID_GENDER = new HashSet<>();

    /**
     * Initialises age group hash set
     * @return age group hash set
     */
    static {
        VALID_GENDER.add(MALE);
        VALID_GENDER.add(FEMALE);
        VALID_GENDER.add(UNKNOWN);

        StringJoiner validGender = new StringJoiner(", ");
        VALID_GENDER.forEach(gender -> validGender.add(gender));
        MESSAGE_CONSTRAINTS += validGender.toString();
    }


    public final String gender;

    public Gender(String gender) {
        requireNonNull(gender);
        this.gender = gender;
    }

    public static boolean isValidGender(String test) {
        return VALID_GENDER.contains(test);
    }

    public static Set<String> getValidGender() {
        return VALID_GENDER;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Gender // instanceof handles nulls
            && gender.equals(((Gender) other).gender)); // state check
    }

    @Override
    public String toString() {
        return gender;
    }

    @Override
    public int hashCode() {
        return gender.hashCode();
    }

}
