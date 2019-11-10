package seedu.address.diaryfeature.model.details;

public class Username {
    public static final String MESSAGE_CONSTRAINTS = "" +
            "Username has to be at least 8 characters, is case insensitive and has to be alphanumeric ";
    public static final int USERNAME_MIN_LENGTH = 8;
    private final String userName;

    /**
     * Construct a valid username
     * @param input
     */
    public Username(String input) {
        userName = input;
    }


    /**
     * Check if the 2 usernames are equal, case insensitive
     * @param input
     * @return boolean
     */
    public boolean equalsSpecial(Username input) {
        return userName.equalsIgnoreCase(input.toString());
    }

    public String toString() {
        return userName;
    }

    @Override
    public int hashCode() {
        return userName.hashCode();
    }
}
