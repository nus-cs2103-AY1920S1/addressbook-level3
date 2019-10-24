package seedu.address.diaryfeature.model.diaryEntry;

import static java.util.Objects.requireNonNull;

/**
 * Represents the title in a Diary Entry
 */
public class Title {

    public static final String MESSAGE_CONSTRAINTS = "Title can take any value";

    public final String value;

    /**
     * Constructs an {@code Address}.
     *
     * @param title is a valid title.
     */
    public Title(String title) {
        requireNonNull(title);
        value = title;
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Title // instanceof handles nulls
                && value.equals(((Title) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
