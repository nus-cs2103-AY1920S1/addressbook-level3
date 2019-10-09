package seedu.mark.model.reminder;

import static java.util.Objects.requireNonNull;
import static seedu.mark.commons.util.AppUtil.checkArgument;

/**
 * Represent a note in reminder.
 */
public class Note {

    public static final String MESSAGE_CONSTRAINTS =
            "Notess should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the note must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    
    private String noteContent;

    public Note(String noteContent) {
        requireNonNull(noteContent);
        checkArgument(isValidName(noteContent), MESSAGE_CONSTRAINTS);
        this.noteContent = noteContent;
    }

    /**
     * Returns true if a given string is a valid note.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return noteContent;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Note // instanceof handles nulls
                && noteContent.equals(((Note) other).noteContent)); // state check
    }

    @Override
    public int hashCode() {
        return noteContent.hashCode();
    }

}
