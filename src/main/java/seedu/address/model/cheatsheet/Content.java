package seedu.address.model.cheatsheet;

import seedu.address.model.tag.Tag;

import java.util.Objects;
import java.util.Set;

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
    private static int counter = 1;

    public final String content;
    public final Set<Tag> tags;

    public int index = 0;

    /**
     * Constructs a {@code Content}.
     *
     * @param content A valid content name.
     */
    public Content(String content, Set<Tag> tags) {
        requireNonNull(content);
        requireNonNull(tags);
        checkArgument(isValidContent(content), MESSAGE_CONSTRAINTS);
        this.content = content;
        this.tags = tags;

        //this.index = counter++;
    }

    public Content(String question, String answer, Set<Tag> tags) {
        requireNonNull(question, answer);
        requireNonNull(tags);
        checkArgument(isValidContent(question), MESSAGE_CONSTRAINTS);
        checkArgument(isValidContent(answer), MESSAGE_CONSTRAINTS);
        this.tags = tags;
        this.content = "Question: " + question
                + "; Answer: " + answer;

        //this.index = counter++;
    }

    /**
     * Returns true if a given string is a valid content name.
     */
    public static boolean isValidContent(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Content)) {
            return false;
        }

        Content otherContent = (Content) other;
        return otherContent.getContent().equals(getContent())
                && otherContent.getTags().equals(getTags());
    }

    public String getContent() {
        return content;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public static void resetCounter() {
        counter = 1;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, tags);
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return index + ". " + content;
    }

}
