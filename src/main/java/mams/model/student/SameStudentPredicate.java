package mams.model.student;

import java.util.function.Predicate;

/**
 * Tests if a {@code Student} matches another given {@code Student}
 */
public class SameStudentPredicate implements Predicate<Student> {
    private final Student student;

    public SameStudentPredicate(Student student) {
        this.student = student;
    }

    @Override
    public boolean test(Student student) {
        return this.student.equals(student);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SameStudentPredicate // instanceof handles nulls
                && student.equals(((SameStudentPredicate) other).student)); // state check
    }
}
