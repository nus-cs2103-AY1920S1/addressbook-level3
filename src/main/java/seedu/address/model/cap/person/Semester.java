package seedu.address.model.cap.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Semester and the academic year.
 */
public class Semester {

    private final int semester;
    private final String academicYear;
    /**
     * Constructs a {@code Phone}.
     *
     * @param semester A valid phone number.
     */
    public Semester(int semester, String academicYear) {
        requireNonNull(semester);
        requireNonNull(academicYear);
        this.semester = semester;
        this.academicYear = academicYear;
    }

    public int getSemester() {
        return semester;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Semester // instanceof handles nulls
                && semester == semester
                && academicYear.equals(((Semester) other).academicYear)); // state check
    }

    @Override
    public String toString() {
        return Integer.toString(semester) + " " + academicYear;
    }
}
