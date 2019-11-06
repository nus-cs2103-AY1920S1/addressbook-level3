package seedu.address.model.cap.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Academic year of a NUS student.
 * Guarantees: immutable.
 */
public class AcademicYear {

    public static final String MESSAGE_CONSTRAINTS =
            "Academic year should only contain appropriate years with the format Y1Y2 e.g. 1920 reflects"
                    + "years 2019 and 2020. Moreover, only +5 and -5 years from the current year is allowed";
    private final String academicYear;

    /**
     * Constructs a {@code Phone}.
     *
     * @param academicYear A valid phone number.
     */
    public AcademicYear(String academicYear) {
        requireNonNull(academicYear);
        checkArgument(isValidAcademicYear(academicYear), MESSAGE_CONSTRAINTS);
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

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidAcademicYear(String academicYear) {
        switch (academicYear.toLowerCase()) {
        case "1415":
        case "1516":
        case "1617":
        case "1718":
        case "1819":
        case "1920":
        case "2021":
        case "2122":
        case "2223":
        case "2324":
        case "2425":
            return true;
        default:
            return false;
        }
    }
}
