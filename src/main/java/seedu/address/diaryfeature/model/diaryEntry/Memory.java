package seedu.address.diaryfeature.model.diaryEntry;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

public class Memory {


    public static final String MESSAGE_CONSTRAINTS = "" +
            "Memory, while optional, if input, " +
            "can't be the empty string, can't only be spaces ";

    public final String memory;

    /**
     * Constructs an {@code Address}.
     *
     * @param input is a valid title.
     */
    public Memory(String input){
        memory = input;
    }


    @Override
    public String toString() {
        return memory;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Memory // instanceof handles nulls
                && memory.equals(((Memory) other).memory)); // state check
    }

    @Override
    public int hashCode() {
        return memory.hashCode();
    }

}
