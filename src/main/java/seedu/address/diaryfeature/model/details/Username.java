package seedu.address.diaryfeature.model.details;

public class Username {
    public static final String MESSAGE_CONSTRAINTS = "" +
            "Username has to be at least 8 characters, is case insensitive and has to be alphanumeric ";
    public static final int USERNAME_MIN_LENGTH = 8;
    private final String userName;

    public Username(String input) {
        userName = input;
    }

    public String getUserName() {
        return userName;
    }

    public boolean equalsSpecial(Username input) {
        return userName.equalsIgnoreCase(input.getUserName());
    }

    public String toString() {
        return userName;
    }

    @Override
    public int hashCode() {
        return userName.hashCode();
    }
}
