package io.xpire.model.tag;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/**
 * @@author Kalsyc
 * Stores the tags to edit the xpireItem with.
 */
public class TagItemDescriptor {
    private Set<Tag> tags;

    public TagItemDescriptor() {}

    public TagItemDescriptor(TagItemDescriptor toCopy) {
        setTags(toCopy.tags);
    }

    /**
     * Sets {@code tags} to this object's {@code tags}.
     * A defensive copy of {@code tags} is used internally.
     */
    public void setTags(Set<Tag> tags) {
        if (tags != null) {
            TreeSet<Tag> set = new TreeSet<>(new TagComparator());
            set.addAll(tags);
            this.tags = set;
        } else {
            this.tags = null;
        }
    }

    public Set<Tag> getTags() {
        return (this.tags != null) ? Collections.unmodifiableSet(this.tags) : null;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagItemDescriptor)) {
            return false;
        }

        // state check
        TagItemDescriptor e = (TagItemDescriptor) other;

        return getTags().equals(e.getTags());
    }
}
