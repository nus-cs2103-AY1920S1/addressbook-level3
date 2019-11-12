package seedu.address.model.cheatsheet;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a content of a Cheatsheet in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidContent(String)}
 */
public class Content {
    public static final String MESSAGE_CONSTRAINTS =
            "Cheatsheet contents are automatically generated according to tags specified."
                + "\nAny blank contents or contents that do not only contain alphanumeric characters and spaces"
                + "will not be added into the cheatsheet."
                + "\nAll leading and trailing spaces are ignored.";

    /*
     * The first character of the content must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "\\S.*"; //"[\\p{Alnum}][\\p{Alnum} ]*";

    public final String content;
    public final Set<Tag> tags;

    private int index = 0;

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
    }

    public Content(String question, String answer, Set<Tag> tags) {
        requireNonNull(question, answer);
        requireNonNull(tags);
        checkArgument(isValidContent(question), MESSAGE_CONSTRAINTS);
        checkArgument(isValidContent(answer), MESSAGE_CONSTRAINTS);
        this.tags = tags;
        this.content = "Question: " + question
                + "; Answer: " + answer;
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

    public int getIndex() {
        return index;
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, tags);
    }

    /**
     * Formatting for list
     * Truncates the content for the general list command
     * @return String of truncated content
     */
    public String formatToList() {
        StringBuilder toBeListed = new StringBuilder();
        if (this.content.length() > 20) {
            toBeListed.append(content.substring(0, 20))
                    .append("...");
        } else {
            toBeListed.append(content);
        }
        return toBeListed.toString();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return index + ". " + content;
    }

}
