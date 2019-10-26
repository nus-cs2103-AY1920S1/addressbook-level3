package seedu.address.diaryfeature.model;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.address.diaryfeature.model.exceptions.TitleException;

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
    public Title(String input) throws TitleException {
        requireNonNull(input);
        String title = input.trim();

        //If the length of the remaining string
        //(after the trim in parser) is 0, which means an empty string,
        //then nothing is left and
        //this is a faulty title
        if(title.equalsIgnoreCase("")) {
            Logger.getLogger("Title is empty");
            throw new TitleException();
        }
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
