package seedu.address.model.cap.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Faculty of a module in the CAP log.
 */
public class Faculty {

    public static final String MESSAGE_CONSTRAINTS =
            "Faculty should only contain characters, there "
                + "should not be special characters and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    private final String faculty;
    /**
     * Constructs a {@code Phone}.
     *
     * @param faculty A valid phone number.
     */
    public Faculty(String faculty) {
        requireNonNull(faculty);
        checkArgument(isValidName(faculty), MESSAGE_CONSTRAINTS);
        this.faculty = faculty;
    }

    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public String getFaculty() {
        return faculty;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Faculty // instanceof handles nulls
                && faculty.equals(((Faculty) other).faculty)); // state check
    }

    @Override
    public String toString() {
        return faculty;
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidFaculty(String test) {
        switch (test.toLowerCase()) {
        case "nus business school":
        case "business":
        case "arts and social sciences":
        case "computing":
        case "continuing and lifelong education":
        case "dentistry":
        case "design and environment":
        case "duke-nus":
        case "engineering":
        case "integrative sciences and engineering":
        case "law":
        case "medicine":
        case "Yong Loo Lin School (Medicine)":
        case "music":
        case "science":
        case "university scholar programme":
        case "yale-nus":
            return true;
        default:
            return false;
        }
    }
}
