package seedu.address.model.question;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a question's content. Contents are guaranteed to be unique among questions.
 * They are also immutable and guaranteed to be valid.
 */
public class Content {
    public static final String MESSAGE_CONSTRAINTS = "The contents can take any non-blank value.";

    public final String content;

    /**
     * Constructs a {@code Content} object.
     *
     * @param content Valid content.
     */
    public Content(String content) {
        requireNonNull(content);
        checkArgument(isValidArgument(content), MESSAGE_CONSTRAINTS);
        this.content = content;
    }

    /**
     * Returns true if a given string represents valid content.
     */
    public static boolean isValidArgument(String test) {
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
