package seedu.mark.model.reminder;

import static java.util.Objects.requireNonNull;
import static seedu.mark.commons.util.AppUtil.checkArgument;

/**
 * Represents a note in reminder.
 */
public class Note {

    public static final String INVALID_CHARACTER = "/";

    public static final String MESSAGE_CONSTRAINTS = "Notes can contain any characters except " + INVALID_CHARACTER;

    /*
     * The first character of the note must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     *
     * The rest of the note can contain any character except the invalid character.
     */
    public static final String VALIDATION_REGEX = "[^\\s][^" + INVALID_CHARACTER + "]*";

    public static final String DEFAULT_VALUE = "Open";
    private String noteContent;

    public Note(String noteContent) {
        requireNonNull(noteContent);
        checkArgument(isValidNote(noteContent), MESSAGE_CONSTRAINTS);
        this.noteContent = noteContent;
    }

    /**
     * Returns true if a given string is a valid note.
     */
    public static boolean isValidNote(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is an empty note.
     */
    public static boolean isEmptyNote(String test) {
        return test.trim().equals("");
    }

    @Override
    public String toString() {
        return noteContent;
    }

    /**
     * Returns a {@code Note} with the default value.
     */
    public static Note getDefaultNote() {
        return new Note(DEFAULT_VALUE);
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
