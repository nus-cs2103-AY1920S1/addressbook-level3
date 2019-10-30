package seedu.address.model.cap.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable.
 */
public class Grade {

    private final String grade;
    /**
     * Constructs a {@code Phone}.
     *
     * @param grade A valid grade.
     */
    public Grade(String grade) {
        requireNonNull(grade);
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
}
