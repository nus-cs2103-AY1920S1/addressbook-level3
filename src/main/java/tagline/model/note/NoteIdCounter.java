package tagline.model.note;

import static tagline.commons.util.AppUtil.checkArgument;

/**
 * Represents a Note's content in the note book.
 * Guarantees: a Static Class,
 */
public class NoteIdCounter {

    public static final String MESSAGE_CONSTRAINTS = "NoteIdCounter can take any number, and it should not be blank";

    // from https://stackoverflow.com/questions/15111420/how-to-check-if-a-string-contains-only-digits-in-java
    public static final String VALIDATION_REGEX = "\\d+";

    private static long counter;

    private NoteIdCounter() {
    }

    // for testing purposes
    public static void setCount(long newCount) {
        counter = newCount; //Long.valueOf(0);
    }

    // for storage purposes
    public static void setCountFromStorage(String newCount) {
        checkArgument(isValidNoteIdCount(newCount), MESSAGE_CONSTRAINTS);
        counter = Long.valueOf(newCount); //Long.valueOf(0);
    }

    public static String getStorageString() {
        return String.valueOf(counter);
    }

    public static void setZero() {
        counter = 0; //Long.valueOf(0);
    }

    // increment then getValue

    /**
     * Increment the current counter value in NoteIdCounter and returns the new value, this is due to
     * original value starting at 0, added advantage of this counter being of how many notes has
     * been created since calling this.
     * @return long
     */
    public static long incrementThenGetValue() {
        ++counter;
        return counter;
    }

    public static Long getCount() {
        return Long.valueOf(counter);
    }

    /**
     * Returns true if a given string is a valid noteID number count.
     */
    public static boolean isValidNoteIdCount(String test) {
        return test.matches(VALIDATION_REGEX);

    }
}
