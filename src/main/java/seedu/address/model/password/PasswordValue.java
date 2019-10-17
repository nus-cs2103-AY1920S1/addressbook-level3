package seedu.address.model.password;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Password's password value in the password book.
 */
public class PasswordValue {
    public static final String MESSAGE_CONSTRAINTS = "PasswordValue can take any values, and it should not be blank";

    private static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    public PasswordValue(String passwordValue) {
        requireNonNull(passwordValue);
        checkArgument(isValidPasswordValue(passwordValue), MESSAGE_CONSTRAINTS);
        value = passwordValue;
    }

    /**
     * Returns true if a given string is a valid username.
     */
    public static boolean isValidPasswordValue(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public String getEncryptedPasswordValue() {
        return changeToAsterix(value);
    }

    public String getNonEncryptedPasswordValue() {
        return value;
    }

    public String changeToAsterix(String password) {
        int len = password.length();
        if (len <= 3) {
            return asterix(len);
        } else {
            return password.substring(0,2) + asterix(len-2);
        }
    }

    public String asterix(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append("*");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return getEncryptedPasswordValue();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PasswordValue // instanceof handles nulls
                && value.equals(((PasswordValue) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
