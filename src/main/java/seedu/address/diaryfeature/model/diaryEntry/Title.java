package seedu.address.diaryfeature.model.diaryEntry;

/**
 * Represents the title in a Diary Entry
 */
public class Title {

    public static final String MESSAGE_CONSTRAINTS = "" +
            "Title can't be an empty string, can't only be spaces and has to be less than 50 characters ";
    public static final int TITLE_MAX_LENGTH = 50;

    public final String value;

    /**
     * Constructs an {@code Title}.
     *
     * @param input is a valid title.
     */
    public Title(String input) {
        value = input;

    }

    /**
     * To defend against any malfunctions
     * @return a copy of this title
     */
    public Title copy () {
        return new Title(value);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Title // instanceof handles nulls
                && value.equalsIgnoreCase(((Title) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
