package seedu.address.model.quiz.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Question's comment number in modulo.
 * Guarantees: immutable; is valid as declared in {@link #isValidComment(String)}
 */
public class Comment {

    public static final String MESSAGE_CONSTRAINTS =
            "Comment should not contains any instruction command";

    public final String value;

    /**
     * Constructs a {@code Comment}.
     *
     * @param comment A valid comment number.
     */
    public Comment(String comment) {
        requireNonNull(comment);
        checkArgument(isValidComment(comment), MESSAGE_CONSTRAINTS);

        value = comment;
        assert !value.contains("<val>") : "Comment should not contain instruction comment keyword";
    }

    /**
     * Returns true if a given string is a valid comment number.
     */
    public static boolean isValidComment(String test) {
        return !test.contains("<val>");
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Comment // instanceof handles nulls
                && value.equals(((Comment) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
