package seedu.tarence.model.student;

import static java.util.Objects.requireNonNull;
import static seedu.tarence.commons.util.AppUtil.checkArgument;

/**
 * Represents an NUSNETID field of a Student.
 */
public class NusnetId {

    public static final String MESSAGE_CONSTRAINTS =
            "NUSNETID consists of a 'E' followed by 7 numbers.";

    /*
     * Matriculation number is an "E", followed by exactly 7 numbers.
     */
    public static final String VALIDATION_REGEX = "^[eE][0-9]{7}$";

    public final String nusnetId;

    /**
     * Constructs a {@code NusnetId}.
     *
     * @param nusnetId A matriculation number.
     */
    public NusnetId(String nusnetId) {
        requireNonNull(nusnetId);
        checkArgument(isValidNusnetId(nusnetId), MESSAGE_CONSTRAINTS);
        this.nusnetId = nusnetId;
    }

    /**
     * Returns true if a given string is a valid NUSNET ID.
     */
    public static boolean isValidNusnetId(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return nusnetId;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NusnetId // instanceof handles nulls
                && nusnetId.equalsIgnoreCase(((NusnetId) other).nusnetId)); // state check
    }

    @Override
    public int hashCode() {
        return nusnetId.hashCode();
    }
}
