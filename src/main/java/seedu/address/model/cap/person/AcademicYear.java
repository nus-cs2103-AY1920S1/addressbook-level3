package seedu.address.model.cap.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents an Academic year of a NUS student.
 * Guarantees: immutable.
 */
public class AcademicYear {

    private final String academicYear;
    /**
     * Constructs a {@code Phone}.
     *
     * @param academicYear A valid phone number.
     */
    public AcademicYear(String academicYear) {
        requireNonNull(academicYear);
        this.academicYear = academicYear;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AcademicYear // instanceof handles nulls
                && this.academicYear == academicYear); // state check
    }

    @Override
    public String toString() {
        return academicYear;
    }
}
