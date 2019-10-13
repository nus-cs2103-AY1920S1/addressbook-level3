package seedu.address.model.diary;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Page in Duke Cooks.
 * Guarantees: details are present and not null, field values validated and immutable.
 */
public class Page {

    // Identity fields
    private final Title title;
    // private String description;

    /**
     * Every field must be present and not null.
     */
    public Page(Title title) {
        requireAllNonNull(title);
        this.title = title;
    }

    public Title getTitle() {
        return title;
    }

    /**
     * Returns true if both Pages have the same identity fields.
     * This defines a stronger notion of equality between two pages.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Page)) {
            return false;
        }

        Page otherPage = (Page) other;
        return otherPage.getTitle().equals(getTitle());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Title:")
                .append(getTitle());
        return builder.toString();
    }
}
