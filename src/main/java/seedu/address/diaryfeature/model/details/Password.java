package seedu.address.diaryfeature.model.details;

public class Password {
    public static final String MESSAGE_CONSTRAINTS = "" +
            "Password has to be at least 8 characters, is case insensitive and has to be alphanumeric ";
    public static final int PASSWORD_MIN_LENGTH = 8;
    private final String password;

    public Password(String input) {
        password = input;
    }

    public String getPassword() {
        return password;
    }

    public boolean equalsSpecial(Password input) {
        return password.equalsIgnoreCase(input.getPassword());
    }
    public String toString() {
        return password;
    }

    @Override
    public int hashCode() {
        return password.hashCode();
    }
}
