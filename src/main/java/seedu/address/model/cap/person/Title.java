package seedu.address.model.cap.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Represents a Person's address in the address book.
 * Guarantees: immutable.
 */
public class Title {

    public static final String MESSAGE_CONSTRAINTS =
            "Faculty should only contain characters, there "
                    + "should not be special characters and it should not be blank";

    public final String title;

    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    /**
     * Constructs an {@code Address}.
     *
     * @param title A valid address.
     */
    public Title(String title) {
        requireNonNull(title);
        checkArgument(isValidName(title), MESSAGE_CONSTRAINTS);
        this.title = title;
    }

    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Title // instanceof handles nulls
                && title.equals(((Title) other).title)); // state check
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidTitle(String test) {
        try {
            requireNonNull(test);
            checkArgument(isValidName(test));
            Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(test);
            Boolean noSpecialCharacter = !m.find();

            Boolean noNumber = !isNumeric(test.substring(0,1));
            return noNumber && noSpecialCharacter;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static boolean isNumeric(String strNum) {
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }

}
