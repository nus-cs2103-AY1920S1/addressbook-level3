package seedu.address.model.cap.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * It encapsulates the Semester period, either semester 1 or 2, of a typical school terms or 3 and 4 for special terms.
 */
public class SemesterPeriod {

    public static final String MESSAGE_CONSTRAINTS =
            "Semester period can only be either 1, 2, 3 or 4. "
                + "Periods 3 and 4 indicates special terms 1 and 2 respectively.";

    private final int semesterPeriod;
    /**
     * Constructs a {@code SemesterPeriod}.
     *
     * @param semesterPeriod A valid semester period.
     */
    public SemesterPeriod(int semesterPeriod) {
        requireNonNull(semesterPeriod);
        checkArgument(isValidSemesterPeriod(semesterPeriod), MESSAGE_CONSTRAINTS);
        this.semesterPeriod = semesterPeriod;
    }

    public int getSemesterPeriod() {
        return semesterPeriod;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SemesterPeriod // instanceof handles nulls
                && this.semesterPeriod == semesterPeriod); // state check
    }

    @Override
    public String toString() {
        return String.valueOf(semesterPeriod);
    }

    /**
     * Returns true if a given string is a valid semester period.
     */
    public static boolean isValidSemesterPeriod(int test) {
        try {
            switch (test) {
            case 1:
            case 2:
            case 3: //special term 1
            case 4: //special term 2
                return true;
            default:
                return false;
            }
        } catch (StringIndexOutOfBoundsException e) {
            return false;
        }
    }
}
