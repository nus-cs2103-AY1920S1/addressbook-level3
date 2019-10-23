package seedu.address.model.cap.person;

import static java.util.Objects.requireNonNull;

public class SemesterPeriod {

    private final int semesterPeriod;
    /**
     * Constructs a {@code Phone}.
     *
     * @param semesterPeriod A valid phone number.
     */
    public SemesterPeriod(int semesterPeriod) {
        requireNonNull(semesterPeriod);
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
}
