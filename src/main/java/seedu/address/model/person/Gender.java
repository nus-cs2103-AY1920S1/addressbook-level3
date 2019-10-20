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
    private static final Set<String> VALID_GENDER = initializeValidGender();
    private static final String MESSAGE_CONSTRAINTS = initializeMessageConstraints();
    public final String gender;

    public Gender(String gender) {
        requireNonNull(gender);
        this.gender = gender;
    }

    /**
     * Initializes valid gender types.
     *
     * @return a set of valid gender types.
     */
    private static Set<String> initializeValidGender() {
        HashSet<String> result = new HashSet<>();
        result.add(MALE);
        result.add(FEMALE);
        return result;
    }

    /**
     * Initialises message constraints based on valid gender types.
     *
     * @return String.
     */
    private static String initializeMessageConstraints() {
        String message = "Only the following genders are allowed: ";
        StringJoiner result = new StringJoiner(", ");
        result.add(message);
        VALID_GENDER.forEach(gender -> result.add(gender));
        return result.toString();
    }

    public static boolean isValidGender(String test) {
        return VALID_GENDER.contains(test);
    }

    public static Set<String> getValidGender() {
        return VALID_GENDER;
    }

    public static String getMessageConstraints() {
        return MESSAGE_CONSTRAINTS;
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
