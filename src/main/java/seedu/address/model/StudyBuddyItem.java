package seedu.address.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Superclass StudyBuddyItem
 * For future consideration
 * Perhaps get cheatSheet, notes, flashcard objects to extend this class
 * Such that we can extract out the FilteredList to have generics : ? extends StudyBuddyItem?
 */

public class StudyBuddyItem {

    // Data fields
    private final Set<Tag> tags = new HashSet<>();

    public StudyBuddyItem(Set<Tag> tags) {

        this.tags.addAll(tags);
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Checks if this studyBuddyItem contains the tag
     * @return true if it has the tag, false otherwise
     */
    public boolean containsTag(Tag tag) {
        return this.tags.contains(tag);
    }
}
