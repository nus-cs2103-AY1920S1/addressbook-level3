package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a lecture note's contents. They are only guaranteed immutable and non-blank;
 * in particular there can be duplicates.
 */
public class Content {
    public static final String MESSAGE_CONSTRAINTS = "The contents can take any non-blank value";

    public final String content;

    /**
     * Constructs a {@code Content} object.
     *
     * @param content Valid content.
     */
    public Content(String content) {
        requireNonNull(content);
        checkArgument(isValidContent(content), MESSAGE_CONSTRAINTS);
        this.content = content;
    }

    /**
     * Returns true if a given string represents valid content.
     */
    public static boolean isValidContent(String test) {
        return test.trim().length() > 0;
    }

    @Override
    public String toString() {
        return content;
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
