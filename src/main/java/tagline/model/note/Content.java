package tagline.model.note;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Note's content in the note book.
 * Guarantees: immutable
 */
public class Content {

    public final String value;

    /**
     * Constructs an {@code Content}.
     *
     * @param content A valid content.
     */
    public Content(String content) {
        requireNonNull(content);
        // checkArgument(isValidContent(content), MESSAGE_CONSTRAINTS);
        value = content;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Content // instanceof handles nulls
                && value.equals(((Content) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
