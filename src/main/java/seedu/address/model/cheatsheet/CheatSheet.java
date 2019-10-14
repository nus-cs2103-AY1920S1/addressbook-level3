package seedu.address.model.cheatsheet;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Cheatsheet object in the StudyBuddy application.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class CheatSheet {
    // Identity fields
    private final Title title;

    // Data fields
    private final Set<Content> contents = new HashSet<>();
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public CheatSheet(Title title, Set<Content> contents, Set<Tag> tags) {
        requireAllNonNull(title, contents, tags);
        this.title = title;
        this.contents.addAll(contents);
        this.tags.addAll(tags);
    }

    // temporary cheatsheet constructor, just title and tags

    public CheatSheet(Title title, Set<Tag> tags) {
        requireAllNonNull(title, tags);
        this.title = title;
        this.tags.addAll(tags);
    }

    public Title getTitle() {
        return title;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public Set<Content> getContents() {
        return Collections.unmodifiableSet(contents);
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameCheatSheet(CheatSheet otherCheatSheet) {
        if (otherCheatSheet == this) {
            return true;
        }

        return otherCheatSheet != null
                && otherCheatSheet.getTitle().equals(getTitle());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CheatSheet)) {
            return false;
        }

        CheatSheet otherCheatSheet = (CheatSheet) other;
        return otherCheatSheet.getTitle().equals(getTitle())
                && otherCheatSheet.getContents().equals(getContents())
                && otherCheatSheet.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, contents, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Title: ")
                .append(getTitle())
                .append(" Tags: ");
        getTags().forEach(builder::append);

        builder.append(" Contents: ");
        getContents().forEach(builder::append);

        return builder.toString();
    }
}
