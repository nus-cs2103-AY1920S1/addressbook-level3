package seedu.address.model.question;

import static java.util.Objects.requireNonNull;

public class Subject {
    public static final String MESSAGE_CONSTRAINT = "Subjects should begin with a non-white space character "
            + "and only covers the existed subjects in the database";

    public final String subject;

    public Subject(String subject) {
        requireNonNull(subject);
        this.subject = subject;
    }

    public static boolean isValidSubject(String test) {
        return test.trim().length() > 0;
    }

    @Override
    public String toString() {
        return subject;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Subject // instanceof handles nulls
                && subject.equals(((Subject) other).subject)); // state check
    }

    @Override
    public int hashCode() {
        return subject.hashCode();
    }
}
