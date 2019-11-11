package tagline.model.note;

import static java.util.Objects.requireNonNull;
import static tagline.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidNoteId(String)}
 */
public class NoteId {


    public static final String MESSAGE_CONSTRAINTS = "NoteId numbers should only contain positive numbers";

    // from https://stackoverflow.com/questions/15111420/how-to-check-if-a-string-contains-only-digits-in-java
    public static final String VALIDATION_REGEX = "\\d+";

    public final Long value;

    /**
     * Constructs a {@code NoteId}.
     *
     * @param idNumber A valid phone number.
     */
    public NoteId(long idNumber) {
        requireNonNull(idNumber);
        value = Long.valueOf(idNumber);
    }

    public NoteId(String idNumber) {
        requireNonNull(idNumber);

        checkArgument(isValidNoteId(idNumber), MESSAGE_CONSTRAINTS);
        value = Long.valueOf(idNumber);
    }

    public NoteId() {
        value = NoteIdCounter.incrementThenGetValue();
    }

    public Long toLong() {
        return value;
    }

    /**
     * @return String format of note id for storage.
     */
    public String getStorageString() {
        return value.toString();
    }

    /**
     * Returns true if a given string is a valid noteID number.
     */
    public static boolean isValidNoteId(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return String.format("%05d", value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NoteId // instanceof handles nulls
                && value.equals(((NoteId) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
