package seedu.address.model.note;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Content of the note
 */
public class Content {
    public static final String MESSAGE_CONSTRAINTS =
            "Content should not be blank";

    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String content;

    public Content(String content) {
        requireNonNull(content);
        checkArgument(isValidContent(content), MESSAGE_CONSTRAINTS);
        this.content = content;
    }

    @Override
    public String toString() {
        return content;
    }

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
}
