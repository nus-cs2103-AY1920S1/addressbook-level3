package seedu.address.model.cap.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Faculty of a module in the CAP log.
 */
public class Faculty {

    private final String faculty;
    /**
     * Constructs a {@code Phone}.
     *
     * @param credit A valid phone number.
     */
    public Faculty(String credit) {
        requireNonNull(credit);
        this.faculty = credit;
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
}
