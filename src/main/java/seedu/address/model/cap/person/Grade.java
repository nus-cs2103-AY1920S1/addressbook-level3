package seedu.address.model.cap.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable.
 */
public class Grade {

    public static final String MESSAGE_CONSTRAINTS =
            "Grade should be of a valid grade and it is case-sensitive.";
    private final String grade;
    /**
     * Constructs a {@code grade}.
     *
     * @param grade A valid grade.
     */
    public Grade(String grade) {
        requireNonNull(grade);
        checkArgument(isValidGrade(grade), MESSAGE_CONSTRAINTS);
        this.grade = grade;
    }

    public String getGrade() {
        return grade;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Grade // instanceof handles nulls
                && this.grade == grade); // state check
    }

    @Override
    public String toString() {
        return grade;
    }

    /**
     * Checks the validity of the Grade input
     * @param grade can only be the permitted grades by NUS
     * @return boolean values after checking
     */
    public static boolean isValidGrade(String grade) {
        switch (grade) {
        case "A+":
        case "A":
        case "A-":
        case "B+":
        case "B":
        case "B-":
        case "C+":
        case "C":
        case "D+":
        case "D":
        case "F":
            return true;
        default:
            return false;
        }
    }
}
