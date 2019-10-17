package seedu.address.model.cheatsheet;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a content of a Cheatsheet in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidContent(String)}
 */
public class Content {

    public static final String MESSAGE_CONSTRAINTS = "Cheatsheet contents should not be blank. "
            + "For flashcard's components, images are not supported in the cheatsheet.";
    public static final String VALIDATION_REGEX = "\\S.*"; //"\\p{Alnum}+";

    public final String content;

    /**
     * Constructs a {@code Content}.
     *
     * @param content A valid content name.
     */
    public Content(String content) {
        requireNonNull(content);
        checkArgument(isValidContent(content), MESSAGE_CONSTRAINTS);
        this.content = content;
    }

    public Content(String question, String answer) {
        requireNonNull(question, answer);
        checkArgument(isValidContent(question), MESSAGE_CONSTRAINTS);
        checkArgument(isValidContent(answer), MESSAGE_CONSTRAINTS);
        this.content = "Question: " + question
                + "; Answer: " + answer;

        System.out.println(this.content);
    }

    /**
     * Returns true if a given string is a valid content name.
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
