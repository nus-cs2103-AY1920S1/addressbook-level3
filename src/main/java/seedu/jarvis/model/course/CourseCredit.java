package seedu.jarvis.model.course;

import static java.util.Objects.requireNonNull;

public class CourseCredit {
    public final Integer credit;

    /**
     * Constructs a {@code CourseCredit}
     *
     * @param credit of the course
     */
    public CourseCredit(Integer credit) {
        requireNonNull(credit);
        this.credit = credit;
    }

    @Override
    public String toString() {
        return credit + "";
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof CourseCredit
                && credit.equals(((CourseCredit) other).credit));
    }

    @Override
    public int hashCode() {
        return credit.hashCode();
    }
}
