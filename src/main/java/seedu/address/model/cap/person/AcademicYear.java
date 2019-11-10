package seedu.address.model.cap.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
     * Constructs an {@code AcademicYear}.
     *
     * @param academicYear A valid academic year.
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
     * Returns true if a given string is a valid Academic Year i.e. +5 or -5 years of current year.
     */
    public static boolean isValidAcademicYear(String academicYear) {
        try {
            DateFormat df = new SimpleDateFormat("yy");
            int formattedDate = Integer.parseInt(df.format(Calendar.getInstance().getTime()));

            int firstYear = Integer.parseInt(academicYear.substring(0, 2)); //first year input
            int secondYear = Integer.parseInt(academicYear.substring(2, 4)); //second year input

            if (secondYear > (formattedDate + 6) || firstYear > (formattedDate + 5)) {
                return false;
            } else if (secondYear < (formattedDate - 4) || firstYear < (formattedDate - 5)) {
                return false;
            } else {
                return true;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
