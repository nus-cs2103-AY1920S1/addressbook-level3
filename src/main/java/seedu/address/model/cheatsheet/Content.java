package seedu.address.model.cheatsheet;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Content {

    public static final String MESSAGE_CONSTRAINTS = "Content should be alphanumeric. " +
            "For flashcard's components, images are not supported in the cheatsheet.";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public final String content;

    /**
     * Constructs a {@code Tag}.
     *
     * @param content A valid tag name.
     */
    public Content(String content) {
        requireNonNull(content);
        checkArgument(isValidContent(content), MESSAGE_CONSTRAINTS);
        this.content = content;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidContent(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Content // instanceof handles nulls
                && content.equals(((Content) other).content)); // state check
    }

    @Override
    public int hashCode() {
        return content.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + content + ']';
    }

}
