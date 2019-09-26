package seedu.mark.model.bookmark;

import static seedu.mark.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.mark.model.tag.Tag;

/**
 * Represents a Bookmark in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Bookmark {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Url url;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Bookmark(Name name, Phone phone, Url url, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, url, address, tags);
        this.name = name;
        this.phone = phone;
        this.url = url;
        this.address = address;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Url getUrl() {
        return url;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Bookmark otherBookmark) {
        if (otherBookmark == this) {
            return true;
        }

        return otherBookmark != null
                && otherBookmark.getName().equals(getName())
                && (otherBookmark.getPhone().equals(getPhone()) || otherBookmark.getUrl().equals(getUrl()));
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

        if (!(other instanceof Bookmark)) {
            return false;
        }

        Bookmark otherBookmark = (Bookmark) other;
        return otherBookmark.getName().equals(getName())
                && otherBookmark.getPhone().equals(getPhone())
                && otherBookmark.getUrl().equals(getUrl())
                && otherBookmark.getAddress().equals(getAddress())
                && otherBookmark.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, url, address, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" URL: ")
                .append(getUrl())
                .append(" Address: ")
                .append(getAddress())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
