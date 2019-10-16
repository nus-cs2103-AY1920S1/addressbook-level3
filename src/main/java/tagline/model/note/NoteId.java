package tagline.model.note;

import static java.util.Objects.requireNonNull;
import static tagline.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidNoteId(String)}
 */
public class NoteId {


    public static final String MESSAGE_CONSTRAINTS = "NoteId numbers should only contain numbers";

    // from https://stackoverflow.com/questions/15111420/how-to-check-if-a-string-contains-only-digits-in-java
    public static final String VALIDATION_REGEX = "\\d+";

    public final Long value;
    //public final String value;

    /**
     * Constructs a {@code NoteId}.
     *
     * @param idNumber A valid phone number.
     */
    public NoteId(long idNumber) {
        requireNonNull(idNumber);
        //checkArgument(isValidNoteId(idNumber), MESSAGE_CONSTRAINTS);
        value = Long.valueOf(idNumber);
    }

    // this constructor supports storage
    // is this a point of failure? should i be using a factory method?
    public NoteId(String idNumber) {
        requireNonNull(idNumber);
        checkArgument(isValidNoteId(idNumber), MESSAGE_CONSTRAINTS);
        value = Long.valueOf(idNumber);
    }

    public NoteId() {
        value = NoteIdCounter.incrementThenGetValue();
    }

    // This part is to convert the Long to String for Storage
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
        String formattedNoteId = String.format("%05d", value);
        return formattedNoteId; //String.valueOf(value);
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
