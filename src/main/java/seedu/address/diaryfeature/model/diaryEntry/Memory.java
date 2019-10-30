package seedu.address.diaryfeature.model.diaryEntry;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.address.diaryfeature.model.exceptions.TitleException;

public class Memory {


    public static final String MESSAGE_CONSTRAINTS = "Memory has to be a short message, of less than 100 chars";


    public final String value;

    /**
     * Constructs an {@code Address}.
     *
     * @param input is a valid title.
     */
    public Memory(String input){
        requireNonNull(input);
        String title = input.trim();

        //If the length of the remaining string
        //(after the trim in parser) is 0, which means an empty string,
        //then nothing is left and
        //this is a faulty memory
        if (title.equalsIgnoreCase("")) {
            Logger.getLogger("Memory is empty");
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
                || (other instanceof Memory // instanceof handles nulls
                && value.equals(((Title) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
