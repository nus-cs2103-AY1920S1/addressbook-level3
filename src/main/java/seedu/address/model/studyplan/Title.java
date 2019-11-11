package seedu.address.model.studyplan;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.model.studyplan.exceptions.InvalidTitleException;

/**
 * Represents the title of a study plan.
 */
public class Title implements Cloneable {
    public static final int TITLE_CHARACTERS_LIMIT = 20;
    public static final String MESSAGE_CONSTRAINTS = "A title should only contain at most "
            + TITLE_CHARACTERS_LIMIT + " ASCII characters ";
    public static final String VALIDATION_REGEX = "\\p{ASCII}{0," + TITLE_CHARACTERS_LIMIT + "}";

    private String value;

    /**
     * Constructs a title that fulfills the validation constraint.
     *
     * @param value the name of the title.
     */
    public Title(String value) {
        requireNonNull(value);
        try {
            checkArgument(isValidTitle(value), MESSAGE_CONSTRAINTS);
        } catch (IllegalArgumentException exception) {
            throw new InvalidTitleException(exception.getMessage());
        }
        this.value = value;
    }

    /**
     * Returns true if a given string is a valid study plan title.
     */
    public static boolean isValidTitle(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Title // instanceof handles nulls
                && value.equals(((Title) other).value)); // state check
    }

    public Title clone() throws CloneNotSupportedException {
        return (Title) super.clone();
    }
}
